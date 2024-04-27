package crud_app.repository.impl;

import crud_app.entity.Topic;
import crud_app.repository.TopicRepository;
import crud_app.utils.DataBase;
import crud_app.utils.TopicMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * this is implementation topic repository interface
 */
public class TopicRepositoryImpl implements TopicRepository {
    /**
     * sql query for save topic
     */
    private final String createQuery = """
            INSERT INTO topics (name) VALUES (?)
            """;
    /**
     * sql query for save id`s in topic_groups table
     */
    private final String saveInTopicGroupsQuery = """
            INSERT INTO topic_groups (group_id, topic_id) VALUES (?, ?)
            """;
    /**
     * sql query for read topic
     */
    private final String readQuery = """
            SELECT t.name AS "topic_name", t.id AS "topic_id", g.name AS "group_name", g.id AS "group_id"
            FROM topics AS t
            LEFT JOIN topic_groups ON topic_groups.topic_id = t.id
            LEFT JOIN groups AS g ON topic_groups.group_id = g.id
            WHERE t.id = ?
            """;
    /**
     * sql query for update topic
     */
    private final String updateQuery = """
            UPDATE topics SET name = ? 
            WHERE id = ?
            """;
    /**
     * sql query for remove topic
     */
    private final String removeQuery = """
            DELETE FROM topics 
            WHERE id = ?
            """;
    /**
     * sql query for get all topics by group id
     */
    private final String getAllTopicGroupQuery = """
            SELECT t.name AS "topic_name", t.id AS "topic_id", g.name AS "group_name", g.id AS "group_id"
            FROM topics AS t
            JOIN topic_groups ON topic_groups.topic_id = t.id
            JOIN groups AS g ON topic_groups.group_id = g.id
            WHERE g.id = ?
            """;
    /**
     * sql query for get all topics
     */
    private final String getAllTopicQuery = """
            SELECT t.name AS "topic_name", t.id AS "topic_id", g.name AS "group_name", g.id AS "group_id"
            FROM topics AS t
            LEFT JOIN topic_groups ON topic_groups.topic_id = t.id
            LEFT JOIN groups AS g ON topic_groups.group_id = g.id
            """;

    /**
     * method save new topic in database
     *
     * @param topic topic for save
     * @return saved topic
     */
    @Override
    public Topic createTopic(Topic topic) {
        if (topic.getId() != 0) throw new IllegalStateException("new topic id must be 0");
        if (topic.getGroup().getId() == 0) throw new IllegalStateException("group id not must be 0");

        Connection connection = DataBase.getConnection();
        try (PreparedStatement preparedStatementTopic = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedStatementTopicGroups = connection.prepareStatement(saveInTopicGroupsQuery)) {
            connection.setAutoCommit(false);

            preparedStatementTopic.setString(1, topic.getName());
            preparedStatementTopic.executeUpdate();
            ResultSet generatedKeys = preparedStatementTopic.getGeneratedKeys();
            generatedKeys.next();
            topic.setId(generatedKeys.getInt(1));

            preparedStatementTopicGroups.setInt(1, topic.getGroup().getId());
            preparedStatementTopicGroups.setInt(2, topic.getId());
            preparedStatementTopicGroups.execute();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException(ex);
        }

        return topic;
    }

    /**
     * method update topic in database
     *
     * @param topic topic for update
     * @return updated topic
     */
    @Override
    public Topic updateTopic(Topic topic) {
        if (topic.getId() == 0) throw new IllegalStateException("topic id not must be 0");
        if (topic.getGroup().getId() == 0) throw new IllegalStateException("group id not must be 0");
        try (PreparedStatement preparedStatement = DataBase.getConnection().prepareStatement(updateQuery)) {
            preparedStatement.setString(1, topic.getName());
            preparedStatement.setInt(2, topic.getId());
            int insertRow = preparedStatement.executeUpdate();
            if (insertRow == 0) throw new RuntimeException("topic not update");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topic;
    }

    /**
     * method read topic from database by id
     *
     * @param topicId topic id in database
     * @return topic
     */
    @Override
    public Topic getTopic(int topicId) {
        try (PreparedStatement preparedStatement = DataBase.getConnection().prepareStatement(readQuery)) {
            preparedStatement.setInt(1, topicId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return TopicMapper.mapTopic(resultSet);
            else throw new RuntimeException("not row to map");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method remove topic from database
     *
     * @param topicId topic id in database
     * @return removed topic
     */
    @Override
    public boolean removeTopic(int topicId) {
        try (PreparedStatement preparedStatement = DataBase.getConnection().prepareStatement(removeQuery)) {
            preparedStatement.setInt(1, topicId);
            int removeRow = preparedStatement.executeUpdate();
            if (removeRow == 0) throw new RuntimeException("topic not removed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * method return list topic for group by id from database
     *
     * @param groupId group id in database
     * @return list topic
     */
    @Override
    public List<Topic> getAllTopicGroup(int groupId) {
        List<Topic> topicList = new ArrayList<>();
        try (PreparedStatement preparedStatement = DataBase.getConnection().prepareStatement(getAllTopicGroupQuery)) {
            preparedStatement.setInt(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                topicList.add(TopicMapper.mapTopic(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicList;
    }

    /**
     * method return list all topics from database
     *
     * @return topic list
     */
    @Override
    public List<Topic> getAllTopic() {
        List<Topic> topicList = new ArrayList<>();
        try (PreparedStatement preparedStatement = DataBase.getConnection().prepareStatement(getAllTopicQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                topicList.add(TopicMapper.mapTopic(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicList;
    }
}
