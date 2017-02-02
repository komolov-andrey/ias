/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package list;

/**
 *
 * @author Андрюха
 */
public class MoneyOmc {

    private int year;
    private String monthStr;
    private int month;
    private float sum;
    private String cmo;

    public MoneyOmc(int year, String monthStr, int month, float sum, String cmo) {
        this.year = year;
        this.monthStr = monthStr;
        this.month = month;
        this.sum = sum;
        this.cmo = cmo;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonthStr() {
        return monthStr;
    }

    public void setMonthStr(String monthStr) {
        this.monthStr = monthStr;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public String getCmo() {
        return cmo;
    }

    public void setCmo(String cmo) {
        this.cmo = cmo;
    }
}