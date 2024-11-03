package database;

import jakarta.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MySQLConnect {

    private BasicDataSource dataSource;
    protected Connection connection;
    private static final Logger logger = LogManager.getLogger(MySQLConnect.class);

    public MySQLConnect() {
    }

    private Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println("Current active connections: " + dataSource.getNumActive());
        return connection;
    }

    public final Connection connect(ServletContext context) throws Exception {
        // Load the properties from the dbconfig.properties file
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(
                context.getRealPath("/WEB-INF/config/private/dbconfig.properties"))) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error loading database configuration: {}", e.getMessage());
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
        dataSource.setInitialSize(3);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(5);
        dataSource.setMinIdle(2);
        dataSource.setMaxWaitMillis(10000);
        dataSource.setTestOnBorrow(true); // Validate connections before borrowing
        dataSource.setValidationQuery("SELECT 1"); // Simple validation query
        dataSource.setRemoveAbandonedOnBorrow(true);
        dataSource.setRemoveAbandonedTimeout(60); // Seconds
        dataSource.setTimeBetweenEvictionRunsMillis(30000);
        dataSource.setMinEvictableIdleTimeMillis(60000); // 1 minute

        // Load MySQL JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish the connection without try-with-resources so that it can be reused
        try {
            this.connection = getConnection();
            logger.info("Connection established to the MySQL database.");
            return connection;
        } catch (SQLException ex) {
            //Timeout 
            if (ex.getErrorCode() == 0) {
                logger.error("Connection request timed out after waiting for {} ms", dataSource.getMaxWaitMillis());
                throw new SQLException("Connection request timed out", ex);
            }
            logger.error("Error establishing connection to the database: {}", ex.getMessage());
            throw new SQLException("Error establishing connection to the database", ex);
        }
    }

    public void beginTransaction() throws SQLException {
        if (connection != null) {
            connection.setAutoCommit(false);
            logger.info("Transaction started.");
        } else {
            throw new SQLException("Connection fail, cannot start transaction.");
        }
    }

    public void commitTransaction() throws SQLException {
        if (connection != null) {
            connection.commit();
            logger.info("Transaction committed.");
        } else {
            throw new SQLException("Commit Transaction Faild");
        }
    }

    public void rollbackTransaction() {
        if (connection != null) {
            try {
                connection.rollback();
                logger.info("Transaction rolled back.");
            } catch (SQLException e) {
                logger.error("Error during rollback: {}", e.getMessage());
            }
        } else {
            logger.error("Connection is null, cannot rollback transaction.");
        }
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Connection released back to the pool.");
            } catch (SQLException ex) {
                logger.error("Error closing connection: {}", ex.getMessage());
            }
        }
    }

    public void closeDataSource() {
        try {
            if (dataSource != null) {
                dataSource.close();
                logger.info("Data source closed.");
            }
        } catch (SQLException e) {
            logger.error("Error closing data source: {}", e.getMessage());
        }
    }

}
