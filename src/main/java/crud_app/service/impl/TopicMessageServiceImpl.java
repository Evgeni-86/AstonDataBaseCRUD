package crud_app.service.impl;

import crud_app.dto.TopicMessageDto;
import crud_app.entity.Topic;
import crud_app.entity.TopicMessage;
import crud_app.repository.MessageRepository;
import crud_app.repository.impl.MessageRepositoryImpl;
import crud_app.service.TopicMessageService;

import java.util.List;

public class TopicMessageServiceImpl implements TopicMessageService {

    private MessageRepository messageRepository = new MessageRepositoryImpl();

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

    @Override
    public TopicMessageDto get(int messageId) {
        TopicMessage message = messageRepository.getMessage(messageId);
        return TopicMessageDto.toDTO(message);
    }

    @Override
    public boolean remove(int messageId) {
        return messageRepository.removeMessage(messageId);
    }

    @Override
    public List<TopicMessageDto> getAllMessage(int topicId) {
        return messageRepository.getAllMessageTopic(topicId).stream()
                .map(e -> TopicMessageDto.toDTO(e)).toList();
    }
}
