package crud_app.repository;

import crud_app.entity.Topic;
import crud_app.entity.TopicMessage;

import java.time.LocalDateTime;
import java.util.List;


public interface TopicRepository {
    Topic createTopic(Topic topic);
    Topic updateTopic(Topic topic);
    Topic getTopic(int topicId);
    boolean removeTopic(int topicId);
    List<Topic> getAllTopic();
}
