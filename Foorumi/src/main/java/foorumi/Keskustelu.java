package foorumi;

import java.util.*;

public class Keskustelu {
    private int id;
    private Alue alue;
    private List<Viesti> viestit;
    private String nimi;
    
    public Keskustelu(int id, Alue a, List<Viesti> v, String nimi) {
        this.id = id;
        this.alue = a;
        this.nimi = nimi;
        this.viestit = v;
    }
    
    public int getId() {
        return this.id;
    }

    public List<Viesti> getViestit() {
        return viestit;
    }
    
    public Alue getAlueID() {
        return this.alue;
    }
    
    public String getNimi() {
        return this.nimi;
    }
}
