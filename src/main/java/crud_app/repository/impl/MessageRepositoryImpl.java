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

/**
 * this is implementation message repository interface
 */
public class MessageRepositoryImpl implements MessageRepository {
    /**
     * sql query for save message
     */
    private final String createQuery = """
            INSERT INTO topic_messages (topic_id, title, body) 
            VALUES (?, ?, ?)
            """;
    /**
     * sql query for read message
     */
    private final String readQuery = """
            SELECT * FROM topic_messages 
            JOIN topics ON topic_messages.topic_id = topics.id 
            WHERE topic_messages.id = ?
            """;
    /**
     * sql query for update message
     */
    private final String updateQuery = """
            UPDATE topic_messages 
            SET topic_id = ?, title = ?, body = ? 
            WHERE id = ?
            """;
    /**
     * sql query for remove message
     */
    private final String removeQuery = """
            DELETE FROM topic_messages 
            WHERE id = ?
            """;
    /**
     * sql query for get all message one topic
     */
    private final String getAllMessageTopicQuery = """
            SELECT * FROM topic_messages 
            JOIN topics ON topic_messages.topic_id = topics.id 
            WHERE topic_id = ?
            """;

    /**
     * method save new message in database
     *
     * @param topicMessage message for save
     * @return saved message
     */
    @Override
    public TopicMessage createMessage(TopicMessage topicMessage) {
        if (topicMessage.getId() != 0) throw new IllegalStateException("new topic message id must be 0");
        if (topicMessage.getTopic().getId() == 0) throw new IllegalStateException("topic id not must be 0");
        try (PreparedStatement preparedStatement =
                     DataBase.getConnection().prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, topicMessage.getTopic().getId());
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

    /**
     * method update message in database
     *
     * @param topicMessage message for update
     * @return updated message
     */
    @Override
    public TopicMessage updateMessage(TopicMessage topicMessage) {
        if (topicMessage.getId() == 0) throw new IllegalStateException("topic message id not must be 0");
        if (topicMessage.getTopic().getId() == 0) throw new IllegalStateException("topic id not must be 0");
        try (PreparedStatement preparedStatement = DataBase.getConnection().prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, topicMessage.getTopic().getId());
            preparedStatement.setString(2, topicMessage.getTitle());
            preparedStatement.setString(3, topicMessage.getBody());
            preparedStatement.setInt(4, topicMessage.getId());
            int insertRow = preparedStatement.executeUpdate();
            if (insertRow == 0) throw new RuntimeException("topic message not update");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return topicMessage;
    }

    /**
     * method read message from database by id
     *
     * @param messageId message id in database
     * @return message
     */
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

    /**
     * method remove message from database
     *
     * @param messageId message id in database
     * @return removed message
     */
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

    /**
     * method return list all messages one topic from database
     *
     * @param topicId topic id in database
     * @return list messages
     */
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
