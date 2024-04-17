package crud_app.repository.impl;

import crud_app.entity.Topic;
import crud_app.repository.TopicRepository;
import crud_app.utils.DataBase;
import crud_app.utils.TopicMapper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TopicRepositoryImpl implements TopicRepository {

    private final String createQuery = "INSERT INTO topics (name) VALUES (?)";
    private final String readQuery = "SELECT * FROM topics WHERE id = ?";
    private final String updateQuery = "UPDATE topics SET name = ? WHERE id = ?";
    private final String removeQuery = "DELETE FROM topics WHERE id = ?";
    private final String getAllTopicQuery = "SELECT * FROM topics";

    @Override
    public Topic createTopic(Topic topic) {
        if (topic.getId() != 0) throw new IllegalStateException("new topic id must be 0");
        try (PreparedStatement preparedStatement =
                     DataBase.getConnection().prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, topic.getName());
            int insertRow = preparedStatement.executeUpdate();
            if (insertRow == 0) throw new RuntimeException("topic not save");
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            topic.setId(generatedKeys.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topic;
    }

    @Override
    public Topic updateTopic(Topic topic) {
        if (topic.getId() == 0) throw new IllegalStateException("topic id not must be 0");
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

    @Override
    public List<Topic> getAllTopic() {
        List<Topic> topicList = new ArrayList<>();
        try (Statement statement = DataBase.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllTopicQuery);
            while (resultSet.next()) {
                topicList.add(TopicMapper.mapTopic(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicList;
    }
}
