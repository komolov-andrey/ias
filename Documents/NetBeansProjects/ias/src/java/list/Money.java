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
public class Money {

    private LocalDate year;
    private String hosp;
    private float cost;

    public Money(String year, String cost, String hosp) {
        //подумать над датой
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate year1 = LocalDate.parse(year, formatter);
        this.year = year1;
        this.cost = Float.parseFloat(cost);
        this.hosp = hosp;
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

    public float getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

}