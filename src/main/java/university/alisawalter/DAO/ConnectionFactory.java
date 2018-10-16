package university.alisawalter.DAO;

import lombok.extern.log4j.Log4j;
import university.alisawalter.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j
public class ConnectionFactory {

    private static ConnectionFactory connectionFactory;

    private ConnectionFactory() {
    }

    public static synchronized ConnectionFactory getInstance() {
        if (connectionFactory == null)
            connectionFactory = new ConnectionFactory();
        return connectionFactory;
    }

    public Connection getConnection() {
        try {
            Class.forName(Config.DB_DRIVER);
            return DriverManager.getConnection(Config.DB_URL, Config.DB_USERNAME, Config.DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            log.error("Cannot connect to database", e);
            throw new DAOException(e.getMessage(), e);
        }
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException | NullPointerException e) {
            log.error("Cannot close connection");
            throw new DAOException(e.getMessage(), e);
        }
    }
}


