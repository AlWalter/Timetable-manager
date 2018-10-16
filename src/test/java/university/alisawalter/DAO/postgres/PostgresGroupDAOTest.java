package university.alisawalter.DAO.postgres;


import org.junit.Before;
import org.junit.Test;
import university.alisawalter.DAO.ConnectionFactory;
import university.alisawalter.DAO.DAOException;
import university.alisawalter.domain.Group;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PostgresGroupDAOTest {
    private Group group1;
    private Group group2;
    private PostgresGroupDAO postgresGroupDAO;

    @Before
    public void setUp() throws Exception {
        setupDatabase();
        group1 = new Group("GroupTest1",1);
        group2 = new Group("GroupTest2",2);
        postgresGroupDAO = new PostgresGroupDAO();
    }

    private void setupDatabase() throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM timetables; DELETE FROM groups");
        ConnectionFactory.getInstance().closeConnection(connection);
    }


    @Test
    public void addAndRemove_ShouldAddAndRemove() throws DAOException {
        postgresGroupDAO.add(group1);
        Group expected = postgresGroupDAO.getById(1);
        assertEquals(expected.getId(),1);
        postgresGroupDAO.remove(group1);
        assertNull(postgresGroupDAO.getById(1));
    }

    @Test
    public void getAll_HaveToReturnNotEmptyList() throws DAOException, SQLException {
        addTwoGroups();
        List<Group> expected = new ArrayList<>();
        expected = postgresGroupDAO.getAll();
        assertNotNull(expected);
        assertEquals(expected.size(),2);
    }

    @Test
    public void getById_id1_HaveToReturtGroupWithId1() throws DAOException {
        postgresGroupDAO.add(group2);
        Group expected = postgresGroupDAO.getById(2);
        assertEquals(expected.getId(),2);
    }

    private void addTwoGroups() throws DAOException {
        postgresGroupDAO.add(new Group("A",777));
        postgresGroupDAO.add(new Group("B",888));
    }
}