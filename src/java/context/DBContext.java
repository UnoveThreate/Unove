/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import jakarta.servlet.ServletContext;

/**
 *
 * @author Per
 */
public class DBContext {

    public static Connection getConnection(ServletContext context) throws Exception {
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
        String portNumber = props.getProperty("db.portNumber", "3306"); // Default MySQL port

        // Build the connection URL
        String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + databaseName;

        // Load MySQL JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish the connection
        return DriverManager.getConnection(url, username, password);
    }

    public static boolean checkConnection(ServletContext context) {
        try (Connection connection = getConnection(context)) {
            // Check if the connection is valid within a timeout of 5 seconds
            if (connection != null && connection.isValid(5)) {
                System.out.println("Database connection is valid.");
                return true;
            }
            
            connection.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
        System.out.println("Database connection is invalid.");
        return false;
    }

    public static void main(String[] args) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
