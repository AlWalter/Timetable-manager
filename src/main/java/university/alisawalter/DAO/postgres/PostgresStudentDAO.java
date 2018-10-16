package university.alisawalter.DAO.postgres;

import lombok.extern.log4j.Log4j;
import university.alisawalter.DAO.DAOException;
import university.alisawalter.domain.Group;
import university.alisawalter.domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class PostgresStudentDAO extends PostgresDAO implements DAO<Student> {
    private PostgresGroupDAO postgresGroupDAO = new PostgresGroupDAO();

    public void add(Student student) throws DAOException {
        log.info("Adding a student with id " + student.getPersonalID());
        String sql = "INSERT INTO students (fname, lname,  group_id, student_id) VALUES ('%s', '%s', %d, %d)";
        sql = String.format(sql, student.getFirstName(), student.getLastName(), student.getGroup().getId(), student.getPersonalID());
        execute(sql);
        log.trace("Student with id " + student.getPersonalID() + " added successfully");
    }

    @Override
    public List<Student> getAll() throws DAOException {
        return getAllStudents();
    }

    @Override
    public Student getById(int id) throws DAOException {
        return getStudentById(id);
    }

    @Override
    public void update(Student student) throws DAOException {
        log.info("Updating student with id " + student.getPersonalID());
        String sql = "UPDATE students SET fname = '%s', lname = '%s', group_id = %d WHERE student_id = %d";
        sql = String.format(sql, student.getFirstName(), student.getLastName(), student.getGroup().getId(), student.getPersonalID());
        execute(sql);
        log.info("Student with id " + student.getPersonalID() + " updated succesfully");
    }

    @Override
    public void remove(Student student) throws DAOException {
        log.info("Removing student with id " + student.getPersonalID() + " from database");
        String sql = "DELETE FROM students WHERE student_id = %d";
        sql = String.format(sql, student.getPersonalID());
        execute(sql);
        log.info("Student with id " + student.getPersonalID() + " removed successfully");
    }

    private List<Student> getAllStudents() throws DAOException {
        log.info("Getting all students");
        String sql = "SELECT fname, lname, student_id, group_id  FROM students";
        List<Student> studentList = new ArrayList<>();
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
                int groupID = resultSet.getInt(4);
                Group group = postgresGroupDAO.getById(groupID);
                Student student = new Student(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getInt(3), group);
                studentList.add(student);
            }
        } catch (SQLException e) {
            log.error("An error occured when getting all students", e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            closeConnection();
            log.trace("Connection closed successfully");
        }
        log.info("Returning students List");
        return studentList;
    }

    private Student getStudentById(int id) throws DAOException {
        log.info("Getting student with id = " + id);
        String sql = "SELECT fname, lname, student_id, group_id  FROM students WHERE student_id = ?";
        Student student = null;
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
                int groupID = resultSet.getInt(4);
                Group group = postgresGroupDAO.getById(groupID);
                student = new Student(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getInt(3), group);
            }
        } catch (SQLException e) {
            log.error("An error occured when getting a student with id " + id, e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            closeConnection();
            log.trace("Connection closed successfully");
        }
        log.info("Returning student with id " + id);
        return student;
    }
}
