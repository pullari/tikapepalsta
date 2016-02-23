package foorumi;

import java.sql.*;

public class KeskusteluDao {

    private Database db;

    public KeskusteluDao(Database d) {
        this.db = d;
    }

    public Keskustelu haeYksi(String key, ViestiDao vdao) throws Exception {
        Connection con = db.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM Keskustelu WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        int id = rs.getInt("id");
        String nimi = rs.getString("avaus");
        
        Keskustelu k = new Keskustelu(id, null, vdao.haeKeskustelunViestit(id), nimi);
        return k;
    }
}
