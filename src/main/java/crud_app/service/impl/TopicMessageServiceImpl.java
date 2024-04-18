package crud_app.service.impl;

import crud_app.entity.TopicMessage;
import crud_app.repository.MessageRepository;

import java.util.List;

public class TopicMessageServiceImpl implements MessageRepository {

    private MessageRepository messageRepository = new TopicMessageServiceImpl();

    @Override
    public TopicMessage createMessage(int topicId, TopicMessage topicMessage) {
        return messageRepository.createMessage(topicId, topicMessage);
    }

    @Override
    public TopicMessage updateMessage(TopicMessage topicMessage) {
        return messageRepository.updateMessage(topicMessage);
    }

    @Override
    public TopicMessage getMessage(int messageId) {
        return messageRepository.getMessage(messageId);
    }

    @Override
    public boolean removeMessage(int messageId) {
        return messageRepository.removeMessage(messageId);
    }

    @Override
    public List<TopicMessage> getAllMessageTopic(int topicId) {
        return messageRepository.getAllMessageTopic(topicId);
    }
}
