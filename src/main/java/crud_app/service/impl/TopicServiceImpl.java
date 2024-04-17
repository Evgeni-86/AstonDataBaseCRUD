package crud_app.service.impl;

import crud_app.entity.Topic;
import crud_app.repository.TopicRepository;
import crud_app.service.TopicService;

import java.util.List;

public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository;

    @Override
    public Topic save(Topic topic) {
        return topicRepository.createTopic(topic);
    }

    @Override
    public Topic update(Topic topic) {
        return topicRepository.updateTopic(topic);
    }

    @Override
    public Topic get(int topicId) {
        return topicRepository.getTopic(topicId);
    }

    @Override
    public boolean remove(int topicId) {
        return topicRepository.removeTopic(topicId);
    }

    @Override
    public List<Topic> getAll() {
        return topicRepository.getAllTopic();
    }
}
