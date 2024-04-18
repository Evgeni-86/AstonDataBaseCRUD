package crud_app.service;

import crud_app.dto.TopicMessageDto;
import crud_app.entity.TopicMessage;

import java.util.List;

public interface TopicMessageService {
    TopicMessageDto create(TopicMessageDto topicMessage);
    TopicMessageDto update(TopicMessageDto topicMessage);
    TopicMessageDto get(int messageId);
    boolean remove(int messageId);
    List<TopicMessageDto> getAllMessage(int topicId);
}
