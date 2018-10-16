package university.alisawalter.DAO.postgres;

import org.junit.Before;
import org.junit.Test;
import university.alisawalter.DAO.ConnectionFactory;
import university.alisawalter.DAO.DAOException;
import university.alisawalter.domain.Lesson;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PostgresLessonDAOTest {
    private Lesson lesson;
    private PostgresLessonDAO postgresLessonDAO;

    @Before
    public void setUp() throws SQLException {
        setupDatabase();
        postgresLessonDAO = new PostgresLessonDAO();
        lesson = new Lesson("Test",1);
    }

    private void setupDatabase() throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM timetables; DELETE FROM lessons");
        ConnectionFactory.getInstance().closeConnection(connection);
    }

    @Test
    public void add_Update_Remove_ShouldAddUpdateAndRemove() throws DAOException {
        postgresLessonDAO.add(lesson);
        Lesson expected = postgresLessonDAO.getById(lesson.getLessonId());
        assertEquals(expected.getLessonId(),lesson.getLessonId());

        lesson.setLessonName("UpdateTest");
        postgresLessonDAO.update(lesson);
        expected = postgresLessonDAO.getById(lesson.getLessonId());
        assertEquals(expected.getLessonName(),"UpdateTest");

        postgresLessonDAO.remove(lesson);
        assertNull(postgresLessonDAO.getById(lesson.getLessonId()));
    }

    @Test
    public void getAll() throws DAOException, SQLException {
        addTwoLessons();
        List<Lesson> expected = new ArrayList<>();
        expected = postgresLessonDAO.getAll();
        assertNotNull(expected);
        assertEquals(expected.size(),2);
    }

    private void addTwoLessons(){
        postgresLessonDAO.add(new Lesson("Test",10));
        postgresLessonDAO.add(new Lesson("Test1",20));
    }

}