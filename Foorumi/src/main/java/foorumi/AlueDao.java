package foorumi;

import java.sql.*;
import java.util.*;

public class AlueDao {

    private Database db;

    public AlueDao(Database d) {
        this.db = d;
    }

    public List<Alue> haeKaikki() throws Exception {
        List<Alue> A = new ArrayList<>();
        Connection con = db.getConnection();
        String kysely = "SELECT Alue.id, Alue.nimi, COUNT(Viesti.id) AS Viestit, datetime(MAX(Viesti.aikaleima), 'localtime') "
                + "FROM Alue LEFT JOIN Keskustelu ON Alue.id = Keskustelu.alueID INNER JOIN Viesti ON Keskustelu.id = Viesti.keskusteluID "
                + "GROUP BY Alue.nimi ORDER BY Viesti.aikaleima";
        PreparedStatement stmt = con.prepareStatement(kysely);
//        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.isBeforeFirst();
        if (!hasOne) {
            return null;
        }
        while (rs.next()) {
            int id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            int viestit = rs.getInt("viestit");

            A.add(new Alue(id, nimi, viestit, null));
        }
        return A;

    }
}
