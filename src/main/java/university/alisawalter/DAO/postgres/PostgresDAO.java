package university.alisawalter.DAO.postgres;

import lombok.extern.log4j.Log4j;
import university.alisawalter.DAO.ConnectionFactory;
import university.alisawalter.DAO.DAOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Log4j
public class PostgresDAO {
    private ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private Connection connection;

    void execute(String query) throws DAOException {
        try {
            log.trace("Opening Connection");
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            log.error("An error occured when cteating statement",e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            closeConnection();
            log.trace("Connection closed successfully");
        }
    }

    Connection getConnection() {
        if (!isConnectionOpen())
            connection = connectionFactory.getConnection();
        return connection;
    }

    void closeConnection() {
        close(this.connection);
        this.connection = null;
    }

    void close(Connection connection) {
        connectionFactory.closeConnection(connection);
    }

    private boolean isConnectionOpen() {
        try {
            return !connection.isClosed();
        } catch (SQLException | NullPointerException e) {
            return false;
        }
    }
}



