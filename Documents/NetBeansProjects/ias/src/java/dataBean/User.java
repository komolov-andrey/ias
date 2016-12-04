package dataBean;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.SessionScoped;
import javax.faces.validator.ValidatorException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

@ManagedBean(name = "user")
@SessionScoped
public class User implements Serializable {

    private String username;
    private String password;
    private static boolean isShow;

    public boolean isIsShow() {
        return isShow = true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String roleEnter() {

        //проверка логина с базы данных
        db.DataConn db = new db.DataConn();
        db.qeuryRequest("select from users where login = '" + getUsername() + "';");
        ArrayList login = db.queryField("login");
        ArrayList pwd = db.queryField("pwd");

        ArrayList user = db.queryField("id_regs");
        ArrayList doctor = db.queryField("id_doctor");
        ArrayList admin = db.queryField("isAdmin");
        ArrayList hospital = db.queryField("id_hosp");
        ArrayList cmos = db.queryField("id_cmo");
        db.closeConn();

        if (login.size() == 1) {
            if ((login.get(0).equals(getUsername())) && (pwd.get(0).equals(getPassword()))) {
                if (cmos.get(0) != null) {
                    return "cmo?faces-redirect=true";
                }
                if (user.get(0) != null) {
                    return "client?faces-redirect=true";
                }
                if (doctor.get(0) != null) {
                    return "doctor?faces-redirect=true";
                }
                if (hospital.get(0) != null) {
                    return "hospital?faces-redirect=true";
                }
                if (admin.get(0).equals(true)) {
                    return "government?faces-redirect=true";
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Неверное имя пользователя или пароль!"));
            }}else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Неверное имя пользователя или пароль!"));
            }

            return "";
    }
}
