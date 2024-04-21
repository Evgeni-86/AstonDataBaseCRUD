package crud_app.service.impl;

import crud_app.dto.TopicMessageDto;
import crud_app.entity.Topic;
import crud_app.entity.TopicMessage;
import crud_app.repository.MessageRepository;
import crud_app.repository.impl.MessageRepositoryImpl;
import crud_app.service.TopicMessageService;

import java.util.Comparator;
import java.util.List;

/**
 * this is implementation topic message service interface
 */
public class TopicMessageServiceImpl implements TopicMessageService {
    /**
     * topic message repository interface
     */
    private MessageRepository messageRepository = new MessageRepositoryImpl();

    /**
     * method save topic message
     *
     * @param topicMessage topic message dto
     * @return saved topic message dto
     */
    @Override
    public TopicMessageDto create(TopicMessageDto topicMessage) {
        Topic topic = new Topic();
        topic.setId(topicMessage.getTopicId());
        TopicMessage message = new TopicMessage(
                topicMessage.getId(),
                topicMessage.getTitle(),
                topicMessage.getBody(),
                topic);
        TopicMessage result = messageRepository.createMessage(message);
        topicMessage.setId(result.getId());
        return topicMessage;
    }

    /**
     * method update topic message
     *
     * @param topicMessage topic message dto
     * @return updated topic message dto
     */
    @Override
    public TopicMessageDto update(TopicMessageDto topicMessage) {
        Topic topic = new Topic();
        topic.setId(topicMessage.getTopicId());
        TopicMessage message = new TopicMessage(
                topicMessage.getId(),
                topicMessage.getTitle(),
                topicMessage.getBody(),
                topic);
        messageRepository.updateMessage(message);
        return topicMessage;
    }

    /**
     * method get topic message dto by id
     *
     * @param messageId topic message id in database
     * @return topic message dto
     */
    @Override
    public TopicMessageDto get(int messageId) {
        TopicMessage message = messageRepository.getMessage(messageId);
        return TopicMessageDto.toDTO(message);
    }

    /**
     * method delete topic message by id
     *
     * @param messageId topic message id in database
     * @return result (true - removed)
     */
    @Override
    public boolean remove(int messageId) {
        return messageRepository.removeMessage(messageId);
    }

    /**
     * method get all topic messages dto by topic id
     *
     * @return list topic message dto
     */
    @Override
    public List<TopicMessageDto> getAllMessage(int topicId) {
        return messageRepository.getAllMessageTopic(topicId).stream()
                .map(e -> TopicMessageDto.toDTO(e))
                .sorted(Comparator.comparingInt(TopicMessageDto::getId)).toList();
    }
}
