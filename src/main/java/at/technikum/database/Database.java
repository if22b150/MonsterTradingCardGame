package at.technikum.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection connect() throws SQLException {
        String connectionString = "jdbc:postgresql://localhost:5432/mydb?user=postgres&password=postgres";
        return DriverManager.getConnection(connectionString);
    }
}
