/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Per
 */
public class DatabaseConnectionManager {

    private volatile static DatabaseConnectionManager instance;
    private BasicDataSource dataSource;
    private static final Logger logger = LogManager.getLogger(DatabaseConnectionManager.class);

    private DatabaseConnectionManager(ServletContext context) {
        dataSource = new BasicDataSource();
        setupDataSource(context);
    }

    public static DatabaseConnectionManager getInstance(ServletContext context) {
        DatabaseConnectionManager localInstance = instance;
        if (localInstance == null) {
            synchronized (DatabaseConnectionManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = new DatabaseConnectionManager(context);
                }
            }
        }
        return instance;
    }

    private void setupDataSource(ServletContext context) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(context.getRealPath("/WEB-INF/config/private/dbconfig.properties"))) {
            props.load(fis);
        } catch (IOException e) {
            logger.error("Error loading database configuration: {}", e.getMessage());
            throw new RuntimeException("Error loading database configuration", e);
        }

        String serverName = props.getProperty("db.serverName");
        String databaseName = props.getProperty("db.databaseName");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");
        String portNumber = props.getProperty("db.portNumber", "3306");

        String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + databaseName;

        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // Configure pool settings
        dataSource.setInitialSize(2);
        dataSource.setMaxTotal(10);
        dataSource.setMaxIdle(5);
        dataSource.setMinIdle(2);
        dataSource.setMaxWaitMillis(10000);
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setRemoveAbandonedOnBorrow(true);
        dataSource.setRemoveAbandonedTimeout(60);
        dataSource.setTimeBetweenEvictionRunsMillis(30000);
        dataSource.setMinEvictableIdleTimeMillis(60000);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
