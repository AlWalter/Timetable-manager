package university.alisawalter.DAO.postgres;

import lombok.extern.log4j.Log4j;
import university.alisawalter.DAO.DAOException;
import university.alisawalter.domain.Lesson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class PostgresLessonDAO extends PostgresDAO implements DAO<Lesson> {

    @Override
    public void add(Lesson lesson) throws DAOException {
        log.info("Adding a new Lesson with ID = " + lesson.getLessonId() + " and name " + lesson.getLessonName());
        String sql = "INSERT INTO lessons (name, lesson_id) VALUES ('%s', %d)";
        sql = String.format(sql, lesson.getLessonName(), lesson.getLessonId());
        execute(sql);
        log.info("A new lesson added to database successfully");
    }

    @Override
    public List<Lesson> getAll() throws DAOException {
        return getAllLessons();
    }

    @Override
    public Lesson getById(int id) throws DAOException {
        return getLessonById(id);
    }

    @Override
    public void update(Lesson lesson) throws DAOException {
        log.info("Updating a new Lesson with ID " + lesson.getLessonId());
        String sql = "UPDATE lessons SET name = '%s' WHERE lesson_id = %d";
        sql = String.format(sql, lesson.getLessonName(), lesson.getLessonId());
        execute(sql);
        log.info("A lesson with ID " + lesson.getLessonId() + " updated successfully");
    }

    @Override
    public void remove(Lesson lesson) throws DAOException {
        log.info("Removing a lesson with ID" + lesson.getLessonId());
        String sql = "DELETE FROM lessons WHERE name = '%s' AND lesson_id = %d";
        sql = String.format(sql, lesson.getLessonName(), lesson.getLessonId());
        execute(sql);
        log.info("Lesson with ID " + lesson.getLessonId() + " removed successfully");
    }

    private List<Lesson> getAllLessons() throws DAOException {
        log.info("Getiing all lessons");
        String sql = "SELECT name, lesson_id  FROM lessons";
        List<Lesson> lessonsList = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Opening connection");
            connection = getConnection();
            log.trace("Opening Statement");
            statement = connection.createStatement();
            log.trace("Obtaining resultSet");
            resultSet = statement.executeQuery(sql);
            log.trace("Adding lessons to lessonsList");
            while (resultSet.next()) {
                Lesson lesson = new Lesson(resultSet.getString("name"), resultSet.getInt("lesson_id"));
                lessonsList.add(lesson);
            }
        } catch (SQLException e) {
            log.error("An error occured when getting all lessons", e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            closeConnection();
            log.trace("Connection closed successfully");
        }
        log.info("Returning lessonsList");
        return lessonsList;
    }

    public Lesson getLessonById(int id) throws DAOException {
        log.info("Getting lesson with ID = " + id);
        String sql = "SELECT name, lesson_id FROM lessons WHERE lesson_id = ?";
        Lesson lesson = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Creating connection");
            connection = getConnection();
            log.trace("Opening statement");
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            log.trace("Obtaining resultSet");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                lesson = new Lesson(resultSet.getString(1), resultSet.getInt(2));
            }
        } catch (SQLException e) {
            log.error("An error occured when getting lesson with id " + id, e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            closeConnection();
            log.trace("Connection closed successfully");
        }
        log.info("returning Lesson with ID = " + id);
        return lesson;
    }
}
