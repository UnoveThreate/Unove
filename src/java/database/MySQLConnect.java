package database;

import jakarta.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;

public class MySQLConnect {

    private static BasicDataSource dataSource;
    protected volatile static Connection connection;

    public MySQLConnect() {
    }

    private static Connection getConnection() throws SQLException {
        System.out.println("Current active connections: " + dataSource.getNumActive());
        if (dataSource == null) {
            throw new SQLException("Connection pool is not initialized.");
        }
        if (connection == null) {
            connection = dataSource.getConnection();
        }
        
        return connection;
    }

    public synchronized static final Connection connect(ServletContext context) throws Exception {
        if (dataSource == null) {
            // Load the properties from the dbconfig.properties file
            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream(
                    context.getRealPath("/WEB-INF/config/private/dbconfig.properties"))) {
                props.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error loading database configuration: {}" + e.getMessage());
                throw new Exception("Error loading database configuration", e);
            }
            // properties
            dataSource = new BasicDataSource();
            // Get the database connection details from properties
            String serverName = props.getProperty("db.serverName");
            String databaseName = props.getProperty("db.databaseName");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");
            String portNumber = props.getProperty("db.portNumber", "3306");

            /**
             * Connection - URL : serverName / portNumber / databaseName
             */
            String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + databaseName;

            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);

            // Configure pool settings
            dataSource.setInitialSize(2);
            dataSource.setMaxTotal(12);
            dataSource.setMaxIdle(5);
            dataSource.setMinIdle(2);
            dataSource.setMaxWaitMillis(10000);
            dataSource.setTestOnBorrow(true); // Validate connections before borrowing
            dataSource.setValidationQuery("SELECT 1"); // Simple validation query
            dataSource.setRemoveAbandonedOnBorrow(true);
            dataSource.setRemoveAbandonedTimeout(60); // Seconds
            dataSource.setTimeBetweenEvictionRunsMillis(30000);
            dataSource.setMinEvictableIdleTimeMillis(60000); // 1 minute
        }

        // Load MySQL JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish the connection without try-with-resources so that it can be reused
        try {
            System.out.println("Connection established to the database.");
            return getConnection();
        } catch (SQLException ex) {
            //Timeout 
            if (ex.getErrorCode() == 0) {
                System.out.println("Connection request timed out after waiting for {} ms" + dataSource.getMaxWaitMillis());
                throw new SQLException("Connection request timed out", ex);
            }
            System.out.println("Error establishing connection to the database: {}" + ex.getMessage());
            throw new SQLException("Error establishing connection to the database", ex);
        }
    }

    public void beginTransaction() throws SQLException {
        if (connection != null) {
            connection.setAutoCommit(false);
            System.out.println("Transaction started.");
        } else {
            throw new SQLException("Connection fail, cannot start transaction.");
        }
    }

    public void commitTransaction() throws SQLException {
        if (connection != null) {
            connection.commit();
            System.out.println("Transaction committed.");
        } else {
            throw new SQLException("Commit Transaction Faild");
        }
    }

    public void rollbackTransaction() {
        if (connection != null) {
            try {
                connection.rollback();
                System.out.println("Transaction rolled back.");
            } catch (SQLException e) {
                System.out.println("Error during rollback: {}" + e.getMessage());
            }
        } else {
            System.out.println("Connection is null, cannot rollback transaction.");
        }
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection released back to the pool.");
            } catch (SQLException ex) {
                System.out.println("Error closing connection: {}" + ex.getMessage());
            }
        }
    }

    public void closeDataSource() {
        try {
            if (dataSource != null) {
                dataSource.close();
                System.out.println("Data source closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing data source: {}" + e.getMessage());
        }
    }

}
