package dataBean;

import static com.sun.faces.facelets.util.Path.context;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "client")
@SessionScoped
public class Client implements Serializable {

    private static String fio;

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
            
            db.closeConn();
    }

    public String getFio() {
        if (fio == null) {
            setFio();
        }
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
    }
    
    public void showENP() {
        setShowCost(false);
        setShowUsl(false);
        setShowHospital(false);
        setShowENP(true);
    }

}
