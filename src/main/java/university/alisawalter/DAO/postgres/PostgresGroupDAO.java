package university.alisawalter.DAO.postgres;

import lombok.extern.log4j.Log4j;
import university.alisawalter.DAO.DAOException;
import university.alisawalter.domain.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class PostgresGroupDAO extends PostgresDAO implements DAO<Group> {

    @Override
    public void add(Group group) throws DAOException {
        log.info("Creating new Group with name " + group.getGroupName() + " and ID " + group.getId());
        String sql = "INSERT INTO groups (group_id, name) VALUES (%d, '%s')";
        sql = String.format(sql, group.getId(), group.getGroupName());
        execute(sql);
        log.info("A new Group added to database successfully");
    }

    @Override
    public List<Group> getAll() throws DAOException {
        return getAllGroups();
    }

    @Override
    public Group getById(int id) throws DAOException {
        return getGroupById(id);
    }

    @Override
    public void update(Group group) throws DAOException {
        log.info("Updating Group with ID " + group.getId());
        String sql = "UPDATE groups SET name = '%s' WHERE group_id = %d";
        sql = String.format(sql, group.getGroupName(), group.getId());
        execute(sql);
        log.info("The group with ID " + group.getId() + " created successfully");
    }

    @Override
    public void remove(Group group) throws DAOException {
        log.info("Removing a group with ID " + group.getId());
        String sql = "DELETE FROM groups WHERE group_id = %d";
        sql = String.format(sql, group.getId());
        execute(sql);
        log.info("Group with ID " + group.getId() + "removed successfully");
    }

    private List<Group> getAllGroups() throws DAOException {
        log.info("Getting all groups");
        List<Group> groupsList = new ArrayList<>();
        String sql = "SELECT * FROM groups";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Opening connection");
            connection = getConnection();
            log.trace("Creating Statement");
            statement = connection.createStatement();
            log.trace("obtaining resultSet");
            resultSet = statement.executeQuery(sql);
            log.trace("Adding groups from result set");
            while (resultSet.next()) {
                Group group = new Group(resultSet.getString("name"),
                        resultSet.getInt("group_id"));
                groupsList.add(group);
            }
        } catch (SQLException e) {
            log.error("An error occured when getting all groups", e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            closeConnection();
            log.trace("Connection closed successfully");
        }
        log.trace("Returnung groupsList");
        return groupsList;
    }

    private Group getGroupById(int id) throws DAOException {
        Group group = null;
        log.info("Get group by id = " + id);
        String sql = "SELECT name, group_id FROM groups WHERE group_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            log.trace("Opening connection");
            connection = getConnection();
            log.trace("Opening Statement");
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            log.trace("Obtaining resultSet");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                group = new Group(resultSet.getString(1), resultSet.getInt(2));
            }
        } catch (SQLException e) {
            log.error("An error occured when getting group with id " + group.getId(), e);
            throw new DAOException(e.getMessage(), e);
        } finally {
            log.trace("Closing Connection");
            closeConnection();
        }
        log.info("Returning group with ID = " + id);
        return group;
    }
}
