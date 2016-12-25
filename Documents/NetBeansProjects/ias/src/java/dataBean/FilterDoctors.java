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
        doctors = createDoctors(10);
    }
     
    public List<DocItem> createDoctors(int size) {
        List<DocItem> list = new ArrayList<DocItem>();
        //add from db
            list.add(new DocItem("кат1", "фам1", "им1"));
            list.add(new DocItem("кат2", "фам2", "им2"));
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
