package dataBean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "client")
@SessionScoped
public class Client implements Serializable {

    private static String fio;
    private static String fam;
    private static String name;
    private static String enp;
    private static String dr;
    private static String sex;
    private static String whatShow = "";

    private static boolean showUsl = false;
    private static boolean showCost = false;
    private static boolean showHospital = false;
    private static boolean showENP = false;

    public void setFio() {
        db.DataConn db = new db.DataConn();
        db.qeuryRequest("select from regs where @rid = '" + User.getId_regs() + "';");
        ArrayList fio = db.queryField("fam");
        fio.add(db.queryField("name").get(0).toString());
        this.fio = fio.get(1) + " " + fio.get(0);

        setFam(fio.get(0).toString());
        setName(fio.get(1).toString());

        ArrayList dr_sex = db.queryField("birthday");
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String s = df.format(dr_sex.get(0));
        
        dr_sex.add(db.queryField("gender").get(0).toString());
        setDr_Sex(s);
        
        if (dr_sex.get(1).toString().equals("true")) {
            setDr_Sex(getDr_Sex() + ", "+ "M");
        }
        if (dr_sex.get(1).toString().equals("false")) {
            setDr_Sex(getDr_Sex() + ", "+ "Ж");
        }
        setEnp(db.queryField("enp").get(0).toString());

        db.closeConn();
    }

    public String getEnp() {
        return enp;
    }

    public void setEnp(String enp) {
        Client.enp = enp;
    }

    public String getDr_Sex() {
        return dr;
    }

    public void setDr_Sex(String dr) {
        Client.dr = dr;
    }

    public String getFam() {
        return fam;
    }

    public void setFam(String fam) {
        Client.fam = fam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Client.name = name;
    }

    public static String getWhatShow() {
        return whatShow;
    }

    public static void setWhatShow(String whatShow) {
        Client.whatShow = whatShow;
    }

    public String getFio() {
            setFio();
        return fio;
    }

    public boolean isShowHospital() {
        return showHospital;
    }

    public static void setShowHospital(boolean showHospital) {
        Client.showHospital = showHospital;
    }

    public boolean isShowENP() {
        return showENP;
    }

    public static void setShowENP(boolean showENP) {
        Client.showENP = showENP;
    }

    public boolean isShowUsl() {
        return showUsl;
    }

    public static void setShowUsl(boolean show) {
        Client.showUsl = show;
    }

    public boolean isShowCost() {
        return showCost;
    }

    public static void setShowCost(boolean show) {
        Client.showCost = show;
    }

    public void showUsl() {
        setShowUsl(true);
        setShowCost(false);
        setShowHospital(false);
        setShowENP(false);
    }

    public void showCost() {
        setShowCost(true);
        setShowUsl(false);
        setShowHospital(false);
        setShowENP(false);

    }

    public void showHospital() {
        setShowCost(false);
        setShowUsl(false);
        setShowENP(false);
        setShowHospital(true);

        setWhatShow("hosp");
    }

    public void showENP() {
        setShowCost(false);
        setShowUsl(false);
        setShowHospital(false);
        setShowENP(true);

        setWhatShow("enp");
    }

}
