/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foorumi;

import java.sql.*;
import java.util.*;

/**
 *
 * @author Pullis
 */
public class DAO {
    
    private Database db;

    public DAO(Database d) {
        this.db = d;
    }
    
    public List<Alue> haeAlueet() throws Exception{
        List<Alue> A = new ArrayList<>();
        Connection con = db.getConnection();
        String kysely = "SELECT Alue.id, Alue.nimi, COUNT(Viesti.id) AS Viestit, datetime(MAX(Viesti.aikaleima), 'localtime') AS Leima "
                + "FROM Alue LEFT JOIN Keskustelu ON Alue.id = Keskustelu.alueID LEFT JOIN Viesti ON Keskustelu.id = Viesti.keskusteluID "
                + "GROUP BY Alue.nimi ORDER BY Viesti.aikaleima DESC";
        PreparedStatement stmt = con.prepareStatement(kysely);
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.isBeforeFirst();
        if (!hasOne) {
            return null;
        }
        while (rs.next()) {
            int id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            int viestit = rs.getInt("viestit");
            String leima = rs.getString("Leima");
            A.add(new Alue(id, nimi, viestit, leima));
        }
        return A;
    }
    //
    public List<Keskustelu> haeAlueenKonvot(int key) throws Exception {
        List<Keskustelu> A = new ArrayList<>();
        Connection con = db.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT Keskustelu.id, Keskustelu.alueID, Keskustelu.avaus, datetime(MAX(Viesti.aikaleima), 'localtime') AS leima, Viesti.viesti AS viesti "
                                                + "FROM Keskustelu LEFT JOIN Viesti "
                                                + "ON Keskustelu.id = Viesti.keskusteluID "
                                                + "WHERE Keskustelu.alueID = " + key + " "
                                                + "GROUP BY keskustelu.id ORDER BY Viesti.aikaleima DESC");

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.isBeforeFirst();
        if (!hasOne) {
            return null;
        }
        while (rs.next()) {
            int id = rs.getInt("id");
            String nimi = rs.getString("avaus");
            int alueID = rs.getInt("alueID");
            String leima = rs.getString("leima");
            String sisalto = rs.getString("viesti");
            
            A.add(new Keskustelu(id, alueID, nimi, sisalto, leima));
        }
        return A;
    }
    
    public List<Keskustelu> haeKonvot() throws Exception {
        List<Keskustelu> A = new ArrayList<>();
        Connection con = db.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT Keskustelu.id, Keskustelu.alueID, Keskustelu.avaus, datetime(MAX(Viesti.aikaleima), 'localtime') AS leima, Viesti.viesti AS viesti "
                                                + "FROM Keskustelu LEFT JOIN Viesti ON Keskustelu.id = Viesti.keskusteluID "
                                                + "GROUP BY keskustelu.id "
                                                + "ORDER BY Viesti.aikaleima DESC");

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.isBeforeFirst();
        if (!hasOne) {
            return null;
        }
        while (rs.next()) {
            int id = rs.getInt("id");
            String nimi = rs.getString("avaus");
            int alueID = rs.getInt("alueID");
            String leima = rs.getString("leima");
            String sisalto = rs.getString("viesti");
            
            A.add(new Keskustelu(id, alueID, nimi, sisalto, leima));
        }
        return A;
    }
    
    public List<Viesti> haeKeskustelunViestit(int key) throws Exception {
        // tehdään kysely
        Connection con = db.getConnection();
        String kysely = "SELECT id, datetime(aikaleima, 'localtime') AS aikaleima, nimimerkki, viesti FROM Viesti WHERE keskusteluID = ? ORDER BY aikaleima DESC";
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
    
    public void uusiAlue(String nimi) throws Exception{
        Connection con = db.getConnection();
        Statement stmt = con.createStatement();
        int rivit = stmt.executeUpdate("INSERT INTO Alue (nimi) VALUES ('" + nimi + "')");
        ResultSet rs = stmt.executeQuery("SELECT * FROM Alue");
        while(rs.next()){
        //    System.out.println(rs.getString("nimi"));
        }
    }
    
    public void uusiViesti(int keskusteluId, String nimimerkki, String viesti) throws Exception{
        Connection con = db.getConnection();
        Statement stmt = con.createStatement();
        int rivit = stmt.executeUpdate("INSERT INTO Viesti (keskusteluID, nimimerkki, viesti) VALUES ('" + keskusteluId +"', '" + nimimerkki +"', '" + viesti + "')");
        ResultSet rs = stmt.executeQuery("SELECT * FROM Viesti");
        while(rs.next()){
         //   System.out.println(rs.getString("viesti"));
         //   System.out.println(rs.getString("keskusteluID"));
        }
    }
    
    public void uusiKonvo(Alue a, String nimi) throws Exception{
        Connection con = db.getConnection();
        Statement stmt = con.createStatement();
        int rivit = stmt.executeUpdate("INSERT INTO Keskustelu (alueID, avaus) VALUES ('" + a.getId() +"', '" + nimi + "')");
        ResultSet rs = stmt.executeQuery("SELECT * FROM KESKUSTELU");
        while(rs.next()){
            System.out.println("JUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
            rs.getString("alueID");
            rs.getString("avaus");
        }  
    }
}