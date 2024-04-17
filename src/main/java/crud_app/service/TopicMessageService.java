package crud_app.service;

import crud_app.entity.TopicMessage;

import java.util.List;

public interface TopicMessageService {
    TopicMessage create(int topicId, TopicMessage topicMessage);
    TopicMessage update(TopicMessage topicMessage);
    TopicMessage get(int messageId);
    boolean remove(int messageId);
    List<TopicMessage> getAllMessage(int topicId);
}
