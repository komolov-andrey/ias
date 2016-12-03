package dataBean;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "userReg")
@SessionScoped
public class UserReg implements Serializable {

    private String enp;
    private String login;
    private String password;

    public String getEnp() {
        return enp;
    }

    public void setEnp(String enp) {
        this.enp = enp;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String submit() {
        
        //conn к бд с сохранением логина и пароля
        //если ок то ретёрн index
        //else registration.xhtml
        return "index?faces-redirect=true";
    }
}
