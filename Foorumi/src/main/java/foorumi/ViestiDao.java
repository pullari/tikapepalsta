package foorumi;

import java.sql.*;
import java.util.*;

public class ViestiDao {

    private Database db;

    public ViestiDao(Database d) {
        this.db = d;
    }

    public Viesti haeYksi(String key) throws Exception {
        // tehdään kysely
        Connection con = db.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM Viesti WHERE id = ?");
        stmt.setObject(1, key);
        ResultSet rs = stmt.executeQuery();
        //Jos kysely palauttaa tyhjän taulun palautetaan null
        boolean hasOne = rs.next();

        if (!hasOne) {
            return null;
        }
        // luodaan ja palautetaan viesti
        int id = rs.getInt("id");
        String viesti = rs.getString("viesti");
        String nimimerkki = rs.getString("nimimerkki");
        String aikaleima = rs.getString("aikaleima");

        Viesti v = new Viesti(id, null, viesti, nimimerkki, aikaleima);

        return v;
    }

    public List<Viesti> haeKeskustelunViestit(int key) throws Exception {
        // tehdään kysely
        Connection con = db.getConnection();
        String kysely = "SELECT id, datetime(aikaleima, 'localtime') AS aikaleima, nimimerkki, viesti FROM Viesti WHERE keskusteluID = ? ORDER BY aikaleima";
        PreparedStatement stmt = con.prepareStatement(kysely);
        stmt.setObject(1, key);
        List<Viesti> viestit = new ArrayList<>();
        ResultSet rs = stmt.executeQuery();
        //jos tuloksena tyhjä taulu palautetaan null
        boolean hasOne = rs.isBeforeFirst();
        

        if (!hasOne) {
            return null;
        }
        //luodaan lista viesteistä
        while (rs.next()) {
            int id = rs.getInt("id");
            String viesti = rs.getString("viesti");
            String nimimerkki = rs.getString("nimimerkki");
            String aikaleima = rs.getString("aikaleima");

            viestit.add(new Viesti(id, null, viesti, nimimerkki, aikaleima));

        }

        return viestit;
    }


}
