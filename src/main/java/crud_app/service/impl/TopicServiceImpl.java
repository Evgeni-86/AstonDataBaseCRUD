package crud_app.service.impl;

import crud_app.dto.TopicDto;
import crud_app.entity.Topic;
import crud_app.repository.TopicRepository;
import crud_app.repository.impl.TopicRepositoryImpl;
import crud_app.service.TopicService;
import crud_app.utils.TopicFactory;

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
     * topic factory
     */
    private TopicFactory topicFactory = new TopicFactory();

    /**
     * method save topic
     *
     * @param topicDto topic dto
     * @return saved topic dto
     */
    @Override
    public TopicDto create(TopicDto topicDto) {
        Topic newTopic = topicFactory.getTopicMessage(topicDto);
        Topic result = topicRepository.createTopic(newTopic);
        topicDto.setId(result.getId());
        return topicDto;
    }

    /**
     * method update topic
     *
     * @param topicDto topic dto
     * @return updated topic dto
     */
    @Override
    public TopicDto update(TopicDto topicDto) {
        Topic updateTopic = topicFactory.getTopicMessage(topicDto);
        topicRepository.updateTopic(updateTopic);
        return topicDto;
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
     * method return list topic dto by group id
     *
     * @param groupId group id in database
     * @return list topic dto
     */
    @Override
    public List<TopicDto> getAllTopicGroup(int groupId) {
        return topicRepository.getAllTopicGroup(groupId).stream()
                .map(TopicDto::toDTO)
                .sorted(Comparator.comparingInt(TopicDto::getId)).toList();
    }

    /**
     * method return list all topics dto
     *
     * @return list topic dto
     */
    @Override
    public List<TopicDto> getAllTopic() {
        return topicRepository.getAllTopic().stream()
                .map(TopicDto::toDTO)
                .sorted(Comparator.comparingInt(TopicDto::getId)).toList();
    }
}
