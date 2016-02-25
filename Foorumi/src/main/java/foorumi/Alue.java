
package foorumi;

import java.util.*;

public class Alue {
    private int id;
    private String nimi;
    private int viesteja;
    private List<Keskustelu> keskustelut;
    
    public Alue(int id, String nimi, int viestit, List<Keskustelu> k) {
        this.id = id;
        this.nimi = nimi;
        this.keskustelut = k;
        this.viesteja = viestit;
    }

    public int getViesteja() {
        return viesteja;
    }
    
    public int getId() {
        return id;
    }
    
    public String getNimi() {
        return nimi;
    }
}
