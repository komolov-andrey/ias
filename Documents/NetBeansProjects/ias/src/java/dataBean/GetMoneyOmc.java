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
import list.MoneyOmc;

@ManagedBean(name = "moneyOmc")
@ViewScoped
public class GetMoneyOmc implements Serializable {

    private List<MoneyOmc> money;
    private List<MoneyOmc> filterMoney;
    Map<String, String> dictMsk = new HashMap<String, String>();
    Map<Integer, String> dictMonth = new HashMap<Integer, String>();

    @PostConstruct
    public void init() {
        money = createTable();
    }
    public GetMoneyOmc(){
        
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

    public List<MoneyOmc> createTable() {

        List<MoneyOmc> list = new ArrayList<MoneyOmc>();

        db.DataConn db = new db.DataConn();

        db.qeuryRequest("select from Cmos;");
        for (int i = 0; i < db.getResult().size(); i++) {
            //словарь id_msk->name
            String id = db.queryField("@rid").get(i).toString();
            String name = db.queryField("name").get(i).toString();
            dictMsk.put(id, name);
        }

        db.qeuryRequest("select from Get_money where id_hosp = " + User.getId_hosp() + ";");

        ArrayList id_msk = db.queryField("id_msk");
        ArrayList date = db.queryField("date");
        ArrayList sum = db.queryField("sum");

        DateFormat df_y = new SimpleDateFormat("yyyy");
        DateFormat df_m = new SimpleDateFormat("MM");
        try {
            for (int i = 0; i < db.getResult().size(); i++) {
                int year = Integer.parseInt(df_y.format(date.get(i)));
                int month = Integer.parseInt(df_m.format(date.get(i)));
                String monthStr = dictMonth.get(month);
                float count = Float.parseFloat(sum.get(i).toString());
                String id = id_msk.get(i).toString();
                id = id.substring(1, id.length() - 1);
                String nameOmc = dictMsk.get(id);
                
                list.add(new MoneyOmc(year, monthStr, month, count, nameOmc));
            }
        } catch (Exception e) {

        } finally {
            db.closeConn();
        }

        return list;
    }

    public List<MoneyOmc> getMoney() {
        return money;
    }

    public void setMoney(List<MoneyOmc> money) {
        this.money = money;
    }

    public Map<String, String> getDictMsk() {
        return dictMsk;
    }

    public void setDictMsk(Map<String, String> dictMsk) {
        this.dictMsk = dictMsk;
    }

    public Map<Integer, String> getDictMonth() {
        return dictMonth;
    }

    public void setDictMonth(Map<Integer, String> dictMonth) {
        this.dictMonth = dictMonth;
    }

    public List<MoneyOmc> getFilterMoney() {
        return filterMoney;
    }

    public void setFilterMoney(List<MoneyOmc> filterMoney) {
        this.filterMoney = filterMoney;
    }
    
}
