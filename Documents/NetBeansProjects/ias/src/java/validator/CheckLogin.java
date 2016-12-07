/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validator.CheckLogin")
public class CheckLogin implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {

       //проверка логина с базы данных
        if (value.toString().length() == 0) {
            FacesMessage message = new FacesMessage("Введите логин");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        } else {
            db.DataConn db = new db.DataConn();
            db.qeuryRequest("select login from users where login = '" + value.toString() + "';");
            ArrayList login = db.queryField("login");
            db.closeConn();
            if (login.size() > 0) {
                FacesMessage message = new FacesMessage("Логин занят");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

}
