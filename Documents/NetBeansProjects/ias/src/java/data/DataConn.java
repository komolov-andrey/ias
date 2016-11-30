package data;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class DataConn{

    public DataConn() {
    }
    
    public void setConnection(){
        ODatabaseDocumentTx db = new ODatabaseDocumentTx("remote:localhost/teest").open("admin", "admin");
    
        for (ODocument str : db.browseClass("sellers")) {
        System.out.println(str.field("names").toString());
        }
    }
     
}
