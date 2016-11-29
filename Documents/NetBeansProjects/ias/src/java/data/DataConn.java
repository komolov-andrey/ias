package data;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import java.io.Serializable;
import java.util.jar.Attributes;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "data", eager = true)
@RequestScoped
public class DataConn implements Serializable{

    public DataConn() {
        System.out.println("constructor dune!");
    }
    
    public String test(){
        return "test";
    }
    
    public void getConnection(){
        ODatabaseDocumentTx db = new ODatabaseDocumentTx("remote:localhost/teest").open("admin", "admin");
    
        for (ODocument str : db.browseClass("sellers")) {
        System.out.println(str.field("names").toString());
        }
    }
     
}
