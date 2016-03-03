
package foorumi;

import java.net.URI;
import java.sql.*;
import java.util.*;

public class Database {


    private Connection connection;
    private String databaseAddress;
    
    public Database(String address) throws Exception {
        this.connection = DriverManager.getConnection(address);
        this.databaseAddress = address;
    }
    
    private void init() {
        List<String> lauseet = null;
        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }
        return DriverManager.getConnection(databaseAddress);
    }

    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
//        lista.add("DROP TABLE Tuote;");
        // heroku käyttää SERIAL-avainsanaa uuden tunnuksen automaattiseen luomiseen
//        lista.add("CREATE TABLE Tuote (id SERIAL PRIMARY KEY, nimi varchar(255));");
//        lista.add("INSERT INTO Tuote (nimi) VALUES ('postgresql-tuote');");
        
        lista.add("DROP TABLE Alue");
        lista.add("DROP TABLE Keskustelu");
        lista.add("DROP TABLE Viesti");
        
        lista.add("CREATE TABLE Alue (id SERIAL PRIMARY KEY, nimi varchar(50));");
        lista.add("CREATE TABLE Keskustelu (id SERIAL PRIMARY KEY, alueID INTEGER, avaus varchar(140), FOREIGN KEY (alueID) REFERENCES Alue(id));");
        lista.add("CREATE TABLE Viesti (id SERIAL PRIMARY KEY, keskusteluID INTEGER, nimimerkki varchar(50), viesti TEXT, aikaleima TIMESTAMP DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (keskusteluID) REFERENCES Keskustelu(id));");

        lista.add("INSERT INTO Alue (nimi) VALUES ('uusi');");
        lista.add("INSERT INTO Keskustelu (alueID, avaus) VALUES ('1', 'uusi');");
        lista.add("INSERT INTO Viesti (keskusteluID, nimimerkki, viesti) VALUES ('1', 'pullis', 'uusi');");
        
        
        return lista;
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Tuote (id integer PRIMARY KEY, nimi varchar(255));");
        lista.add("INSERT INTO Tuote (nimi) VALUES ('sqlite-tuote');");

        lista.add("CREATE TABLE Alue (id integer PRIMARY KEY, nimi varchar(50));");
        lista.add("CREATE TABLE Keskustelu (id integer PRIMARY KEY, alueID INTEGER, avaus varchar(140), FOREIGN KEY (alueID) REFERENCES Alue(id));");
        lista.add("CREATE TABLE Viesti (id integer PRIMARY KEY, keskusteluID INTEGER, nimimerkki varchar(50), viesti TEXT, aikaleima TIMESTAMP DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (keskusteluID) REFERENCES Keskustelu(id));");
        return lista;
    }

}
