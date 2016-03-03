
package foorumi;

import java.util.*;

public class Alue {
    private int id;
    private String nimi;
    private int viesteja;
    private List<Keskustelu> keskustelut;
    private String leima;
    
    public Alue(int id, String nimi, int viestit, String leima) {
        this.id = id;
        this.nimi = nimi;
        this.keskustelut = new ArrayList<>();
        this.viesteja = viestit;
        this.leima = leima;
    }
    
    public List<Keskustelu> getKeskustelut(){
        return keskustelut;
    }

    public int getViesteja() {
        return viesteja;
    }
    
    public void setKeskustelut(List<Keskustelu> konvot){
        this.keskustelut = konvot;
    }
    
    public void lisaaKeskustelu(Keskustelu uusi){
        this.keskustelut.add(uusi);
    }
    
    public int getId() {
        return id;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public String getLeima(){
        return leima;
    }
}
