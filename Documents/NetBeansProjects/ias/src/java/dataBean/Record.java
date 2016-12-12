/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Андрюха
 */
@ManagedBean
@SessionScoped
public class Record implements Serializable {
    
    private ArrayList<String> areas;
    private String area;    
    private ArrayList<String> hospitals;
    private String hospital;
    private ArrayList<String> cats;
    private String cat;
    private ArrayList<String> doctors;
    private String doctor;
    //подумать
    private Date date;
    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public ArrayList<String> getAreas() {
        
        areas = new ArrayList<String>();
        areas.add("ramenskoe");
        areas.add("bronnitsy");
        return areas;
    }
    
    public ArrayList<String> getHospitals() {
        
        hospitals = new ArrayList<String>();
        hospitals.add("ram");
        hospitals.add("bronn");
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
