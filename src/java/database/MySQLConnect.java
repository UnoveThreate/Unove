package database;

import jakarta.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
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
    
    public final Connection connect(ServletContext context) throws Exception {
        // Load the properties from the dbconfig.properties file
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(
                context.getRealPath("/WEB-INF/config/private/dbconfig.properties"))) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error loading database configuration", e);
        }

        // Get the database connection details from properties
        String serverName = props.getProperty("db.serverName");
        String databaseName = props.getProperty("db.databaseName");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");
        String portNumber = props.getProperty("db.portNumber", "3306");

        // Build the connection URL
        String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + databaseName;

        // Load MySQL JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish the connection without try-with-resources so that it can be reused
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connection established and ready");
            } else {
                System.out.println("Existing connection -> Reuse");
            }
            return this.connection;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Error establishing connection to the database", ex);
        }
    }
}