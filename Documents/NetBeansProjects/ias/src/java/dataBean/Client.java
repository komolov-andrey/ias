package dataBean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "clientText")
public class Client implements Serializable {
    private static boolean show = true;

    public static void setShow(boolean show) {
        Client.show = show;
    }

    public boolean isShow() {
        return show;
    }

    public void changeshow() {
        if (isShow() == false) {
            setShow(true);
        } else {
            setShow(false); 
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, isShow() + "", "test");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}