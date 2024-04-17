package crud_app.repository.impl;

import crud_app.entity.TopicMessage;
import crud_app.repository.MessageRepository;
import crud_app.utils.DataBase;
import crud_app.utils.TopicMessageMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageRepositoryImpl implements MessageRepository {

    private final String createQuery = "INSERT INTO topic_messages (topic_id, title, body) VALUES (?, ?, ?)";
    private final String readQuery = "SELECT * FROM topic_messages WHERE id = ?";
    private final String updateQuery = "UPDATE topic_messages SET title = ?, body = ? WHERE id = ?";
    private final String removeQuery = "DELETE FROM topic_messages WHERE id = ?";
    private final String getAllMessageTopicQuery = "SELECT * FROM topic_messages WHERE topic_id = ?";

    @Override
    public TopicMessage createMessage(int topicId, TopicMessage topicMessage) {
        if (topicMessage.getId() != 0) throw new IllegalStateException("new topic message id must be 0");
        try (PreparedStatement preparedStatement =
                     DataBase.getConnection().prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, topicId);
            preparedStatement.setString(2, topicMessage.getTitle());
            preparedStatement.setString(3, topicMessage.getBody());
            int insertRow = preparedStatement.executeUpdate();
            if (insertRow == 0) throw new RuntimeException("topic message not save");
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            topicMessage.setId(generatedKeys.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicMessage;
    }

    @Override
    public TopicMessage updateMessage(TopicMessage topicMessage) {
        if (topicMessage.getId() == 0) throw new IllegalStateException("topic message id not must be 0");
        try (PreparedStatement preparedStatement = DataBase.getConnection().prepareStatement(updateQuery)) {
            preparedStatement.setString(1, topicMessage.getTitle());
            preparedStatement.setString(2, topicMessage.getBody());
            preparedStatement.setInt(3, topicMessage.getId());
            int insertRow = preparedStatement.executeUpdate();
            if (insertRow == 0) throw new RuntimeException("topic message not update");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicMessage;
    }

    @Override
    public TopicMessage getMessage(int messageId) {
        try (PreparedStatement preparedStatement = DataBase.getConnection().prepareStatement(readQuery)) {
            preparedStatement.setInt(1, messageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return TopicMessageMapper.mapTopicMessage(resultSet);
            else throw new RuntimeException("not row to map");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeMessage(int messageId) {
        try (PreparedStatement preparedStatement = DataBase.getConnection().prepareStatement(removeQuery)) {
            preparedStatement.setInt(1, messageId);
            int removeRow = preparedStatement.executeUpdate();
            if (removeRow == 0) throw new RuntimeException("topic message not removed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public List<TopicMessage> getAllMessageTopic(int topicId) {
        List<TopicMessage> topicMessageList = new ArrayList<>();
        try (PreparedStatement preparedStatement = DataBase.getConnection().prepareStatement(getAllMessageTopicQuery)) {
            preparedStatement.setInt(1, topicId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                topicMessageList.add(TopicMessageMapper.mapTopicMessage(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicMessageList;
    }
}
