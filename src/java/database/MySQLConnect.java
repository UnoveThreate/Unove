package database;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnect {

    public static Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
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
    
    // This method now accepts properties directly
    public Connection connect(Properties props) throws Exception {
        // Get the database connection details from properties
        String serverName = props.getProperty("db.serverName", "localhost");
        String databaseName = props.getProperty("db.databaseName", "unove_cine");
        String username = props.getProperty("db.username", "root");
        String password = props.getProperty("db.password", "Anhhuyvip123@");
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
//        props.setProperty("db.databaseName", "unove_cine");
//        props.setProperty("db.username", "root");
//        props.setProperty("db.password", "Anhhuyvip123@");
//        // Add port number if necessary
//        props.setProperty("db.portNumber", "3306");
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
