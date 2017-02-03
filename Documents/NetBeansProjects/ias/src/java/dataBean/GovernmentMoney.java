/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBean;

/**
 *
 * @author Андрюха
 */
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import list.GovMoneyItem;
import list.GovHospItem;
import list.GovDocItem;

@ManagedBean(name = "moneyGov")
@ViewScoped
public class GovernmentMoney implements Serializable {

    private List<GovMoneyItem> money;
    private List<GovMoneyItem> filterMoney;

    private List<GovHospItem> hospLoading;
    private List<GovHospItem> filterHospLoading;

    private List<GovDocItem> docLoading;
    private List<GovDocItem> filterDocLoading;

    Map<String, String> dictMsk = new HashMap<String, String>();
    Map<Integer, String> dictMonth = new HashMap<Integer, String>();

    private Map<Integer, String> dictYear = new HashMap<Integer, String>();
    private Map<String, String> dictHosp = new HashMap<String, String>();
    private Map<String, String> dictCat = new HashMap<String, String>();
    private Map<String, Integer> dictCatCount = new HashMap<String, Integer>();

    private int totalMoney;
    private int totalHospLoading;
    private int totalDocLoading;

    @PostConstruct
    public void init() {
        money = filterMoney = createTableMoney();
        hospLoading = filterHospLoading = createTableHospLoading();
        docLoading = filterDocLoading = createTableDocLoading();
    }

    public GovernmentMoney() {

        //словарь номер->месяц
        dictMonth.put(1, "Январь");
        dictMonth.put(2, "Февраль");
        dictMonth.put(3, "Март");
        dictMonth.put(4, "Апрель");
        dictMonth.put(5, "Май");
        dictMonth.put(6, "Июнь");
        dictMonth.put(7, "Июль");
        dictMonth.put(8, "Август");
        dictMonth.put(9, "Сентябрь");
        dictMonth.put(10, "Октябрь");
        dictMonth.put(11, "Ноябрь");
        dictMonth.put(12, "Декабрь");

    }

    public List<GovMoneyItem> createTableMoney() {

        List<GovMoneyItem> list = new ArrayList<GovMoneyItem>();

        db.DataConn db = new db.DataConn();

        db.qeuryRequest("select from Cmos;");
        for (int i = 0; i < db.getResult().size(); i++) {
            //словарь id_msk->name
            String id = db.queryField("@rid").get(i).toString();
            String name = db.queryField("name").get(i).toString();
            dictMsk.put(id, name);
        }

        db.qeuryRequest("select from Hospitals;");
        for (int i = 0; i < db.getResult().size(); i++) {
            //словарь hosp->name
            String id = db.queryField("@rid").get(i).toString();
            String name = db.queryField("name").get(i).toString();
            dictHosp.put(id, name);
        }
        db.qeuryRequest("select from Cat_doc;");
        for (int i = 0; i < db.getResult().size(); i++) {
            //словарь cat->name
            String id = db.queryField("@rid").get(i).toString();
            String name = db.queryField("name").get(i).toString();
            dictCat.put(id, name);
            dictCatCount.put(name, 0);
        }

        db.qeuryRequest("select from Get_money;");

        ArrayList id_msk = db.queryField("id_msk");
        ArrayList date = db.queryField("date");
        ArrayList sum = db.queryField("sum");
        ArrayList hosp = db.queryField("id_hosp");

        DateFormat df_y = new SimpleDateFormat("yyyy");
        DateFormat df_m = new SimpleDateFormat("MM");
        try {
            for (int i = 0; i < db.getResult().size(); i++) {
                String yearStr = df_y.format(date.get(i));
                int year = Integer.parseInt(yearStr);
                dictYear.put(year, yearStr);

                int month = Integer.parseInt(df_m.format(date.get(i)));
                String monthStr = dictMonth.get(month);
                float count = Float.parseFloat(sum.get(i).toString());
                String id = id_msk.get(i).toString();
                id = id.substring(1, id.length() - 1);
                String nameOmc = dictMsk.get(id);

                String id_hosp = hosp.get(i).toString();
                id_hosp = id_hosp.substring(1, id_hosp.length() - 1);
                String nameHosp = dictHosp.get(id_hosp);

                list.add(new GovMoneyItem(nameHosp, year, monthStr, month, count, nameOmc));
            }
        } catch (Exception e) {

        } finally {
            db.closeConn();
        }
        return list;
    }

    public List<GovHospItem> createTableHospLoading() {

        List<GovHospItem> list = new ArrayList<GovHospItem>();

        db.DataConn db = new db.DataConn();

        db.qeuryRequest("select id_hosp, count(*) as count from visits group by id_hosp;");

        ArrayList id_hosp = db.queryField("id_hosp");
        ArrayList countHosp = db.queryField("count");

        try {
            int sumCount = 0;
            for (int i = 0; i < countHosp.size(); i++) {
                sumCount += Integer.parseInt(countHosp.get(i).toString());
            }

            for (int i = 0; i < db.getResult().size(); i++) {

                int count = Integer.parseInt(countHosp.get(i).toString());
                String id = id_hosp.get(i).toString();
                id = id.substring(1, id.length() - 1);
                String nameHosp = dictHosp.get(id);

                float percent = (float) count * 100 / sumCount;
                list.add(new GovHospItem(nameHosp, count, percent));
            }

        } catch (Exception e) {

        } finally {
            db.closeConn();
        }

        return list;
    }

    public List<GovDocItem> createTableDocLoading() {

        List<GovDocItem> list = new ArrayList<GovDocItem>();

        db.DataConn db = new db.DataConn();

        db.qeuryRequest("select id_doctor, count(*) as count from visits group by id_doctor;");

        ArrayList id_doctor = db.queryField("id_doctor");
        ArrayList countHosp = db.queryField("count");

        try {

            for (int i = 0; i < countHosp.size(); i++) {

                int count = Integer.parseInt(countHosp.get(i).toString());

                String id = id_doctor.get(i).toString();
                id = id.substring(1, id.length() - 1);

                db.qeuryRequest("select from Doctors where @rid = " + id + ";");
                String id_cat = db.queryField("id_category").get(0).toString();
                id_cat = id_cat.substring(1, id_cat.length() - 1);

                //класть в словарь
                String nameCat = dictCat.get(id_cat);
                int value = dictCatCount.get(nameCat);
                dictCatCount.put(nameCat, value + count);
            }

            int sum = 0;
            for (int s : dictCatCount.values()) {
                sum += s;
            }
            if (sum == 0) {
                sum = 1;
            }

            for (String name : dictCatCount.keySet()) {
                
                int value = dictCatCount.get(name);
                float percent = (float) value * 100 / sum;
                list.add(new GovDocItem(name, value, percent));
            }

        } catch (Exception e) {

        } finally {
            db.closeConn();
        }

        return list;
    }

    public List<GovMoneyItem> getMoney() {
        return money;
    }

    public void setMoney(List<GovMoneyItem> money) {
        this.money = money;
    }

    public Map<String, String> getDictMsk() {
        return dictMsk;
    }

    public Map<Integer, String> getDictMonth() {
        return dictMonth;
    }

    public List<GovMoneyItem> getFilterMoney() {
        return filterMoney;
    }

    public void setFilterMoney(List<GovMoneyItem> filterMoney) {
        this.filterMoney = filterMoney;
    }

    public Map<Integer, String> getDictYear() {
        return dictYear;
    }

    public Map<String, String> getDictHosp() {
        return dictHosp;
    }

    public int getTotalMoney() {
        setTotalMoney();
        return totalMoney;
    }

    public void setTotalMoney() {
        int sum = 0;
        for (int i = 0; i < getFilterMoney().size(); i++) {
            sum += getFilterMoney().get(i).getSum();
        }
        this.totalMoney = sum;
    }

    public int getTotalHospLoading() {
        setTotalHospLoading();
        return totalHospLoading;
    }

    public void setTotalHospLoading() {
        int sum = 0;
        for (int i = 0; i < getFilterHospLoading().size(); i++) {
            sum += getFilterHospLoading().get(i).getCount();
        }
        this.totalHospLoading = sum;
    }

    public int getTotalDocLoading() {
        setTotalDocLoading();
        return totalDocLoading;
    }

    public void setTotalDocLoading() {
        int sum = 0;
        for (int i = 0; i < getFilterDocLoading().size(); i++) {
            sum += getFilterDocLoading().get(i).getCount();
        }
        this.totalDocLoading = sum;
    }

    public List<GovHospItem> getHospLoading() {
        return hospLoading;
    }

    public void setHospLoading(List<GovHospItem> hospLoading) {
        this.hospLoading = hospLoading;
    }

    public List<GovHospItem> getFilterHospLoading() {
        return filterHospLoading;
    }

    public void setFilterHospLoading(List<GovHospItem> filterHospLoading) {
        this.filterHospLoading = filterHospLoading;
    }

    public List<GovDocItem> getDocLoading() {
        return docLoading;
    }

    public void setDocLoading(List<GovDocItem> docLoading) {
        this.docLoading = docLoading;
    }

    public List<GovDocItem> getFilterDocLoading() {
        return filterDocLoading;
    }

    public void setFilterDocLoading(List<GovDocItem> filterDocLoading) {
        this.filterDocLoading = filterDocLoading;
    }

    public Map<String, String> getDictCat() {
        return dictCat;
    }

}
