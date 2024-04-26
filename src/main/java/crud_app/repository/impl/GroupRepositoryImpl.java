package crud_app.repository.impl;

import crud_app.entity.Group;
import crud_app.entity.Topic;
import crud_app.entity.TopicMessage;
import crud_app.repository.GroupRepository;
import crud_app.utils.DataBase;
import crud_app.utils.GroupMapper;
import crud_app.utils.TopicMapper;
import crud_app.utils.TopicMessageMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * this is implementation group repository interface
 */
public class GroupRepositoryImpl implements GroupRepository {
    /**
     * sql query for save group
     */
    private final String createQuery = """
            INSERT INTO groups (name) VALUES (?)
            """;
    /**
     * sql query for read group
     */
    private final String readQuery = """
            SELECT * FROM groups
            WHERE id = ?
            """;
    /**
     * sql query for update group
     */
    private final String updateQuery = """
            UPDATE groups SET name = ?
            WHERE id = ?
            """;
    /**
     * sql query for remove group
     */
    private final String removeQuery = """
            DELETE FROM groups
            WHERE id = ?
            """;
    /**
     * sql query for get all groups
     */
    private final String getAllGroupQuery = """
            SELECT * FROM groups
            """;

    /**
     * method save new group in database
     *
     * @param group group for save
     * @return saved group
     */
    @Override
    public Group createGroup(Group group) {
        if (group.getId() != 0) throw new IllegalStateException("new group id must be 0");
        try (PreparedStatement preparedStatement =
                     DataBase.getConnection().prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, group.getName());
            int insertRow = preparedStatement.executeUpdate();
            if (insertRow == 0) throw new RuntimeException("group not save");
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            group.setId(generatedKeys.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return group;
    }

    /**
     * method update group in database
     *
     * @param group group for update
     * @return updated group
     */
    @Override
    public Group updateGroup(Group group) {
        if (group.getId() == 0) throw new IllegalStateException("group id not must be 0");
        try (PreparedStatement preparedStatement = DataBase.getConnection().prepareStatement(updateQuery)) {
            preparedStatement.setString(1, group.getName());
            preparedStatement.setInt(2, group.getId());
            int insertRow = preparedStatement.executeUpdate();
            if (insertRow == 0) throw new RuntimeException("group not update");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return group;

    }

    /**
     * method read group from database by id
     *
     * @param groupId group id in database
     * @return group
     */
    @Override
    public Group getGroup(int groupId) {
        try (PreparedStatement preparedStatement = DataBase.getConnection().prepareStatement(readQuery)) {
            preparedStatement.setInt(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return GroupMapper.mapGroup(resultSet);
            else throw new RuntimeException("not row to map");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method remove group from database
     *
     * @param groupId group id in database
     * @return removed group
     */
    @Override
    public boolean removeGroup(int groupId) {
        try (PreparedStatement preparedStatement = DataBase.getConnection().prepareStatement(removeQuery)) {
            preparedStatement.setInt(1, groupId);
            int removeRow = preparedStatement.executeUpdate();
            if (removeRow == 0) throw new RuntimeException("group not removed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * method return list all group from database
     *
     * @return list group
     */
    @Override
    public List<Group> getAllGroup() {
        List<Group> groupList = new ArrayList<>();
        try (PreparedStatement preparedStatement = DataBase.getConnection().prepareStatement(getAllGroupQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groupList.add(GroupMapper.mapGroup(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groupList;
    }
}
