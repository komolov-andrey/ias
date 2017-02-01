/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import list.StreamItem;

/**
 *
 * @author Андрюха
 */
@ManagedBean(name = "stream")
@SessionScoped
public class StreamPatient implements Serializable {

    private List<StreamItem> visits;
    Map<String, Integer> dictTime;

    @PostConstruct
    public void init() {
        visits = setVisitsTable();
    }

    public StreamPatient() {

        dictTime = new HashMap<String, Integer>();
        dictTime.put("08:00-08:59", 0);
        dictTime.put("09:00-09:59", 0);
        dictTime.put("10:00-10:59", 0);
        dictTime.put("11:00-11:59", 0);
        dictTime.put("12:00-12:59", 0);
        dictTime.put("14:00-14:59", 0);
        dictTime.put("15:00-15:59", 0);
        dictTime.put("16:00-16:59", 0);
    }

    public List<StreamItem> setVisitsTable() {

        List<StreamItem> list = new ArrayList<StreamItem>();

        db.DataConn db = new db.DataConn();
        db.qeuryRequest("SELECT date, COUNT(*) as count FROM visits LET $time =  date.format('HH:mm') where id_hosp = " + User.getId_hosp() + " GROUP BY $time;");
        ArrayList date = db.queryField("date");

        ArrayList dateTime = new ArrayList();

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (int i = 0; i < date.size(); i++) {

            String dateString = df.format(date.get(i));
            String yeat = dateString.substring(0, 10);
            String time = dateString.substring(11, dateString.length());

            int count = Integer.parseInt(db.queryField("count").get(i).toString());

            int min = Integer.parseInt(time.substring(0, 2));
            int sek = Integer.parseInt(time.substring(3, 5));

            if ((min == 8) && (sek >= 0) && (sek <= 59)) {
                dictTime.replace("08:00-08:59", dictTime.get("08:00-08:59") + count);
            }
            if ((min == 9) && (sek >= 0) && (sek <= 59)) {
                dictTime.replace("09:00-09:59", dictTime.get("09:00-09:59") + count);
            }
            if ((min == 10) && (sek >= 0) && (sek <= 59)) {
                dictTime.replace("10:00-10:59", dictTime.get("10:00-10:59") + count);
            }
            if ((min == 11) && (sek >= 0) && (sek <= 59)) {
                dictTime.replace("11:00-11:59", dictTime.get("11:00-11:59") + count);
            }
            if ((min == 12) && (sek >= 0) && (sek <= 59)) {
                dictTime.replace("12:00-12:59", dictTime.get("12:00-12:59") + count);
            }
            if ((min == 14) && (sek >= 0) && (sek <= 59)) {
                dictTime.replace("14:00-14:59", dictTime.get("14:00-14:59") + count);
            }
            if ((min == 15) && (sek >= 0) && (sek <= 59)) {
                dictTime.replace("15:00-15:59", dictTime.get("15:00-15:59") + count);
            }
            if ((min == 16) && (sek >= 0) && (sek <= 59)) {
                dictTime.replace("16:00-16:59", dictTime.get("16:00-16:59") + count);
            }
        }

        int sum = 0;
        for (int s : dictTime.values()) {
            sum += s;
        }
        if (sum == 0){
            sum=1;
        }
        
        for (Map.Entry<String, Integer> entry : dictTime.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            float f = value*100/sum;
            list.add(new StreamItem(key, value, f));
        }

        return list;
    }

    public List<StreamItem> getVisits() {
        return visits;
    }

    public void setVisits(List<StreamItem> visits) {
        this.visits = visits;
    }

}
