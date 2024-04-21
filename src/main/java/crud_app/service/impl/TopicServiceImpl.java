package crud_app.service.impl;

import crud_app.dto.TopicDto;
import crud_app.entity.Topic;
import crud_app.repository.TopicRepository;
import crud_app.repository.impl.TopicRepositoryImpl;
import crud_app.service.TopicService;

import java.util.Comparator;
import java.util.List;

/**
 * this is implementation topic service interface
 */
public class TopicServiceImpl implements TopicService {
    /**
     * topic repository interface
     */
    private TopicRepository topicRepository = new TopicRepositoryImpl();

    /**
     * method save topic
     *
     * @param topic topic dto
     * @return saved topic dto
     */
    @Override
    public TopicDto create(TopicDto topic) {
        Topic newTopic = new Topic(topic.getId(), topic.getName());
        Topic result = topicRepository.createTopic(newTopic);
        topic.setId(result.getId());
        return topic;
    }

    /**
     * method update topic
     *
     * @param topic topic dto
     * @return updated topic dto
     */
    @Override
    public TopicDto update(TopicDto topic) {
        Topic updateTopic = new Topic(topic.getId(), topic.getName());
        topicRepository.updateTopic(updateTopic);
        return topic;
    }

    /**
     * method get topic dto by id
     *
     * @param topicId topic id in database
     * @return topic dto
     */
    @Override
    public TopicDto get(int topicId) {
        Topic topic = topicRepository.getTopic(topicId);
        return TopicDto.toDTO(topic);
    }

    /**
     * method delete topic by id
     *
     * @param topicId topic id in database
     * @return result (true - removed)
     */
    @Override
    public boolean remove(int topicId) {
        return topicRepository.removeTopic(topicId);
    }

    /**
     * method get all topic dto
     *
     * @return list topic dto
     */
    @Override
    public List<TopicDto> getAll() {
        return topicRepository.getAllTopic().stream()
                .map(e -> TopicDto.toDTO(e))
                .sorted(Comparator.comparingInt(TopicDto::getId))
                .toList();
    }
}
