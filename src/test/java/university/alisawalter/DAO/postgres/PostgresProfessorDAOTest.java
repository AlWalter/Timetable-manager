package university.alisawalter.DAO.postgres;

import org.junit.Before;
import org.junit.Test;
import university.alisawalter.DAO.ConnectionFactory;
import university.alisawalter.DAO.DAOException;
import university.alisawalter.domain.Professor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PostgresProfessorDAOTest {
    private PostgresProfessorDAO postgresProfessorDAO;
    private Professor professor;

    @Before
    public void setUp() throws Exception {
        setupDatabase();
        postgresProfessorDAO = new PostgresProfessorDAO();
        professor = new Professor("Test","Test",12345678,"Test");
    }

    private void setupDatabase() throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM timetables; DELETE FROM professors");
        ConnectionFactory.getInstance().closeConnection(connection);
    }

    @Test
    public void add_Update_Remove_ShouldAddUpdateAndRemove() throws DAOException {
        postgresProfessorDAO.add(professor);
        Professor expected = postgresProfessorDAO.getById(12345678);
        assertEquals(expected,professor);

        professor.setDepartment("UpdateTest");
        postgresProfessorDAO.update(professor);
        expected = postgresProfessorDAO.getById(professor.getPersonalID());
        assertEquals(expected.getDepartment(),"UpdateTest");

        postgresProfessorDAO.remove(professor);
        assertNull(postgresProfessorDAO.getById(12345678));
    }

    @Test
    public void getAll() throws DAOException, SQLException {
        addTwoProfessors();
        List<Professor> expected = new ArrayList<>();
        expected = postgresProfessorDAO.getAll();
        assertEquals(expected.size(),2);
    }

    private void addTwoProfessors(){
        postgresProfessorDAO.add(new Professor("Test","Test", 1,"Test"));
        postgresProfessorDAO.add(new Professor("Test","Test",2,"Test"));
    }
}