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

        //записать логин / пароль в БД
        db.DataConn db = new db.DataConn();
        db.qeuryRequest("select from Regs where enp = " + getEnp().replaceAll(" ", "") + ";");
        
        ArrayList id_enp = db.queryField("@rid");
        String s = id_enp.get(0).toString();
        
        db.qeuryRun("DELETE FROM Users WHERE id_regs = " + s + ";");

        db.qeuryRun("INSERT INTO users (id_regs, login, pwd) VALUES ((select @rid from regs where enp = '"
                + getEnp().replaceAll(" ", "") + "'), '" + getLogin() + "', '" + getPassword() + "');");

        db.closeConn();

        return "index?faces-redirect=true";
    }
}
