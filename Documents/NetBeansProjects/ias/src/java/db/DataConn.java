package db;

import com.orientechnologies.orient.core.command.OCommandRequest;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.query.OSQLQuery;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import java.util.ArrayList;
import java.util.List;

public class DataConn {

    ODatabaseDocumentTx db;
    List<ODocument> result;
    

    public DataConn() {
        
        db = new ODatabaseDocumentTx("remote:localhost/ias").open("admin", "admin");
    }

    public void qeuryRequest(String sql) {

        result = db.query(
                new OSQLSynchQuery<ODocument>(sql));
    }

    public ArrayList queryField(String field) {

        ArrayList listResult = new ArrayList();
        for (int i = 0; i < result.size(); i++) {

            listResult.add(result.get(i).field(field));
        }
        return listResult;
    }

    public void closeConn() {
        db.close();
    }
    
    public void qeuryRun(String sql) {

        db.command(new OCommandSQL(sql)).execute();
    }
}
