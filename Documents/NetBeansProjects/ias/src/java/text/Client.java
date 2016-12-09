/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package text;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "clientText")
public class Client implements Serializable {

    private String ysl = "Здесь Вы можете оформить заявку на прием к врачу, посмотреть перечень и стоимость оказанных медицинских услуг";
    private String hosp = "Узнайте к какой больнице Вы приклеплены";    
    private String enp = "Узнайте в какой СМО Вы обслуживаетесь";
    private static boolean show = false;

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

    public String getYsl() {
        return ysl;
    }

    public String getHosp() {
        return hosp;
    }

    public String getEnp() {
        return enp;
    }
    
}