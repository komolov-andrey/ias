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
public class GovMoneyItem extends MoneyOmc{

    
    private String hosp;

    public GovMoneyItem(String hosp, int year, String monthStr, int month, float sum, String cmo) {
        super(year, monthStr, month, sum, cmo);
        this.hosp = hosp;
    }


    public String getHosp() {
        return hosp;
    }

    public void setHosp(String hosp) {
        this.hosp = hosp;
    }    
    
}