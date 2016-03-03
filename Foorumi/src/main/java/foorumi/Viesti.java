package foorumi;


public class Viesti {
    private int id;
    private Keskustelu keskustelu;
    private String viesti;
    private String nimimerkki;
    private String aikaleima;
    
    public Viesti(int id, Keskustelu k, String v, String n, String a) {
        this.id = id;
        this.keskustelu = k;
        this.viesti = v;
        this.nimimerkki = n;
        this.aikaleima = a;
    }

    public int getId() {
        return this.id;
    }

    public Keskustelu getKeskusteluID() {
        return this.keskustelu;
    }

    public String getViesti() {
        return this.viesti;
    }

    public String getNimimerkki() {
        return this.nimimerkki;
    }

    public String getAikaleima() {
        return this.aikaleima;
    }
}
