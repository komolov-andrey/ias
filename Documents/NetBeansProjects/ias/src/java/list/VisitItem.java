/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package list;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Андрюха
 */
public class VisitItem {

    private LocalDate year;
    private String hosp;
    private String doc;
    private String cat;
    private int cost;
    private String year2;
    private String year3;

    public VisitItem(String year, String hosp, String doc, String cat, String cost) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate year1 = LocalDate.parse(year, formatter);
        this.year = year1;
        this.hosp = hosp;
        this.doc = doc;
        this.cat = cat;
        this.cost = Integer.parseInt(cost);
    }

    public String getYear3() {
        return year3;
    }

    public void setYear3(String year3) {
        this.year3 = year3;
    }

    public String getYear2() {
        return year2;
    }

    public void setYear2(String year2) {
        this.year2 = year2;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {

        this.year = year;
    }

    public String getHosp() {
        return hosp;
    }

    public void setHosp(String hosp) {
        this.hosp = hosp;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

}
