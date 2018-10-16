package university.alisawalter.DAO.postgres;

import lombok.extern.log4j.Log4j;
import university.alisawalter.DAO.DAOException;
import university.alisawalter.domain.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class PostgressTimeTableDAO extends PostgresDAO implements DAO<TimeTable> {
    private PostgresLessonDAO postgresLessonDAO = new PostgresLessonDAO();
    private PostgresProfessorDAO postgresProfessorDAO = new PostgresProfessorDAO();
    private PostgresGroupDAO postgresGroupDAO = new PostgresGroupDAO();
    private PostgresRoomDAO postgresRoomDAO = new PostgresRoomDAO();

    @Override
    public void add(TimeTable timeTable) throws DAOException {
        log.info("Adding timetable with id " + timeTable.getId() + " to database");
        String sql = "INSERT INTO timetables " +
                "(lesson_id, professor_id, group_id, room_id, time_begin, time_end, timetable_id) " +
                "VALUES( %d, %d, %d, %d, '%s', '%s', %d)";
        sql = makeTimeTableFormat(sql, timeTable);
        execute(sql);
        log.info("Timetable with id " + timeTable.getId() + " added successfully");
    }

    @Override
    public List<TimeTable> getAll() throws DAOException {
        return getAllTimeTables();
    }

    @Override
    public TimeTable getById(int id) throws DAOException {
        return getTimeTableById(id);
    }

    @Override
    public void update(TimeTable timetable) throws DAOException {
        log.info("Updating a timetable with id");
        String sql = "UPDATE timetables " +
                "SET lesson_id =%d, professor_id =%d, group_id =%d, room_id =%d, time_begin ='%s', time_end ='%s' " +
                "WHERE timetable_id = %d";
        sql = makeTimeTableFormat(sql, timetable);
        execute(sql);
        log.info("Timetable with id " + timetable.getId() + "updated succezsfully");

    }

    @Override
    public void remove(TimeTable timeTable) throws DAOException {
        log.info("removing timetable with id " + timeTable.getId() + " from database");
        String sql = "DELETE FROM timetables WHERE timetable_id = %d";
        sql = String.format(sql, timeTable.getId());
        execute(sql);
        log.info("timetable with id " + timeTable.getId() + " removed successfully");
    }

    private List<TimeTable> getAllTimeTables() throws DAOException {
        log.info("Getting all timetables");
        String sql = "SELECT timetable_id, lesson_id, professor_id, group_id, room_id, time_begin, time_end FROM timetables";
        List<TimeTable> timetableList = new ArrayList<>();
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
                int id = resultSet.getInt(1);
                Lesson lesson = postgresLessonDAO.getById(resultSet.getInt(2));
                Professor professor = postgresProfessorDAO.getById(resultSet.getInt(3));
                Group group = postgresGroupDAO.getById(resultSet.getInt(4));
                Room room = postgresRoomDAO.getById(resultSet.getInt(5));
                LocalDateTime timeBegin = resultSet.getTimestamp(6).toLocalDateTime();
                LocalDateTime timeEnd = resultSet.getTimestamp(7).toLocalDateTime();
                TimeTable timetable = new TimeTable(id, lesson, professor, group, room, timeBegin, timeEnd);
                timetableList.add(timetable);
            }
        } catch (SQLException e) {
            log.error("An error occured when getting all timetables", e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            closeConnection();
            log.trace("Connection closed successfully");
        }
        log.info("Returning timetables list");
        return timetableList;
    }


    private TimeTable getTimeTableById(int id) throws DAOException {
        log.info("Getting timetable with id " + id);
        String sql = "SELECT timetable_id, lesson_id, professor_id, group_id, room_id, time_begin, time_end " +
                "FROM timetables " +
                "WHERE timetable_id = ?";
        TimeTable timetable = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Getting connection");
            connection = getConnection();
            log.info("Opening statement");
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            log.trace("Obtaining resultSet");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Lesson lesson = postgresLessonDAO.getById(resultSet.getInt(2));
                Professor professor = postgresProfessorDAO.getById(resultSet.getInt(3));
                Group group = postgresGroupDAO.getById(resultSet.getInt(4));
                Room room = postgresRoomDAO.getById(resultSet.getInt(5));
                LocalDateTime timeBegin = resultSet.getTimestamp(6).toLocalDateTime();
                LocalDateTime timeEnd = resultSet.getTimestamp(7).toLocalDateTime();
                timetable = new TimeTable(id, lesson, professor, group, room, timeBegin, timeEnd);
            }
        } catch (SQLException e) {
            log.error("An error occured when getting timetable with id " + id, e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            closeConnection();
            log.trace("Connection closed successfully");
        }
        log.info("Returning timetable with id " + id);
        return timetable;
    }

    public List<TimeTable> getPeriod(LocalDateTime minTime, LocalDateTime maxTime) throws DAOException {
        log.info("Getting timetables for period from " + minTime.toString() + " to " + maxTime.toString());
        String sql = "SELECT timetable_id, lesson_id, professor_id, group_id, room_id, time_begin, time_end " +
                "FROM timetables WHERE time_begin >= ? AND time_begin <= ? ";
        List<TimeTable> timetableList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Opening connection");
            connection = getConnection();
            log.trace("Opening statement");
            statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(minTime));
            statement.setTimestamp(2, Timestamp.valueOf(maxTime));
            log.trace("Obtaining result set");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Lesson lesson = postgresLessonDAO.getById(resultSet.getInt(2));
                Professor professor = postgresProfessorDAO.getById(resultSet.getInt(3));
                Group group = postgresGroupDAO.getById(resultSet.getInt(4));
                Room room = postgresRoomDAO.getById(resultSet.getInt(5));
                LocalDateTime timeBegin = resultSet.getTimestamp(6).toLocalDateTime();
                LocalDateTime timeEnd = resultSet.getTimestamp(7).toLocalDateTime();
                TimeTable timetable = new TimeTable(id, lesson, professor, group, room, timeBegin, timeEnd);
                timetableList.add(timetable);
            }
        } catch (SQLException e) {
            log.error("An error occured when getting timetables for period", e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            closeConnection();
            log.trace("Connection closed successfully");
        }
        log.info("Returning timetables");
        return timetableList;
    }

    private String makeTimeTableFormat(String sql, TimeTable timeTable) {
        return String.format(sql, timeTable.getLesson().getLessonId(), timeTable.getProfessor().getPersonalID(),
                timeTable.getGroup().getId(), timeTable.getRoom().getRoomId(), Timestamp.valueOf(timeTable.getTimeBegin()),
                Timestamp.valueOf(timeTable.getTimeEnd()), timeTable.getId());
    }
}
