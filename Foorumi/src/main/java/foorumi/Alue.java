
package foorumi;

import java.util.*;

public class Alue {
    private int id;
    private String nimi;
    private List<Keskustelu> keskustelut;
    
    public Alue(int id, String nimi, List<Keskustelu> k) {
        this.id = id;
        this.nimi = nimi;
        this.keskustelut = k;
    }
    
    public int getId() {
        return id;
    }
    
    public String getNimi() {
        return nimi;
    }
}
