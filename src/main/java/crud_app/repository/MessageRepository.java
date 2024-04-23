package crud_app.repository;

import crud_app.entity.TopicMessage;

import java.util.List;

/**
 * this is message repository interface
 */
public interface MessageRepository {
    /**
     * method save new message in database
     *
     * @param topicMessage message for save
     * @return saved message
     */
    TopicMessage createMessage(TopicMessage topicMessage);

    /**
     * method update message in database
     *
     * @param topicMessage message for update
     * @return updated message
     */
    TopicMessage updateMessage(TopicMessage topicMessage);

    /**
     * method read message from database by id
     *
     * @param messageId message id in database
     * @return message from database
     */
    TopicMessage getMessage(int messageId);

    /**
     * method removed message from database by id
     *
     * @param messageId message id in database
     * @return removed message
     */
    boolean removeMessage(int messageId);

    /**
     * method return list messages by topic id
     *
     * @param topicId topic id in database
     * @return messages list
     */
    List<TopicMessage> getAllMessageTopic(int topicId);
}
