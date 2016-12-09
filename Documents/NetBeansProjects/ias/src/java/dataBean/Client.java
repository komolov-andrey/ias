package dataBean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "client")
public class Client implements Serializable {
    private static boolean showUsl = true;    
    private static boolean showCost = false;

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