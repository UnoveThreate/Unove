package DB;

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

    public Connection connect(Properties props) throws Exception {
        // Get configuration from properties
        String serverName = props.getProperty("db.serverName", "localhost");
        String databaseName = props.getProperty("db.databaseName", "Unove");
        String username = props.getProperty("db.username", "root");
        String password = props.getProperty("db.password", "Password.1");

        try {
            // Connect to MySQL
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

    public static void main(String[] args) {
        MySQLConnect mySQLConnect = new MySQLConnect();
        Properties props = new Properties();

        // Set database properties for testing
        props.setProperty("db.serverName", "localhost");
        props.setProperty("db.databaseName", "Unove");
        props.setProperty("db.username", "root");
        props.setProperty("db.password", "Password.1");

        try {
            mySQLConnect.connect(props);
            System.out.println("Database connection test successful!");
            mySQLConnect.closeConnection();
        } catch (Exception e) {
            System.err.println("Database connection test failed: " + e.getMessage());
        }
    }
}
