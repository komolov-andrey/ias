/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBean;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import java.io.Serializable;
import static java.lang.Math.abs;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import list.VisitItem;

/**
 *
 * @author Андрюха
 */
@ManagedBean(name = "filterViewVisits")
@ViewScoped
public class FilterVisits implements Serializable {

    private List<VisitItem> visits;
    private static List<VisitItem> filteredVisits;
    private int total;

    @PostConstruct
    public void init() {
        visits = createVisits();
    }

    public List<VisitItem> createVisits() {

        db.DataConn db = new db.DataConn();

        db.qeuryRequest("select from Doctors where @rid in (select id_doctor from visits group by id_doctor)");
        Map<String, String> dictDoc = new HashMap<String, String>();
        for (int i = 0; i < db.getResult().size(); i++) {
            //словарь id_doc->fam,im
            dictDoc.put(db.getResult().get(i).field("@rid").toString(),
                    db.getResult().get(i).field("fam").toString() + " " + db.getResult().get(i).field("im").toString());
        }

        ArrayList id_cat = new ArrayList();

        db.qeuryRequest("select from doctors where @rid in (" + dictDoc.keySet() + ")");
        for (int i = 0; i < db.getResult().size(); i++) {
            //удалить []
            String cat = db.getResult().get(i).field("id_category").toString();
            cat = cat.substring(1, cat.length() - 1);
            id_cat.add(cat);
        }

        db.qeuryRequest("select from Cat_doc where @rid in (" + id_cat + ")");
        Map<String, String> dictCat = new HashMap<String, String>();
        for (int i = 0; i < db.getResult().size(); i++) {
            //словарь id_cat->name
            dictCat.put(db.getResult().get(i).field("@rid").toString(), db.getResult().get(i).field("name").toString());
        }

        db.qeuryRequest("select from Hospitals where @rid in (select id_hosp from visits group by id_hosp)");
        Map<String, String> dictHosp = new HashMap<String, String>();
        Map<String, String> dictHospCat = new HashMap<String, String>();
        for (int i = 0; i < db.getResult().size(); i++) {
            //словарь id_hosp->name
            dictHosp.put(db.getResult().get(i).field("@rid").toString(), db.getResult().get(i).field("name").toString());
            //словарь id_hosp->name
            String s = db.getResult().get(i).field("id_cat_hosp").toString();
            s = s.substring(1, s.length() - 1);
            dictHospCat.put(db.getResult().get(i).field("@rid").toString(), s);
        }

        db.qeuryRequest("select from Doctors");
        Map<String, String> dictCatNameDoc = new HashMap<String, String>();
        for (int i = 0; i < db.getResult().size(); i++) {
            //словарь id_hosp->name
            dictCatNameDoc.put(db.getResult().get(i).field("fam").toString() + " " + db.getResult().get(i).field("im").toString(),
                    db.getResult().get(i).field("id_category").toString());
        }

        //тариф
        db.qeuryRequest("select year from Tarifs group by year;");
        ArrayList year = db.queryField("year");

        db.qeuryRequest("select id_cat_pat from Tarifs group by id_cat_pat;");
        ArrayList id_cat_pat = db.queryField("id_cat_pat");

        db.qeuryRequest("select id_cat_doc from Tarifs group by id_cat_doc;");
        ArrayList id_cat_doc = db.queryField("id_cat_doc");

        db.qeuryRequest("select id_cat_hosp from Tarifs group by id_cat_hosp;");
        ArrayList ish_id_cat_hosp = db.queryField("id_cat_hosp");
        ArrayList id_cat_hosp = new ArrayList();
        for (int i = 0; i < ish_id_cat_hosp.size(); i++) {
            String s = ish_id_cat_hosp.get(i).toString();
            s = s.substring(1, s.length() - 1);
            id_cat_hosp.add(s);
        }

        Map<String, String> dictTarif = new HashMap<String, String>();
        for (int a = 0; a < year.size(); a++) {
            for (int b = 0; b < id_cat_pat.size(); b++) {
                for (int c = 0; c < id_cat_doc.size(); c++) {
                    for (int d = 0; d < id_cat_hosp.size(); d++) {

                        String yearS = year.get(a).toString();
                        String id_cat_patS = id_cat_pat.get(b).toString();
                        id_cat_patS = id_cat_patS.substring(1, id_cat_patS.length() - 1);
                        String id_cat_docS = id_cat_doc.get(c).toString();
                        id_cat_docS = id_cat_docS.substring(1, id_cat_docS.length() - 1);
                        String id_cat_hospS = id_cat_hosp.get(d).toString();
                        db.qeuryRequest("select tarif from Tarifs where year = "
                                + yearS + " and id_cat_pat = "
                                + id_cat_patS + " and id_cat_doc = "
                                + id_cat_docS + " and id_cat_hosp = "
                                + id_cat_hospS + ";");
                        //словарь год/кат_пац/кат_док/кат_больн -> тариф
                        dictTarif.put(yearS + "" + id_cat_patS + "" + id_cat_docS + "" + id_cat_hospS + "", db.getResult().get(0).field("tarif").toString());
                    }
                }
            }
        }

        db.qeuryRequest(
                "select birthday from Regs where @rid = " + User.getId_regs() + ";");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dr_user = df.format(db.queryField("birthday").get(0));

        db.qeuryRequest("select from Visits where id_regs = " + User.getId_regs() + ";");

        ArrayList ish_dataVisit = db.queryField("date");
        ArrayList ish_hospVisit = db.queryField("id_hosp");
        ArrayList ish_docVisit = db.queryField("id_doctor");
        db.closeConn();

        ArrayList dataVisit = new ArrayList();
        ArrayList hospVisit = new ArrayList();
        ArrayList docVisit = new ArrayList();
        ArrayList catVisit = new ArrayList();
        ArrayList tarifVisit = new ArrayList();
        String nameHosp;
        String nameDoc;
        String nameCat;
        for (int i = 0; i < ish_dataVisit.size(); i++) {
            
            String dateVisit = df.format(ish_dataVisit.get(i));
            nameHosp = ish_hospVisit.get(i).toString();
            nameHosp = nameHosp.substring(1, nameHosp.length() - 1);

            nameDoc = ish_docVisit.get(i).toString();
            nameDoc = nameDoc.substring(1, nameDoc.length() - 1);
            String s = dictDoc.get(nameDoc);
            String cat_doc = dictCatNameDoc.get(s);
            cat_doc = cat_doc.substring(1, cat_doc.length() - 1);

            String yearVisit = dateVisit.substring(6);
            String cat_pat;

            int vozrast = abs(getResult(dateVisit, dr_user));
            if (vozrast < 18) {
                cat_pat = "#42:0";
            } else {
                cat_pat = "#41:0";
            }

            String tarif = yearVisit + cat_pat + cat_doc + dictHospCat.get(nameHosp);
            dataVisit.add(new VisitItem(dateVisit, dictHosp.get(nameHosp), s, dictCat.get(cat_doc), dictTarif.get(tarif)));
        }
        setFilteredVisits(dataVisit);
        return dataVisit;
    }

    public int getResult(String d1, String d2) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate firstDate = LocalDate.parse(d1, formatter);
        LocalDate secondDate = LocalDate.parse(d2, formatter);

        Period period = Period.between(firstDate, secondDate);
        return period.getYears();
    }

    public List<VisitItem> getVisits() {
        return visits;
    }

    public void setVisits(List<VisitItem> visits) {
        this.visits = visits;
    }

    public List<VisitItem> getFilteredVisits() {
        return filteredVisits;
    }
    public static List<VisitItem> getFilteredVisitsForGraph() {
        return filteredVisits;
    }

    public void setFilteredVisits(List<VisitItem> filteredVisits) {
        this.filteredVisits = filteredVisits;
    }

    public int getTotal() {
        setTotal();
        return total;
    }

    public void setTotal() {
        int sum = 0;
        for (int i = 0; i < getFilteredVisits().size(); i++) {
           sum +=  getFilteredVisits().get(i).getCost();           
        }
        this.total = sum;
    }
}
