package university.alisawalter.DAO.postgres;

import lombok.extern.log4j.Log4j;
import university.alisawalter.DAO.DAOException;
import university.alisawalter.domain.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class PostgresRoomDAO extends PostgresDAO implements DAO<Room> {

    @Override
    public void add(Room room) throws DAOException {
        log.info("Adding a room with id " + room.getRoomId());
        String sql = "INSERT INTO rooms (room_id, room_number, building_number, number_of_places) VALUES (%d, %d, %d, %d)";
        sql = String.format(sql, room.getRoomId(), room.getRoomNumber(), room.getBuildingNumber(), room.getNumberOfPlaces());
        execute(sql);
        log.info("Room with id " + room.getRoomId() + " added successfully");
    }

    @Override
    public List<Room> getAll() throws DAOException {
        return getAllRooms();
    }

    @Override
    public Room getById(int id) throws DAOException {
        return getRoomByID(id);
    }

    @Override
    public void update(Room room) throws DAOException {
        log.info("Updating room with id " + room.getRoomId());
        String sql = "UPDATE rooms SET room_number = %d, building_number = %d, number_of_places = %d WHERE room_id = %d";
        sql = String.format(sql, room.getRoomNumber(), room.getBuildingNumber(), room.getNumberOfPlaces(), room.getRoomId());
        execute(sql);
        log.info("Room with id " + room.getRoomId() + "updated successfully");
    }

    @Override
    public void remove(Room room) throws DAOException {
        log.info("Removing room with id " + room.getRoomId() + " from database");
        String sql = "DELETE FROM rooms WHERE room_id = %d";
        sql = String.format(sql, room.getRoomId());
        execute(sql);
        log.info("Room with id " + room.getRoomId() + "removed successfully");
    }

    private List<Room> getAllRooms() throws DAOException {
        log.info("Getting all Rooms");
        String sql = "SELECT room_id, room_number, building_number, number_of_places  FROM rooms";
        List<Room> roomList = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Opening connection");
            connection = getConnection();
            log.trace("Opening statement");
            statement = connection.createStatement();
            log.trace("Obtaining resultSet");
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Room room = new Room(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getInt(3), resultSet.getInt(4));
                roomList.add(room);
            }
        } catch (SQLException e) {
            log.error("An error occured when getting all rooms", e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            closeConnection();
            log.trace("Connection closed successfully");
        }
        log.info("Returning roomsList");
        return roomList;
    }

    private Room getRoomByID(int id) throws DAOException {
        log.info("Getting room with id " + id);
        String sql = "SELECT room_id, room_number, building_number, number_of_places  FROM rooms WHERE room_id = ?";
        Room room = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Opening connection");
            connection = getConnection();
            log.trace("Opening statement");
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            log.trace("Obtaining resultSet");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                room = new Room(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getInt(3), resultSet.getInt(4));
            }
        } catch (SQLException e) {
            log.error("An error occured when getting room with id " + room.getRoomId(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            closeConnection();
            log.trace("Connection closed successfully");
        }
        log.info("Returning room with id " + id);
        return room;
    }
}
