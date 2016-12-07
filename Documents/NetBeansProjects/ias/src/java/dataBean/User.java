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
    
    private String id_regs;
    private String id_doctor;
    private String id_hosp;
    private String id_cmo;

    public String getId_regs() {
        return id_regs;
    }

    public void setId_regs(String id_regs) {
        this.id_regs = id_regs;
    }

    public String getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(String id_doctor) {
        this.id_doctor = id_doctor;
    }

    public String getId_hosp() {
        return id_hosp;
    }

    public void setId_hosp(String id_hosp) {
        this.id_hosp = id_hosp;
    }

    public String getId_cmo() {
        return id_cmo;
    }

    public void setId_cmo(String id_cmo) {
        this.id_cmo = id_cmo;
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
                    setId_cmo(cmos.get(0).toString());
                    return "cmo?faces-redirect=true";
                }
                if (user.get(0) != null) {
                    setId_regs(user.get(0).toString());
                    return "client?faces-redirect=true";
                }
                if (doctor.get(0) != null) {
                    setId_doctor(doctor.get(0).toString());
                    return "doctor?faces-redirect=true";
                }
                if (hospital.get(0) != null) {
                    setId_hosp(hospital.get(0).toString());
                    return "hospital?faces-redirect=true";
                }
                if (admin.get(0).equals(true)) {
                    return "government?faces-redirect=true";
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Неверное имя пользователя или пароль!"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Неверное имя пользователя или пароль!"));
        }

        return "";
    }
}
