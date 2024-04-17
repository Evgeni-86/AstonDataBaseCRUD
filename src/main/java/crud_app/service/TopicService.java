package crud_app.service;

import crud_app.entity.Topic;

import java.util.List;

public interface TopicService {
    Topic save(Topic topic);
    Topic update(Topic topic);
    Topic get(int topicId);
    boolean remove(int topicId);
    List<Topic> getAll();
}
