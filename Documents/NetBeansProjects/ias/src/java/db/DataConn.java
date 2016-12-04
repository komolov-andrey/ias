package db;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLQuery;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import java.util.ArrayList;
import java.util.List;

public class DataConn {

    ODatabaseDocumentTx db;
    List<ODocument> result;

    public DataConn() {
    }

    public void qeuryRequest(String sql) {

        db = new ODatabaseDocumentTx("remote:localhost/ias").open("admin", "admin");
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
}
