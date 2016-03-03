package foorumi;

import java.util.*;

public class Keskustelu {
    private int id;
    private int alueID;
    private List<Viesti> viestit;
    private String nimi;
    private String uusinViesti;
    private String leima;

    public Keskustelu(int id, int a, String nimi, String uv, String leima) {
        this.id = id;
        this.alueID = a;
        this.nimi = nimi;
        this.viestit = new ArrayList<>();
        this.uusinViesti = uv;
        this.leima = leima;  
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setViestit(List<Viesti> nama){
        this.viestit = nama;
    }

    public List<Viesti> getViestit() {
        return viestit;
    }
    
    public int getAlueID() {
        return this.alueID;
    }
    
    public String getNimi() {
        return this.nimi;
    }
    
    public String getUusin(){
        return this.uusinViesti;
    }
    
    public String getLeima(){
        return this.leima;
    }
}
