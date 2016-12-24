/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBean;

import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import list.ListItem;
 
@ManagedBean(name="doctorList")
@ViewScoped
public class DoctorList implements Serializable {
     
    private ArrayList<ListItem> doctors;

    public ArrayList<ListItem> getDoctors() {
        doctors = new ArrayList<ListItem>();
        doctors.add(new ListItem("категория", "фамилия", "имя"));
        return doctors;
    }

    public void setDoctors(ArrayList<ListItem> doctors) {        
        this.doctors = doctors;
    }

}