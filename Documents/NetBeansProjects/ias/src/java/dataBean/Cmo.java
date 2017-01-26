package dataBean;

import db.DataConn;
import java.io.Serializable;
import static java.lang.Math.abs;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import list.VisitItem;
import list.Money;

/**
 *
 * @author Андрюха
 */
@ManagedBean(name = "cmo")
@SessionScoped
public class Cmo implements Serializable {

    private List<Money> payment;
    private static List<Money> filteredPayment;

    db.DataConn db = new db.DataConn();
    Map<String, String> dictDoc = new HashMap<String, String>();
    Map<String, String> dictCatNameDoc = new HashMap<String, String>();
    Map<String, String> dictCat = new HashMap<String, String>();
    Map<String, String> dictHosp = new HashMap<String, String>();
    Map<String, String> dictHospCat = new HashMap<String, String>();
    Map<String, String> dictTarif = new HashMap<String, String>();

    @PostConstruct
    public void init() {
        payment = createPayment();
    }

    public Cmo() {

        db.qeuryRequest("select from Doctors where @rid in (select id_doctor from visits group by id_doctor)");

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

        for (int i = 0; i < db.getResult().size(); i++) {
            //словарь id_cat->name
            dictCat.put(db.getResult().get(i).field("@rid").toString(), db.getResult().get(i).field("name").toString());
        }

        db.qeuryRequest("select from Hospitals where @rid in (select id_hosp from visits group by id_hosp)");
        for (int i = 0; i < db.getResult().size(); i++) {
            //словарь id_hosp->name
            dictHosp.put(db.getResult().get(i).field("@rid").toString(), db.getResult().get(i).field("name").toString());
            //словарь id_hosp->name
            String s = db.getResult().get(i).field("id_cat_hosp").toString();
            s = s.substring(1, s.length() - 1);
            dictHospCat.put(db.getResult().get(i).field("@rid").toString(), s);
        }

        db.qeuryRequest("select from Doctors");

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
    }

    private String name;
    private String countClients;
    private String year;
    private ArrayList<String> years;
    private String month;
    private ArrayList<String> months;
    private String monthsNum;
    private int total;
    private String ly;

    public String getName() {
        setName();
        return name;
    }

    public void setName() {

        db.DataConn db = new db.DataConn();
        db.qeuryRequest("select name from Cmos where @rid = '" + User.getId_cmo() + "';");
        ArrayList nameComp = db.queryField("name");
        db.closeConn();

        this.name = nameComp.get(0).toString();
    }

    public String getLy() {
        return ly;
    }

    public void setLy(String ly) {
        this.ly = ly;
    }

    public String getMonthsNum() {
        return monthsNum;
    }

    public void setMonthsNum(String monthsNum) {
        this.monthsNum = monthsNum;
    }

    public String getCountClients() {
        setCountClients();
        return countClients;
    }

    public void setCountClients() {
        db.DataConn db = new db.DataConn();
        db.qeuryRequest("select count(*) as count from regs where id_cmo=" + User.getId_cmo() + ";");
        String count = db.queryField("count").get(0).toString();
        db.closeConn();

        this.countClients = count;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ArrayList<String> getYears() {

        years = new ArrayList<String>();

        years.add("2017");
        years.add("2016");
        years.add("2015");

        return years;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        switch (month) {
            case "январь":
                setMonthsNum("01");
                break;
            case "февраль":
                setMonthsNum("02");
                break;
            case "март":
                setMonthsNum("03");
                break;
            case "апрель":
                setMonthsNum("04");
                break;
            case "май":
                setMonthsNum("05");
                break;
            case "июнь":
                setMonthsNum("06");
                break;
            case "июль":
                setMonthsNum("07");
                break;
            case "август":
                setMonthsNum("08");
                break;
            case "сентябрь":
                setMonthsNum("09");
                break;
            case "октябрь":
                setMonthsNum("10");
                break;
            case "ноябрь":
                setMonthsNum("11");
                break;
            case "декабрь":
                setMonthsNum("12");
                break;
        }
        this.month = month;
    }

    public ArrayList<String> getMonths() {

        months = new ArrayList<String>();

        months.add("январь");
        months.add("февраль");
        months.add("март");
        months.add("апрель");
        months.add("май");
        months.add("июнь");
        months.add("июль");
        months.add("август");
        months.add("сентябрь");
        months.add("октябрь");
        months.add("ноябрь");
        months.add("декабрь");

        return months;
    }

    public List<VisitItem> createVisits() {

        ArrayList<VisitItem> dataVisit = new ArrayList();

        if ((getMonth() != null) && (getYear() != null)) {
            db = new DataConn();
            db.qeuryRequest("select from Regs where id_cmo = " + User.getId_cmo() + ";");
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            ArrayList birthday = db.queryField("birthday");
            ArrayList id = db.queryField("@rid");

            for (int j = 0; j < birthday.size(); j++) {

                String dr_user = df.format(birthday.get(j));

                db = new db.DataConn();
                //getmax date
                int iYear = Integer.parseInt(getYear());
                int iMonth = Integer.parseInt(getMonthsNum()) - 1; // 1 (months begin with 0)
                int iDay = 1;

// Create a calendar object and set year and month
                Calendar mycal = new GregorianCalendar(iYear, iMonth, iDay);

// Get the number of days in that month
                int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28

                db.qeuryRequest("select from Visits where id_regs = " + id.get(j).toString() + " and date > DATE('" + getYear() + "-" + getMonthsNum() + "-01 00:00:00') and "
                        + "date < DATE('" + getYear() + "-" + getMonthsNum() + "-" + daysInMonth + " 00:00:00');");

                ArrayList ish_dataVisit = db.queryField("date");
                ArrayList ish_hospVisit = db.queryField("id_hosp");
                ArrayList ish_docVisit = db.queryField("id_doctor");
                db.closeConn();

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
            }
            int sum = 0;
            for (int i = 0; i < dataVisit.size(); i++) {
                sum += dataVisit.get(i).getCost();
            }
            setTotal(sum);
        }

        return dataVisit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getResult(String d1, String d2) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate firstDate = LocalDate.parse(d1, formatter);
        LocalDate secondDate = LocalDate.parse(d2, formatter);

        Period period = Period.between(firstDate, secondDate);
        return period.getYears();
    }

    private List<Money> createPayment() {

        List<Money> list = new ArrayList<Money>();
        //add from db
        db.DataConn db = new db.DataConn();
        db.qeuryRequest("select * from Get_money where id_msk = " + User.getId_cmo() + ";");

        ArrayList id_hosp = db.queryField("id_hosp");
        ArrayList date = db.queryField("date");
        ArrayList sum = db.queryField("sum");

        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            for (int i = 0; i < id_hosp.size(); i++) {
                //удалить []
                String id_hospital = id_hosp.get(i).toString();
                id_hospital = id_hospital.substring(1, id_hospital.length() - 1);
                String year = df.format(date.get(i));
                String summa = sum.get(i).toString();
                String hospital = dictHosp.get(id_hospital);

                list.add(new Money(year, summa, hospital));
            }
        } catch (Exception e) {

        } finally {
            db.closeConn();
        }

        setFilteredPayment(list);
        return list;
    }

    public List<Money> getPayment() {
        return payment;
    }

    public void setPayment(List<Money> payment) {
        this.payment = payment;
    }

    public static List<Money> getFilterPayForGraph() {
        return filteredPayment;
    }

    public List<Money> getFilteredPayment() {
        return filteredPayment;
    }

    public void setFilteredPayment(List<Money> filteredPayment) {
        Cmo.filteredPayment = filteredPayment;
    }

    public Map<String, String> getDictHosp() {
        return dictHosp;
    }

    public void setDictHosp(Map<String, String> dictHosp) {
        this.dictHosp = dictHosp;
    }

    public void savePayment() {
        //сформировать счет  с учетом больницы
            try {
                db.DataConn db = new db.DataConn();
                String ly_name = getLy();
                String ly_id = getKeyByValue(dictHosp, ly_name);
                db.qeuryRequest("select from Get_money where date in ('" + getYear() + "-" + getMonthsNum() + "-01 00:00:00') and"
                        + " id_hosp = " + ly_id + " and id_msk = " + User.getId_cmo() + ";");
                ArrayList money_id = db.queryField("@rid");
                
                if (money_id.size() == 0) {
                    db.qeuryRun("INSERT INTO Get_money (date, sum, id_msk, id_hosp) VALUES "
                            + "('" + getYear() + "-" + getMonthsNum() + "-01 00:00:00', " + getTotal() + ", " + User.getId_cmo() + ", " + ly_id + ");");
                } else {
                    String id = money_id.get(0).toString();
                    db.qeuryRun("UPDATE Get_money SET sum = " + getTotal() + " WHERE @rid = " + id + ";");
                }
            } catch (Exception e) {

            } finally {
                //db.closeConn();
            }
    }

    public <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
