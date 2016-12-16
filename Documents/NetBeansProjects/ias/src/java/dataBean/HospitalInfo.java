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

    private String latitude;
    private String longitude;
    private String name;

    public HospitalInfo() {

        if ((getLatitude() == null) || (getLongitude() == null)) {
            db.DataConn db = new db.DataConn();
            db.qeuryRequest("select id_hosp from Regs where @rid = '" + User.getId_regs() + "';");

            ArrayList id_hosp = db.queryField("id_hosp");
            ArrayList coord = new ArrayList();            
            ArrayList name = new ArrayList();
            if (id_hosp.size() > 0) {
                //удалить []
                String str = id_hosp.get(0).toString();
                db.qeuryRequest("select from Hospitals where @rid = '" + str.substring(1, str.length() - 1) + "';");
                coord = db.queryField("latitude");
                coord.add(db.queryField("longitude").get(0).toString());
                name = db.queryField("name");
                if (name != null)
                    setName(name.get(0).toString());
            }

            if (coord.size() > 1) {
                setLatitude(coord.get(0).toString());
                setLongitude(coord.get(1).toString());
            } else {
                setLatitude(null);
                setLongitude(null);
            }

            db.closeConn();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
