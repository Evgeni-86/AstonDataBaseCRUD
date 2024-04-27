package crud_app.repository.impl;

import crud_app.entity.Group;
import crud_app.entity.Topic;
import crud_app.repository.GroupRepository;
import crud_app.utils.DataBase;

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
            SELECT g.name AS "group_name", g.id AS "group_id", t.name AS "topic_name", t.id AS "topic_id"
            FROM groups AS g
            LEFT JOIN topic_groups ON topic_groups.group_id = g.id
            LEFT JOIN topics AS t ON topic_groups.topic_id = t.id
            WHERE g.id = ?
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
            SELECT g.name AS "group_name", g.id AS "group_id", t.name AS "topic_name", t.id AS "topic_id"
            FROM groups AS g
            LEFT JOIN topic_groups ON topic_groups.group_id = g.id
            LEFT JOIN topics AS t ON topic_groups.topic_id = t.id
            ORDER BY g.id
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

            Group currentGroup = null;
            while (resultSet.next()) {

                if (currentGroup == null) {
                    currentGroup = new Group(resultSet.getInt("group_id"), resultSet.getString("group_name"));
                    currentGroup.setTopics(new ArrayList<>());
                }

                int currentTopicId = resultSet.getInt("topic_id");
                if (currentTopicId != 0) {
                    Topic topic = new Topic(currentTopicId, resultSet.getString("topic_name"));
                    topic.setGroup(currentGroup);
                    currentGroup.getTopics().add(topic);
                }
            }

            if (currentGroup == null) throw new RuntimeException("not row to map");
            else return currentGroup;

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

            int tempId = 0;
            Group currentGroup = null;
            while (resultSet.next()) {

                int currentId = resultSet.getInt("group_id");
                if (currentId != tempId) {
                    Group group = new Group(currentId, resultSet.getString("group_name"));
                    group.setTopics(new ArrayList<>());
                    groupList.add(group);
                    currentGroup = group;
                    tempId = group.getId();
                }

                int currentTopicId = resultSet.getInt("topic_id");
                if (currentTopicId != 0) {
                    Topic topic = new Topic(currentTopicId, resultSet.getString("topic_name"));
                    topic.setGroup(currentGroup);
                    currentGroup.getTopics().add(topic);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groupList;
    }
}
