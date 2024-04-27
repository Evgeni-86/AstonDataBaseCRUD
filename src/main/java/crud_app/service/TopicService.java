package crud_app.service;

import crud_app.dto.TopicDto;

import java.util.List;

/**
 * this is topic service interface
 */
public interface TopicService {
    /**
     * method save topic
     *
     * @param topic topic dto
     * @return saved topic dto
     */
    TopicDto create(TopicDto topic);

    /**
     * method update topic
     *
     * @param topic topic dto
     * @return updated topic dto
     */
    TopicDto update(TopicDto topic);

    /**
     * method get topic dto by id
     *
     * @param topicId topic id in database
     * @return topic dto
     */
    TopicDto get(int topicId);

    /**
     * method delete topic by id
     *
     * @param topicId topic id in database
     * @return result (true - removed)
     */
    boolean remove(int topicId);

    /**
     * method return list topics dto by group id
     *
     * @param groupId group id in database
     * @return list topics dto
     */
    List<TopicDto> getAllTopicGroup(int groupId);

    /**
     * method return list all topics
     *
     * @return list topics dto
     */
    List<TopicDto> getAllTopic();
}
