/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import list.DocItem;

@ManagedBean(name = "filterViewDoctors")
@ViewScoped
public class FilterDoctors implements Serializable {

    private List<DocItem> doctors;
    private List<DocItem> filteredDoctors;

    @PostConstruct
    public void init() {
        doctors = createDoctors();
    }

    public List<DocItem> createDoctors() {
        List<DocItem> list = new ArrayList<DocItem>();
        //add from db
        db.DataConn db = new db.DataConn();
        db.qeuryRequest("select id_hosp from Regs where @rid = '" + User.getId_regs() + "';");

        ArrayList id_hosp = db.queryField("id_hosp");
        ArrayList categoryName = new ArrayList();
        ArrayList fam = new ArrayList();
        ArrayList im = new ArrayList();
        if (id_hosp.size() > 0) {
            //удалить []
            String id_hospital = id_hosp.get(0).toString();
            id_hospital = id_hospital.substring(1, id_hospital.length() - 1);

            db.qeuryRequest("select id_category, fam, im from Doctors where id_hosp = " + id_hospital + ";");

            try {
                ArrayList category = db.queryField("id_category");
                for (int i = 0; i < category.size(); i++) {
                    String id_cat = category.get(i).toString();
                    id_cat = id_cat.substring(1, id_cat.length() - 1);
                    db.qeuryRequest("select from Cat_doc where @rid = " + id_cat + ";");
                    categoryName.add(db.queryField("name").get(0).toString());
                }
            db.qeuryRequest("select id_category, fam, im from Doctors where id_hosp = " + id_hospital + ";");
                fam = db.queryField("fam");
                im = db.queryField("im");
                for (int i = 0; i < fam.size(); i++) {
                    list.add(new DocItem(categoryName.get(i).toString(), fam.get(i).toString(), im.get(i).toString()));
                }
            } catch (Exception e) {

            } finally {
                db.closeConn();
            }
        }

        return list;
    }

    public List<DocItem> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DocItem> doctors) {
        this.doctors = doctors;
    }

    public List<DocItem> getFilteredDoctors() {
        return filteredDoctors;
    }

    public void setFilteredDoctors(List<DocItem> filteredDoctors) {
        this.filteredDoctors = filteredDoctors;
    }
}
