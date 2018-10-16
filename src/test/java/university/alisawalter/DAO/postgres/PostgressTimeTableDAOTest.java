package university.alisawalter.DAO.postgres;

import org.junit.Before;
import org.junit.Test;
import university.alisawalter.DAO.ConnectionFactory;
import university.alisawalter.DAO.DAOException;
import university.alisawalter.domain.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.junit.Assert.*;

public class PostgressTimeTableDAOTest {
    private PostgressTimeTableDAO postgressTimeTableDAO;
    private PostgresLessonDAO postgresLessonDAO;
    private PostgresProfessorDAO postgresProfessorDAO;
    private PostgresGroupDAO postgresGroupDAO;
    private PostgresRoomDAO postgresRoomDAO;
    private Lesson lesson;
    private Professor professor;
    private Group group;
    private Room room;
    LocalDateTime timeBeginTest1;
    LocalDateTime timeEndTest1;
    LocalDateTime timeBeginTest2;
    LocalDateTime timeEndTest2;
    TimeTable timetable;
    TimeTable timetable2;


    @Before
    public void setUp() throws Exception {
        setupDatabase();
        postgressTimeTableDAO = new PostgressTimeTableDAO();
        postgresLessonDAO = new PostgresLessonDAO();
        postgresProfessorDAO = new PostgresProfessorDAO();
        postgresGroupDAO = new PostgresGroupDAO();
        postgresRoomDAO = new PostgresRoomDAO();
        lesson = new Lesson("Test", 1);
        professor = new Professor("Test", "Test", 1, "Test");
        group = new Group("Test", 1);
        room = new Room(1, 1, 1, 1);
        timeBeginTest1 = LocalDateTime.parse("1990-04-05T08:00:00", ISO_LOCAL_DATE_TIME);
        timeBeginTest2 = LocalDateTime.parse("1990-04-07T08:00:00", ISO_LOCAL_DATE_TIME);
        timeEndTest1 = LocalDateTime.parse("1990-04-05T10:30:00", ISO_LOCAL_DATE_TIME);
        timeEndTest2 = LocalDateTime.parse("1990-04-07T10:30:00", ISO_LOCAL_DATE_TIME);
        timetable = new TimeTable(1, lesson, professor, group, room, timeBeginTest1, timeEndTest1);
        timetable2 = new TimeTable(2, lesson, professor, group, room, timeBeginTest2, timeEndTest2);
    }

    private void setupDatabase() throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM timetables;" +
                "DELETE FROM lessons;" +
                "DELETE FROM professors;" +
                "DELETE FROM groups;" +
                "DELETE FROM rooms");
        ConnectionFactory.getInstance().closeConnection(connection);
    }

    private void setTwoTimetables() {
        postgresLessonDAO.add(lesson);
        postgresProfessorDAO.add(professor);
        postgresGroupDAO.add(group);
        postgresRoomDAO.add(room);
        postgressTimeTableDAO.add(timetable);
        postgressTimeTableDAO.add(timetable2);
    }

    @Test
    public void getPeriod_ShouldReturnOneValue() throws DAOException {
        List<TimeTable> expected = new ArrayList<>();
        setTwoTimetables();
        LocalDateTime timeMin = LocalDateTime.parse("1990-04-05T08:00:00", ISO_LOCAL_DATE_TIME);
        LocalDateTime timeMax = LocalDateTime.parse("1990-04-06T08:00:00", ISO_LOCAL_DATE_TIME);
        expected = postgressTimeTableDAO.getPeriod(timeMin, timeMax);
        assertEquals(expected.size(), 1);
    }

    @Test
    public void getAll_SchouldReturnTwo() throws DAOException, SQLException {
        setTwoTimetables();
        List<TimeTable> expected = new ArrayList<>();
        expected = postgressTimeTableDAO.getAll();
        assertEquals(expected.size(), 2);
    }

    @Test
    public void add_Remove_SchouldAddAndRemove() throws DAOException, SQLException {
        setTwoTimetables();
        assertNotNull(postgressTimeTableDAO.getById(1));

        postgressTimeTableDAO.remove(timetable);
        assertNull(postgressTimeTableDAO.getById(1));
    }
}