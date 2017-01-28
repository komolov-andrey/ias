/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import list.PatientItem;

/**
 *
 * @author Андрюха
 */
@ManagedBean(name = "doctor")
@SessionScoped
public class Doctor implements Serializable {

    private String fi;
    private Date selectedDate;
    private String selectedDateString = "";
    private List<PatientItem> patients;
    private PatientItem selectedPatient;

    @PostConstruct
    public void init() {
        setFio();
        patients = setPatientTable();
    }

    public void setFio() {

        db.DataConn db = new db.DataConn();
        db.qeuryRequest("select from Doctors where @rid = '" + User.getId_doctor() + "';");

        String fam = db.queryField("fam").get(0).toString();
        String im = db.queryField("im").get(0).toString();
        setFi(fam + " " + im);

        db.closeConn();
    }

    public String getFi() {
        return fi;
    }

    public void setFi(String fi) {
        this.fi = fi;
    }

    public PatientItem getSelectedPatient() {
        return selectedPatient;
    }

    public void setSelectedPatient(PatientItem selectedPatient) {
        this.selectedPatient = selectedPatient;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getSelectedDateString() {

        if (getSelectedDate() != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            setSelectedDateString(df.format(getSelectedDate()));
        }
        return selectedDateString;
    }

    public void setSelectedDateString(String selectedDateString) {
        this.selectedDateString = selectedDateString;
    }

    public List<PatientItem> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientItem> patients) {
        this.patients = patients;
    }

            
    public List<PatientItem> setPatientTable() {

        List<PatientItem> list = new ArrayList<PatientItem>();
        //add from db
        db.DataConn db = new db.DataConn();
        String s = getSelectedDateString();
        db.qeuryRequest("select from Appoinments where id_doctor = " + User.getId_doctor() + " and date in ('" + s + " 00:00:00');");

        ArrayList times = new ArrayList();

        ArrayList dates = db.queryField("date");

        ArrayList t08_00 = db.queryField("t08_00");
        ArrayList t08_20 = db.queryField("t08_20");
        ArrayList t08_40 = db.queryField("t08_40");
        ArrayList t09_00 = db.queryField("t09_00");
        ArrayList t09_20 = db.queryField("t09_20");
        ArrayList t09_40 = db.queryField("t09_40");
        ArrayList t10_00 = db.queryField("t10_00");
        ArrayList t10_20 = db.queryField("t10_20");
        ArrayList t10_40 = db.queryField("t10_40");
        ArrayList t11_00 = db.queryField("t11_00");
        ArrayList t11_20 = db.queryField("t11_20");
        ArrayList t11_40 = db.queryField("t11_40");
        ArrayList t12_00 = db.queryField("t12_00");
        ArrayList t12_20 = db.queryField("t12_20");
        ArrayList t12_40 = db.queryField("t12_40");
        ArrayList t14_00 = db.queryField("t14_00");
        ArrayList t14_20 = db.queryField("t14_20");
        ArrayList t14_40 = db.queryField("t14_40");
        ArrayList t15_00 = db.queryField("t15_00");
        ArrayList t15_20 = db.queryField("t15_20");
        ArrayList t15_40 = db.queryField("t15_40");
        ArrayList t16_00 = db.queryField("t16_00");
        ArrayList t16_20 = db.queryField("t16_20");
        ArrayList t16_40 = db.queryField("t16_40");

        for (int i = 0; i < dates.size(); i++) {
            if (t08_00.get(i) != null) {
                times.add("08_00");
            }
            if (t08_20.get(i) != null) {
                times.add("08_20");
            }
            if (t08_40.get(i) != null) {
                times.add("08_40");
            }
            if (t09_00.get(i) != null) {
                times.add("09_00");
            }
            if (t09_20.get(i) != null) {
                times.add("09_20");
            }
            if (t09_40.get(i) != null) {
                times.add("09_40");
            }
            if (t10_00.get(i) != null) {
                times.add("10_00");
            }
            if (t10_20.get(i) != null) {
                times.add("10_20");
            }
            if (t10_40.get(i) != null) {
                times.add("10_40");
            }
            if (t11_00.get(i) != null) {
                times.add("11_00");
            }
            if (t11_20.get(i) != null) {
                times.add("11_20");
            }
            if (t11_40.get(i) != null) {
                times.add("11_40");
            }
            if (t12_00.get(i) != null) {
                times.add("12_00");
            }
            if (t12_20.get(i) != null) {
                times.add("12_20");
            }
            if (t12_40.get(i) != null) {
                times.add("12_40");
            }
            if (t14_00.get(i) != null) {
                times.add("14_00");
            }
            if (t14_20.get(i) != null) {
                times.add("14_20");
            }
            if (t14_40.get(i) != null) {
                times.add("14_40");
            }
            if (t15_00.get(i) != null) {
                times.add("15_00");
            }
            if (t15_20.get(i) != null) {
                times.add("15_20");
            }
            if (t15_40.get(i) != null) {
                times.add("15_40");
            }
            if (t16_00.get(i) != null) {
                times.add("16_00");
            }
            if (t16_20.get(i) != null) {
                times.add("16_20");
            }
            if (t16_40.get(i) != null) {
                times.add("16_40");
            }
        }

        DateFormat df;
        for (int i = 0; i < times.size(); i++) {
            db.qeuryRequest("select from Appoinments where id_doctor = " + User.getId_doctor()
                    + " and date in ('" + s + " 00:00:00') and t" + times.get(i) + " = true;");
            ArrayList date = db.queryField("date");
            df = new SimpleDateFormat("dd/MM/yyyy");
            String year = df.format(date.get(0));
            
            ArrayList id_regs = db.queryField("id_regs");
            
            //удалить []
            String id = id_regs.get(0).toString();
            id = id.substring(1, id.length() - 1);
            db.qeuryRequest("select from Regs where @rid = " + id + ";");

            ArrayList fam = db.queryField("fam");
            ArrayList name = db.queryField("name");
            
            list.add(new PatientItem(year, times.get(i).toString(), fam.get(0).toString(), name.get(0).toString()));
        }
        return list;
    }
    
    public void deletePatient() {
        
        patients.remove(selectedPatient);
        
        //добавить в visits
        db.DataConn db = new db.DataConn();
        
        db.qeuryRequest("select from Regs where fam = '" + selectedPatient.getFam() + "' and name = '" + selectedPatient.getIm() + "';");
        String id_u = db.queryField("@rid").get(0).toString();
        
        //1 doctor in 1 hospital
        db.qeuryRequest("select from Doctors where @rid = " + User.getId_doctor() + ";");
        String id_h = db.queryField("id_hosp").get(0).toString();
        id_h = id_h.substring(1, id_h.length() - 1);
        
        db.qeuryRun("INSERT INTO Visits (date, id_regs, id_doctor, id_hosp) VALUES (DATE('" + selectedPatient.getDate() + " 00:00:00'), " + id_u +
                ", " + User.getId_doctor() + ", " + id_h +");");
        
        //удалить из appointments
       db.qeuryRun("delete from Appoinments where id_doctor = " + User.getId_doctor()
                    + " and date in ('" + selectedPatient.getDate() + " 00:00:00') and id_regs = " + id_u + ";");
        
        selectedPatient = null;
    }
}
