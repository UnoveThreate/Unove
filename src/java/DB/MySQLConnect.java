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

            // Load the properties from the dbconfig.properties file
            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream(context.getRealPath("/WEB-INF/config/private/dbconfig.properties"))) {
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

            // Establish the connection
            this.connection = DriverManager.getConnection(url, username, password); 
            return connection; 
        }
//    public static void main(String[] args) {
//        MySQLConnect mySQLConnect = new MySQLConnect();
//        Properties props = new Properties();
//
//        // Set database properties for testing
//        props.setProperty("db.serverName", "localhost");
//        props.setProperty("db.databaseName", "Unove");
//        props.setProperty("db.username", "root");
//        props.setProperty("db.password", "Password.1");
//
//        try {
//            mySQLConnect.connect(props);
//            System.out.println("Database connection test successful!");
//            mySQLConnect.closeConnection();
//        } catch (Exception e) {
//            System.err.println("Database connection test failed: " + e.getMessage());
//        }
//    }
    
}
