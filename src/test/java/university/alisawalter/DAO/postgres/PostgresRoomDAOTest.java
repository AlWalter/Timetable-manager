package university.alisawalter.DAO.postgres;


import org.junit.Before;
import org.junit.Test;
import university.alisawalter.DAO.ConnectionFactory;
import university.alisawalter.DAO.DAOException;
import university.alisawalter.domain.Room;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PostgresRoomDAOTest {
    private PostgresRoomDAO postgresRoomDAO;
    private Room room;

    @Before
    public void setUp() throws Exception {
        postgresRoomDAO = new PostgresRoomDAO();
        room = new Room(0, 0, 0, 0);
        setupDatabase();
    }

    private void setupDatabase() throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM timetables; DELETE FROM rooms");
        ConnectionFactory.getInstance().closeConnection(connection);
    }

    @Test
    public void add_update_Remove_ShouldAddUpdateAndRemove() throws DAOException {
        postgresRoomDAO.add(room);
        Room expected = postgresRoomDAO.getById(room.getRoomId());
        assertEquals(expected.getRoomId(), room.getRoomId());

        room.setBuildingNumber(99);
        postgresRoomDAO.update(room);
        expected = postgresRoomDAO.getById(room.getRoomId());
        assertEquals(expected.getBuildingNumber(), room.getBuildingNumber());

        postgresRoomDAO.remove(room);
        assertNull(postgresRoomDAO.getById(room.getRoomId()));
    }

    @Test
    public void getAll() throws DAOException, SQLException {
        addTwoRooms();
        List<Room> expected = new ArrayList<>();
        expected = postgresRoomDAO.getAll();
        assertEquals(expected.size(), 2);
    }

    private void addTwoRooms() {
        postgresRoomDAO.add(new Room(1, 1, 1, 1));
        postgresRoomDAO.add(new Room(2, 2, 2, 2));
    }
}