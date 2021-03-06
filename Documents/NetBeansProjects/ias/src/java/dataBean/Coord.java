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

@ManagedBean(name = "coord")
@SessionScoped
public class Coord implements Serializable {

    private static String latitude;
    private static String longitude;
    private static String metka;
    
    ArrayList coord = new ArrayList();

    public void setCoord() {

        if (Client.getWhatShow().equals("hosp")) {
            db.DataConn db = new db.DataConn();
            db.qeuryRequest("select from Hospitals where @rid = '" + HospitalInfo.getId_hospital() + "';");

            coord = db.queryField("latitude");
            coord.add(db.queryField("longitude").get(0).toString());

            try {
                setLatitude(coord.get(0).toString());
            } catch (Exception e) {
                setLatitude(null);
            }
            try {
                setLongitude(coord.get(1).toString());
            } catch (Exception e) {
                setLongitude(null);
            }
            try {
                setMetka(db.queryField("name").get(0).toString());
            } catch (Exception e) {
                setMetka(null);
            }

            db.closeConn();
        }

        if (Client.getWhatShow().equals("enp")) {
            db.DataConn db = new db.DataConn();
            db.qeuryRequest("select from Cmos where @rid = '" + EnpInfo.getId_cmo() + "';");

            coord = db.queryField("latitude");
            coord.add(db.queryField("longitude").get(0).toString());

            try {
                setLatitude(coord.get(0).toString());
            } catch (Exception e) {
                setLatitude(null);
            }
            try {
                setLongitude(coord.get(1).toString());
            } catch (Exception e) {
                setLongitude(null);
            }
            try {
                setMetka(db.queryField("name").get(0).toString());
            } catch (Exception e) {
                setMetka(null);
            }

            db.closeConn();
        }
    }

    public String getMetka() {
        return metka;
    }

    public void setMetka(String metka) {
        Coord.metka = metka;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
