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
import javax.faces.validator.ValidatorException;
import org.primefaces.event.FlowEvent;

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
    //подумать
    private Date date;
    private Date time;
    private String dateString;
    private String timeString;

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String s = df.format(date);
        this.dateString = s;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        DateFormat df = new SimpleDateFormat("HH:mm");
        String s = df.format(time);
        this.timeString = s;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
        setTimeString(time.toString());
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        setDateString(date.toString());
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
        
        cats.add("imynolog");
        cats.add("dantist");
        return cats;
    }

    public ArrayList<String> getDoctors() {

        doctors = new ArrayList<String>();
        doctors.add("ivanov");
        doctors.add("petrov");
        return doctors;
    }
}
