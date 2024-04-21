package crud_app.service.impl;

import crud_app.dto.TopicMessageDto;
import crud_app.entity.TopicMessage;
import crud_app.repository.MessageRepository;
import crud_app.repository.impl.MessageRepositoryImpl;
import crud_app.service.TopicMessageService;
import crud_app.utils.TopicMessageFactory;

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
     * topic message factory
     */
    private TopicMessageFactory topicMessageFactory = new TopicMessageFactory();

    /**
     * method save topic message
     *
     * @param topicMessageDto topic message dto
     * @return saved topic message dto
     */
    @Override
    public TopicMessageDto create(TopicMessageDto topicMessageDto) {
        TopicMessage message = topicMessageFactory.getTopicMessage(topicMessageDto);
        messageRepository.createMessage(message);
        topicMessageDto.setId(message.getId());
        return topicMessageDto;
    }

    /**
     * method update topic message
     *
     * @param topicMessageDto topic message dto
     * @return updated topic message dto
     */
    @Override
    public TopicMessageDto update(TopicMessageDto topicMessageDto) {
        TopicMessage message = topicMessageFactory.getTopicMessage(topicMessageDto);
        messageRepository.updateMessage(message);
        return topicMessageDto;
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
