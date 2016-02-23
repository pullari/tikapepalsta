
package foorumi;

import java.sql.*;
import java.util.*;

public class Database {


    private Connection connection;

    public Database(String address) throws Exception {
        this.connection = DriverManager.getConnection(address);
    }

    public Connection getConnection() {
        return connection;
    }

}
