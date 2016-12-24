/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package list;

public class ListItem {

    private String cat;
    private String fam;
    private String im;

    public ListItem(String cat, String fam, String im) {
        this.cat = cat;
        this.fam = fam;
        this.im = im;
    }

    public String getCat() {
        return cat;
    }

    public String getFam() {
        return fam;
    }
    
    public String getIm() {
        return im;
    }
}
