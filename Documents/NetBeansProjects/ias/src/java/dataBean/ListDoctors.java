/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import list.DoctorsItem;

@ManagedBean(name = "listDoctors")
@ViewScoped
public class ListDoctors implements Serializable {

    private List<DoctorsItem> doctors;
    Map<String, Integer> dictDoc = new HashMap<String, Integer>();

    @PostConstruct
    public void init() {
        doctors = createDoctors();
    }

    public List<DoctorsItem> createDoctors() {

        List<DoctorsItem> list = new ArrayList<DoctorsItem>();

        db.DataConn db = new db.DataConn();

        db.qeuryRequest("select count(*) as count, id_doctor from Visits where id_hosp = " + User.getId_hosp() + " group by id_doctor;");
        for (int i = 0; i < db.getResult().size(); i++) {
            //словарь id_doc->count
            String id = db.queryField("id_doctor").get(i).toString();
            id = id.substring(1, id.length() - 1);
            int count = Integer.parseInt(db.queryField("count").get(i).toString());
            dictDoc.put(id, count);
        }

        int sum = 0;
        for (int s : dictDoc.values()) {
            sum += s;
        }
        if (sum == 0) {
            sum = 1;
        }

        db.qeuryRequest("select * from doctors where id_hosp = " + User.getId_hosp() + ";");

        ArrayList id_hosp = db.queryField("@rid");
        ArrayList fam = db.queryField("fam");
        ArrayList im = db.queryField("im");
        ArrayList category = db.queryField("id_category");

        try {
            for (int i = 0; i < id_hosp.size(); i++) {
                String id_cat = category.get(i).toString();
                id_cat = id_cat.substring(1, id_cat.length() - 1);

                db.qeuryRequest("select from Cat_doc where @rid = " + id_cat + ";");

                try {
                    String id = id_hosp.get(i).toString();
                    float f = (float) dictDoc.get(id_hosp.get(i).toString()) * 100 / sum;
                    list.add(new DoctorsItem(db.queryField("name").get(0).toString(), fam.get(i).toString(), im.get(i).toString(), f));
                } catch (Exception e) {
                    list.add(new DoctorsItem(db.queryField("name").get(0).toString(), fam.get(i).toString(), im.get(i).toString(), 0));
                }
            }
        } catch (Exception e) {

        } finally {
            db.closeConn();
        }

        return list;
    }

    public List<DoctorsItem> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorsItem> doctors) {
        this.doctors = doctors;
    }
}
