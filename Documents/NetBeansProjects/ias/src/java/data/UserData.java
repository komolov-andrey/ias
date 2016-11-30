package data;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "userData")
@SessionScoped
public class UserData implements Serializable {

   public String data;

   public String getData() {
      return data;
   }

   public void setData(String data) {
      this.data = data;
   }
}
