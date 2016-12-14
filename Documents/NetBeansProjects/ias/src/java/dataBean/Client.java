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

    public static void setShowUsl(boolean show) {
        Client.showUsl = show;
    }
//тут дальше перебор что выключать что включать в отображение

    public boolean isShowUsl() {
        return showUsl;
    }

    public void showUsl() {
        setShowUsl(true);
        setShowCost(false);
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, isShow() + "", "test");
        //FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void showNotUsl() {
        setShowUsl(false);
    }

    public static void setShowCost(boolean show) {
        Client.showCost = show;
    }
//тут дальше перебор что выключать что включать в отображение

    public boolean isShowCost() {
        return showCost;
    }

    public void showCost() {
        setShowCost(true);
        setShowUsl(false);
    }

    public void showNotCost() {
        setShowUsl(false);
    }

}
