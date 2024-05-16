package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.logging.Logger;
/**
 * The ConnectionFactory class is responsible for creating and closing connections to the database.
 */
public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/ordermanagement";
    private static final String USER = "root";
    private static final String PASSWORD = "sailorMoon26";

    private static ConnectionFactory singleInstance = new ConnectionFactory();
    /**
     * Constructs a new ConnectionFactory.
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates a new connection to the database.
     * @return the created connection
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            LOGGER.severe("Error creating the connection: " + e.getMessage());
        }
        return connection;
    }
    /**
     * Gets a connection to the database.
     * @return the connection
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }
    /**
     * Closes the given connection.
     * @param connection the connection to close
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.severe("Error closing the connection: " + e.getMessage());
            }
        }
    }
    /**
     * Closes the given statement.
     * @param statement the statement to close
     */
    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.severe("Error closing the statement: " + e.getMessage());
            }
        }
    }
    /**
     * Closes the given result set.
     * @param resultSet the result set to close
     */
    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.severe("Error closing the result set: " + e.getMessage());
            }
        }
    }
}