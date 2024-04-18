package crud_app.service;

import crud_app.dto.TopicDto;

import java.util.List;

public interface TopicService {
    TopicDto create(TopicDto topic);
    TopicDto update(TopicDto topic);
    TopicDto get(int topicId);
    boolean remove(int topicId);
    List<TopicDto> getAll();
}
