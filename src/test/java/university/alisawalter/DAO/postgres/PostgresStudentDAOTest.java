package university.alisawalter.DAO.postgres;

import org.junit.Before;
import org.junit.Test;
import university.alisawalter.DAO.ConnectionFactory;
import university.alisawalter.DAO.DAOException;
import university.alisawalter.domain.Group;
import university.alisawalter.domain.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PostgresStudentDAOTest {
    private PostgresStudentDAO postgresStudentDAO;
    private PostgresGroupDAO postgresGroupDAO;
    private Student student;
    private Group group;

    @Before
    public void setUp() throws DAOException, SQLException {
        postgresStudentDAO = new PostgresStudentDAO();
        postgresGroupDAO = new PostgresGroupDAO();
        group = new Group("Test", 1);
        student = new Student("Test", "Test", 0, group);
        setupDatabase();
    }

    private void setupDatabase() throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM timetables; DELETE FROM students;DELETE FROM groups;");
        ConnectionFactory.getInstance().closeConnection(connection);
        postgresGroupDAO.add(group);
    }

    @Test
    public void add_update_delete_shouldAddAndUpdateAndDelete() throws DAOException {
        postgresStudentDAO.add(student);
        Student expected = postgresStudentDAO.getById(0);
        assertEquals(expected.getPersonalID(), student.getPersonalID());
        assertEquals(expected.getFirstName(), student.getFirstName());

        student.setFirstName("UpdateTest");
        postgresStudentDAO.update(student);
        expected = postgresStudentDAO.getById(0);
        assertEquals(expected.getFirstName(), student.getFirstName());

        postgresStudentDAO.remove(student);
        assertNull(postgresStudentDAO.getById(0));
    }

    @Test
    public void getAll() throws DAOException, SQLException {
        addTwoStudents();
        List<Student> expected = new ArrayList<>();
        expected = postgresStudentDAO.getAll();
        assertEquals(expected.size(), 2);
    }

    private void addTwoStudents() {
        postgresStudentDAO.add(new Student("Test", "Test", 1, group));
        postgresStudentDAO.add(new Student("Test", "Test", 2, group));
    }
}