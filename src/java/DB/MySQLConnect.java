/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import jakarta.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnect {
    protected Connection connection;

    public MySQLConnect() {
    }

    public void closeConnection() {
        try {
            if (this.connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection connect(ServletContext context) throws Exception {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(context.getRealPath("/WEB-INF/config/private/dbconfig.properties"))) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error loading database configuration", e);
        }

        // Lấy thông tin cấu hình từ file properties
        String serverName = props.getProperty("db.serverName", "localhost");
        String databaseName = props.getProperty("db.databaseName", "Unove");
        String username = props.getProperty("db.username", "root");
        String password = props.getProperty("db.password", "Password.1");

        try {
            // Kết nối tới MySQL
            String URLConnect = "jdbc:mysql://" + serverName + ":3306/" + databaseName + "?useSSL=false&serverTimezone=UTC";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URLConnect, username, password);

            if (connection == null) {
                throw new Exception("Error When Connecting");
            }

            System.out.println("Connected to MySQL database");
            DatabaseMetaData dm = (DatabaseMetaData) connection.getMetaData();

            return connection;

        } catch (SQLException e) {
            System.err.println("Cannot connect to the database, " + e);
            throw new Exception("Database connection error", e);
        }
    }
}
