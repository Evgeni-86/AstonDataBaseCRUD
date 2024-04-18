package crud_app.repository;

import crud_app.entity.Topic;
import crud_app.entity.TopicMessage;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository {
    TopicMessage createMessage(TopicMessage topicMessage);
    TopicMessage updateMessage(TopicMessage topicMessage);
    TopicMessage getMessage(int messageId);
    boolean removeMessage(int messageId);
    List<TopicMessage> getAllMessageTopic(int topicId);
}
