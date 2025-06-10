package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// mysql -u root -p

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        final String url = "jdbc:mysql://localhost:3306/sys?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        final String username = "root";
        final String pass = "newpassword";

        return DriverManager.getConnection(url, username, pass);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }

    
}
