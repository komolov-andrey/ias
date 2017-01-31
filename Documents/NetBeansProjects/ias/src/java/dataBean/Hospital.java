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
    private String fioDoc;
    private String famDoc;
    private String imDoc;
    private ArrayList<String> doctors;
    private String selectedDateString;
    private Date selectedDate;
    
    private static boolean showStream = false;
    private static boolean showStuff = false;
    private static boolean showMoney = false;

    public String getFamDoc() {
        return famDoc;
    }

    public void setFamDoc(String famDoc) {
        this.famDoc = famDoc;
    }

    public String getImDoc() {
        return imDoc;
    }

    public void setImDoc(String imDoc) {
        this.imDoc = imDoc;
    }

    public String getFioDoc() {
        return fioDoc;
    }

    public void setFioDoc(String fioDoc) {
        this.fioDoc = fioDoc;
    }

    public ArrayList<String> getDoctors() {

        setDoctors();

        return doctors;
    }

    public void setDoctors() {

        doctors = new ArrayList<String>();

        db.DataConn db = new db.DataConn();
        db.qeuryRequest("select from Doctors where id_hosp = " + User.getId_hosp() + ";");
        ArrayList fam = db.queryField("fam");
        ArrayList im = db.queryField("im");

        for (int i = 0; i < fam.size(); i++) {

            doctors.add(fam.get(i).toString() + " " + im.get(i).toString());
        }
        db.closeConn();

    }

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

    public void findDoctor() {
        if (fioDoc != null) {
            String[] mas = new String[2];
            mas = fioDoc.split(" ");
            setFamDoc(mas[0]);
            setImDoc(mas[1]);
        }
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getSelectedDateString() {

        if (getSelectedDate() != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            setSelectedDateString(df.format(getSelectedDate()));
        }
        return selectedDateString;
    }

    public void setSelectedDateString(String selectedDateString) {
        this.selectedDateString = selectedDateString;
    }

    public void addDoctorHoliday() {
        // в appoinments false 
        db.DataConn db = new db.DataConn();

        try {
            if ((getSelectedDateString() != null) && (getFamDoc() != null)) {
                db.qeuryRequest("select from Doctors where id_hosp = " + User.getId_hosp() + " and fam = '"
                        + getFamDoc() + "' and im = '" + getImDoc() + "';");
                String id_doc = db.queryField("@rid").get(0).toString();

                db.qeuryRequest("select from Appoinments where date in Date('" + getSelectedDateString() + " 00:00:00') and id_doctor = " + id_doc + ";");

                if (db.queryField("@rid").size() == 0) {
                    db.qeuryRun("INSERT INTO Appoinments (date, id_doctor, id_regs, "
                            + "t08_00, t08_20, t08_40, t09_00, t09_20, t09_40, t10_00, t10_20, t10_40, t11_00, t11_20, t11_40, "
                            + "t12_00, t12_20, t12_40, t14_00, t14_20, t14_40, t15_00, t15_20, t15_40, t16_00, t16_20, t16_40) "
                            + "VALUES (DATE('" + getSelectedDateString() + " 00:00:00'), " + id_doc + ", null, "
                                    + "false, false, false, false, false, false, false, false, false, false, false, false, "
                                    + "false, false, false, false, false, false, false, false, false, false, false, false);");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Есть клиенты", ""));
                }
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Неизвестная ошибка", ""));
        } finally {
            db.closeConn();
        }
    }

    public boolean isShowStream() {
        return showStream;
    }

    public static void setShowStream(boolean showStream) {
        Hospital.showStream = showStream;
    }

    public boolean isShowStuff() {
        return showStuff;
    }

    public static void setShowStuff(boolean showStuff) {
        Hospital.showStuff = showStuff;
    }

    public boolean isShowMoney() {
        return showMoney;
    }

    public static void setShowMoney(boolean showMoney) {
        Hospital.showMoney = showMoney;
    }
    public void showStream(){
        setShowStream(true);
        setShowMoney(false);
        setShowStuff(false);
    }
    public void showStuff(){
        setShowStream(false);
        setShowMoney(false);
        setShowStuff(true);
    }
    public void showMoney(){
        setShowStream(false);
        setShowMoney(true);
        setShowStuff(false);
    }
}
