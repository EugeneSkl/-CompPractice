package by.bsu.config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public  class JDBCPostgreConnection {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DB_URL = "jdbc:postgresql://localhost/restaurant-db";
    private static final String USER = "postgres";
    private static final String PASS = "1234";

    public Connection JDBCConnection() {
    try {
        Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
        LOGGER.error("PostgreSQL JDBC Driver is not found. Include it in your library path ");
        e.printStackTrace();
    }

	LOGGER.info("PostgreSQL JDBC Driver successfully connected");
    Connection connection = null;

	try {
        connection = DriverManager
                .getConnection(DB_URL, USER, PASS);


    } catch (SQLException e) {
        LOGGER.error(e);
    }

	if (connection != null) {
        LOGGER.info("You successfully connected to database now");
    } else {
        LOGGER.error("Failed to make connection to database");
    }
        return connection;
    }

}
