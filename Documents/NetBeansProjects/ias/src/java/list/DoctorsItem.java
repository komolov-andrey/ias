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
public class DoctorsItem extends DocItem{
    
    private float percent;
    
    public DoctorsItem(String cat, String fam, String im, float percent) {
        super(cat, fam, im);
        this.percent = percent;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
    
}
