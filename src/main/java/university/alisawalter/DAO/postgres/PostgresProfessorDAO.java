package university.alisawalter.DAO.postgres;

import lombok.extern.log4j.Log4j;
import university.alisawalter.DAO.DAOException;
import university.alisawalter.domain.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class PostgresProfessorDAO extends PostgresDAO implements DAO<Professor> {

    @Override
    public void add(Professor professor) throws DAOException {
        String sql = "INSERT INTO professors (fname, lname, department, professor_id) VALUES ('%s', '%s', '%s', %d)";
        sql = String.format(sql, professor.getFirstName(), professor.getLastName(),
                professor.getDepartment(), professor.getPersonalID());
        execute(sql);
    }

    @Override
    public List<Professor> getAll() throws DAOException {
        return getAllProfessors();
    }

    @Override
    public Professor getById(int id) throws DAOException {
        return getProfessorById(id);
    }

    @Override
    public void update(Professor professor) throws DAOException {
        String sql = "UPDATE professors SET fname = '%s', lname = '%s', department = '%s' WHERE professor_id = %d";
        sql = String.format(sql, professor.getFirstName(), professor.getLastName(),
                professor.getDepartment(), professor.getPersonalID());
        execute(sql);
    }

    @Override
    public void remove(Professor professor) throws DAOException {
        String sql = "DELETE FROM professors WHERE professor_id = %d";
        sql = String.format(sql, professor.getPersonalID());
        execute(sql);
    }

    private List<Professor> getAllProfessors() throws DAOException {
        String sql = "SELECT fname, lname, professor_id, department  FROM professors";
        List<Professor> professorList = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Professor professor = new Professor(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getInt(3), resultSet.getString(4));
                professorList.add(professor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return professorList;
    }

    private Professor getProfessorById(int id) throws DAOException {
        String sql = "SELECT fname, lname, professor_id, department  FROM professors where professor_id = ?";
        Professor professor = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                professor = new Professor(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getInt(3), resultSet.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return professor;
    }
}
