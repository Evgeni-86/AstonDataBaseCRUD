package crud_app.service.impl;

import crud_app.dto.TopicDto;
import crud_app.entity.Topic;
import crud_app.repository.TopicRepository;
import crud_app.repository.impl.TopicRepositoryImpl;
import crud_app.service.TopicService;

import java.util.List;
import java.util.Optional;

public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository = new TopicRepositoryImpl();

    @Override
    public TopicDto create(TopicDto topic) {
        Topic newTopic = new Topic(topic.getId(), topic.getName());
        Topic result = topicRepository.createTopic(newTopic);
        topic.setId(result.getId());
        return topic;
    }

    @Override
    public TopicDto update(TopicDto topic) {
        Topic updateTopic = new Topic(topic.getId(), topic.getName());
        topicRepository.updateTopic(updateTopic);
        return topic;
    }

    @Override
    public TopicDto get(int topicId) {
        Topic topic = topicRepository.getTopic(topicId);
        return TopicDto.toDTO(topic);
    }

    @Override
    public boolean remove(int topicId) {
        return topicRepository.removeTopic(topicId);
    }

    @Override
    public List<TopicDto> getAll() {
        return topicRepository.getAllTopic().stream().map(e -> TopicDto.toDTO(e)).toList();
    }
}
