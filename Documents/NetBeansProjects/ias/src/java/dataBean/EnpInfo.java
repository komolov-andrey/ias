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

@ManagedBean(name = "enpInfo")
@SessionScoped
public class EnpInfo implements Serializable {
    
    private String cmo;
    private static String id_cmo;    
    private String phone;
    private String address;

        public void setEnpInfo() {

        db.DataConn db = new db.DataConn();
        db.qeuryRequest("select id_cmo from Regs where @rid = '" + User.getId_regs() + "';");

        ArrayList id_cmoArray = db.queryField("id_cmo");
        ArrayList addressArray = new ArrayList();
        ArrayList phoneArray = new ArrayList();
        ArrayList cmoArray = new ArrayList();
        
        if (id_cmoArray.size() > 0) {
            //удалить []
            setId_cmo(id_cmoArray.get(0).toString());
            setId_cmo(getId_cmo().substring(1, getId_cmo().length() - 1));
            db.qeuryRequest("select from Cmos where @rid = '" + getId_cmo() + "';");

            Coord c = new Coord();
            c.setCoord();

            try {
                cmoArray.add(db.queryField("name").get(0).toString());
                setCmo(cmoArray.get(0).toString());
            } catch (Exception e) {
                setCmo("---");
            }
            
            try {
                addressArray.add(db.queryField("address").get(0).toString());
                setAddress(addressArray.get(0).toString());
            } catch (Exception e) {
                setAddress("---");
            }
            try {
                phoneArray.add(db.queryField("phone").get(0).toString());
                setPhone(phoneArray.get(0).toString());
            } catch (Exception e) {
                setPhone("");
            }
           
        }

        //  db.closeConn();
        //закрывается в coord
    }

    public static String getId_cmo() {
        return id_cmo;
    }

    public void setId_cmo(String id_cmo) {
        this.id_cmo = id_cmo;
    }
    
    public String getCmo() {
        return cmo;
    }

    public void setCmo(String cmo) {
        this.cmo = cmo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
