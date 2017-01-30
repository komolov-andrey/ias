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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Андрюха
 */
@ManagedBean(name = "hospital")
@SessionScoped
public class Hospital implements Serializable {

    private String name;
    private String countClients;
    private String enp;
    private String fam;
    private String im;
    private String dr;

    public String getName() {
        setName();
        return name;
    }

    public void setName() {

        db.DataConn db = new db.DataConn();
        db.qeuryRequest("select name from Hospitals where @rid = '" + User.getId_hosp() + "';");
        ArrayList nameHosp = db.queryField("name");
        db.closeConn();

        this.name = nameHosp.get(0).toString();
    }

    public String getCountClients() {
        setCountClients();
        return countClients;
    }

    public void setCountClients() {
        db.DataConn db = new db.DataConn();
        db.qeuryRequest("select count(*) as count from regs where id_hosp=" + User.getId_hosp() + ";");
        String count = db.queryField("count").get(0).toString();
        db.closeConn();

        this.countClients = count;
    }

    public String getEnp() {
        return enp;
    }

    public void setEnp(String enp) {
        this.enp = enp;
    }

    public String getFam() {
        return fam;
    }

    public void setFam(String fam) {
        this.fam = fam;
    }

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public String getDr() {
        return dr;
    }

    public void setDr(String dr) {
        this.dr = dr;
    }

    public void findClient() {

        db.DataConn db = new db.DataConn();

        try {
            db.qeuryRequest("select from regs where enp = '" + getEnp().replace(" ", "") + "';");
            int size = db.queryField("fam").size();

            if (size != 0) {
                setFam(db.queryField("fam").get(0).toString());
                setIm(db.queryField("name").get(0).toString());
                
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                setDr(df.format(db.queryField("birthday").get(0)));

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Клиент не найден", ""));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Неизвестная ошибка", ""));
        } finally {
            db.closeConn();
        }
    }

    public void addClient() {
        db.DataConn db = new db.DataConn();

        try {
            if ((getEnp() != null) && (getFam() != null)) {
                db.qeuryRun("UPDATE regs SET id_hosp = " + User.getId_hosp() + " where enp = '" + getEnp().replace(" ", "") + "';");
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Неизвестная ошибка", ""));
        } finally {
            db.closeConn();
        }
    }
}
