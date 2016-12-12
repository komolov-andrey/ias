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

@FacesValidator("validator.CheckDate")
public class CheckDate implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {

        //проверка енп с базы данных (надо заполнить)
        /*
        if (value.toString().length() == 0) {
            FacesMessage message = new FacesMessage("Зполните ЕНП");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        } else {
            db.DataConn db = new db.DataConn();
            db.qeuryRequest("select enp from regs where enp = '" + value.toString().replaceAll(" ","") + "';");
            ArrayList enp = db.queryField("enp");
            db.closeConn();
            if (enp.size() != 1) {
                FacesMessage message = new FacesMessage("ЕНП не найден");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
*/
    }

}
