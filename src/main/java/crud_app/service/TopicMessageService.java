package crud_app.service;

import crud_app.dto.TopicMessageDto;

import java.util.List;

/**
 * this is topic message service interface
 */
public interface TopicMessageService {
    /**
     * method save topic message dto
     *
     * @param topicMessage topic message dto
     * @return saved topic message dto
     */
    TopicMessageDto create(TopicMessageDto topicMessage);

    /**
     * method update topic message dto
     *
     * @param topicMessage topic message dto
     * @return updated topic message dto
     */
    TopicMessageDto update(TopicMessageDto topicMessage);

    /**
     * method get topic message dto
     *
     * @param messageId topic message id in database
     * @return topic message dto
     */
    TopicMessageDto get(int messageId);

    /**
     * method delete topic message
     *
     * @param messageId topic message id in database
     * @return result (true - removed)
     */
    boolean remove(int messageId);

    /**
     * method get all messages dto by topic id
     *
     * @return list topic messages dto
     */
    List<TopicMessageDto> getAllMessage(int topicId);
}
