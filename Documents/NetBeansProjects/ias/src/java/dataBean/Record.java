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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Андрюха
 */
@ManagedBean
@SessionScoped
public class Record implements Serializable {

    private ArrayList<String> citys;
    public static String city;
    private ArrayList<String> hospitals;
    private String hospital;
    private ArrayList<String> cats;
    private String cat;
    private ArrayList<String> doctors;
    private String doctor;
    private Date date;
    private Date time;
    private String dateString;
    private String timeString;

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        DateFormat df = new SimpleDateFormat("HH:mm");
        setTimeString(df.format(time));
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        setDateString(df.format(date));
        this.date = date;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public ArrayList<String> getCitys() {

        citys = new ArrayList<String>();

        db.DataConn db = new db.DataConn();
        db.qeuryRequest("select city from hospitals;");
        citys = db.queryField("city");
        db.closeConn();

        return citys;
    }

    public ArrayList<String> getHospitals() {

        db.DataConn db = new db.DataConn();
        db.qeuryRequest("select name from hospitals where city = '" + city + "';");
        hospitals = db.queryField("name");
        db.closeConn();

        return hospitals;
    }

    public ArrayList<String> getCats() {

        cats = new ArrayList<String>();

        db.DataConn db = new db.DataConn();
        db.qeuryRequest("select from Hospitals where name = '" + getHospital() + "';");
        String id_hosp = db.queryField("@rid").get(0).toString();
        db.qeuryRequest("select id_category from Doctors where id_hosp = " + id_hosp + ";");

        ArrayList id_cats = new ArrayList();
        id_cats = db.queryField("id_category");
        ArrayList<String> id_catsString = new ArrayList<String>();

        for (int i = 0; i < id_cats.size(); i++) {
            String str = id_cats.get(i).toString();
            id_catsString.add(str.substring(1, str.length() - 1));
        }
        db.qeuryRequest("select name from Cat_doc where @rid in (" + id_catsString + ");");

        cats = db.queryField("name");
        db.closeConn();

        return cats;
    }

    public ArrayList<String> getDoctors() {

        doctors = new ArrayList<String>();
        db.DataConn db = new db.DataConn();

        db.qeuryRequest("select from Cat_doc where name = '" + getCat() + "';");
        String id_cat = db.queryField("@rid").get(0).toString();
        db.qeuryRequest("select from Hospitals where name = '" + getHospital() + "';");
        String id_hosp = db.queryField("@rid").get(0).toString();

        db.qeuryRequest("select from Doctors where id_category = " + id_cat + " AND id_hosp = " + id_hosp + ";");
        ArrayList<String> fam = new ArrayList<String>();
        ArrayList<String> name = new ArrayList<String>();

        fam = db.queryField("fam");
        name = db.queryField("im");

        for (int i = 0; i < fam.size(); i++) {
            doctors.add(fam.get(i).toString() + " " + name.get(i).toString());
        }
        db.closeConn();

        return doctors;
    }

    public void save() {
        db.DataConn db = new db.DataConn();
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String d = df.format(date);

            df = new SimpleDateFormat("HH_mm");
            String t = df.format(time);

            String[] fio = getDoctor().split(" ");
            String fam = fio[0];
            String im = fio[1];

            db.qeuryRequest("select from Doctors where fam = '" + fam + "' AND im = '" + im + "';");
            String id_doc = db.queryField("@rid").get(0).toString();

            db.qeuryRequest("select from Appoinments where date in DATE('" + d + " 00:00:00');");
            ArrayList appoinmentDate = db.queryField("date");

            if (appoinmentDate.size() == 0) {
                db.qeuryRun("INSERT INTO Appoinments SET id_regs = " + User.getId_regs() + ", date = DATE('" + d + " 00:00:00'), id_doctor = " + id_doc + ", t" + t + " = true;");
                FacesMessage msg = new FacesMessage("Заявка принята");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                db.qeuryRequest("select from Appoinments where date in DATE('" + d + " 00:00:00') AND (t" + t + " = true OR t" + t + " = false);");
                ArrayList appoinmentTime = db.queryField("t" + t);
                if (appoinmentTime.size() == 0) {
                    db.qeuryRun("INSERT INTO Appoinments SET id_regs = " + User.getId_regs() + ", date = DATE('" + d + " 00:00:00'), id_doctor = " + id_doc + ", t" + t + " = true;");
                    FacesMessage msg = new FacesMessage("Заявка принята");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage("Время занято");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }

        } catch (Exception e) {
            FacesMessage msg = new FacesMessage("Ошибка");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } finally {
            db.closeConn();
            Client.setShowUsl(false);
        }
    }
}
