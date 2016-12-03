package dataBean;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "user")
@SessionScoped
public class User implements Serializable {

    private String username;
    private String password;

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
        db.closeConn();
        
        //if (login.size() == 1){
        //додумать!)
            if ((login.get(0).equals(getUsername()))&&(pwd.get(0).equals(getPassword()))){
                if (!user.get(0).equals(null))
                    return "client?faces-redirect=true";
                if (!doctor.get(0).equals(null))
                    return "doctor?faces-redirect=true";
                if (!hospital.get(0).equals(null))
                    return "hospital?faces-redirect=true";
                if (admin.get(0).equals(true))
                    return "cmo?faces-redirect=true";//test
            }
        

                
       // }else{
           //тут прикрутить growl
        //}
        //switch Параметр из бд по username passwd
        
        return "#";
    }

}
