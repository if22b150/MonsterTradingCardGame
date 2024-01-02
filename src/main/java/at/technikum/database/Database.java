package at.technikum.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if(connection == null) {
            String connectionString = "jdbc:postgresql://localhost:5432/mydb?user=postgres&password=postgres";
            connection = DriverManager.getConnection(connectionString);
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
