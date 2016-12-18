/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBean;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "hosp")
@SessionScoped
public class HospitalInfo implements Serializable {

    private String obl;
    private String raion;
    private String city;
    private String street;
    private String home;
    private String name;
    private static String id_hospital;

    public void setHospitalInfo() {

        db.DataConn db = new db.DataConn();
        db.qeuryRequest("select id_hosp from Regs where @rid = '" + User.getId_regs() + "';");

        ArrayList id_hosp = db.queryField("id_hosp");
        ArrayList address = new ArrayList();
        ArrayList nameArray = new ArrayList();
        if (id_hosp.size() > 0) {
            //удалить []
            setId_hospital(id_hosp.get(0).toString());
            setId_hospital(getId_hospital().substring(1, id_hospital.length() - 1));
            db.qeuryRequest("select from Hospitals where @rid = '" + getId_hospital() + "';");

            Coord c = new Coord();
            c.setCoord();

            try {
                address.add(db.queryField("oblast").get(0).toString());
            } catch (Exception e) {
                address.add("");
            }
            try {
                address.add(db.queryField("raion").get(0).toString());
            } catch (Exception e) {
                address.add("");
            }
            try {
                address.add(db.queryField("city").get(0).toString());
            } catch (Exception e) {
                address.add("");
            }
            try {
                address.add(db.queryField("street").get(0).toString());
            } catch (Exception e) {
                address.add("");
            }
            try {
                address.add(db.queryField("home").get(0).toString());
            } catch (Exception e) {
                address.add("");
            }

            try {
             nameArray.add(db.queryField("name").get(0).toString());
                setName(nameArray.get(0).toString());
            } catch (Exception e) {
            }

            if (!address.get(0).equals("")) {
                setObl(address.get(0).toString());
            } else {
                setObl("---");
            }
            if (!address.get(1).equals("")) {
                setRaion(address.get(1).toString());
            } else {
                setRaion("---");
            }
            if (!address.get(2).equals("")) {
                setCity(address.get(2).toString());
            } else {
                setCity("---");
            }
            if (!address.get(3).equals("")) {
                setStreet(address.get(3).toString());
            } else {
                setStreet("---");
            }
            if (!address.get(4).equals("")) {
                setHome(address.get(4).toString());
            } else {
                setHome("---");
            }
        }

        //  db.closeConn();
        //закрывается в coord
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getId_hospital() {
        return id_hospital;
    }

    public static void setId_hospital(String id_hospital) {
        HospitalInfo.id_hospital = id_hospital;
    }

    public String getObl() {
        return obl;
    }

    public void setObl(String obl) {
        this.obl = obl;
    }

    public String getRaion() {
        return raion;
    }

    public void setRaion(String raion) {
        this.raion = raion;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }
}
