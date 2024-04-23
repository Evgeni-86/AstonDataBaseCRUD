package crud_app.service.impl;

import crud_app.dto.TopicDto;
import crud_app.dto.TopicMessageDto;
import crud_app.entity.Topic;
import crud_app.entity.TopicMessage;
import crud_app.repository.MessageRepository;
import crud_app.repository.TopicRepository;
import crud_app.service.TopicMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class TopicMessageServiceImplTest {

    @Mock
    private MessageRepository messageRepository;
    @InjectMocks
    private TopicMessageService topicMessageService = new TopicMessageServiceImpl();


    @Test
    @DisplayName("create new message")
    void create() {
        //Arrange
        Topic topic = new Topic();
        TopicMessageDto topicMessageDto = new TopicMessageDto(1, "New Title", "New");
        topic.setId(topicMessageDto.getTopicId());
        TopicMessage newTopicMessage = new TopicMessage(
                topicMessageDto.getId(),
                topicMessageDto.getTitle(),
                topicMessageDto.getBody(),
                topic);
        TopicMessage savedTopicMessage = new TopicMessage(1, "New Title", "New");
        Mockito.when(messageRepository.createMessage(newTopicMessage)).thenReturn(savedTopicMessage);
        //Act
        TopicMessageDto actual = topicMessageService.create(topicMessageDto);
        //Assert
        Mockito.verify(messageRepository, times(1)).createMessage(newTopicMessage);
        assertEquals(savedTopicMessage.getId(), actual.getId());
        assertEquals(savedTopicMessage.getTitle(), actual.getTitle());
        assertEquals(savedTopicMessage.getBody(), actual.getBody());
    }

    @Test
    @DisplayName("update message")
    void update() {
        //Arrange
        Topic topic = new Topic();
        TopicMessageDto topicMessageDto = new TopicMessageDto(1, 1, "New Title", "New");
        topic.setId(topicMessageDto.getTopicId());
        TopicMessage updateTopicMessage = new TopicMessage(
                topicMessageDto.getId(),
                topicMessageDto.getTitle(),
                topicMessageDto.getBody(),
                topic);
        TopicMessage savedTopicMessage = new TopicMessage(1, "New Title", "New", topic);
        Mockito.when(messageRepository.updateMessage(updateTopicMessage)).thenReturn(savedTopicMessage);
        //Act
        TopicMessageDto actual = topicMessageService.update(topicMessageDto);
        //Assert
        Mockito.verify(messageRepository, times(1)).updateMessage(updateTopicMessage);
        assertEquals(topicMessageDto, actual);
    }

    @Test
    @DisplayName("get message by id")
    void get() {
        //Arrange
        int messageId = 1;
        Topic topic = new Topic();
        TopicMessageDto topicMessageDto = new TopicMessageDto(1, 1, "New Title", "New");
        topic.setId(topicMessageDto.getTopicId());
        TopicMessage topicMessage = new TopicMessage(1, "New Title", "New", topic);
        Mockito.when(messageRepository.getMessage(messageId)).thenReturn(topicMessage);
        //Act
        TopicMessageDto actual = topicMessageService.get(messageId);
        //Assert
        Mockito.verify(messageRepository, times(1)).getMessage(messageId);
        assertEquals(topicMessageDto, actual);
    }

    @Test
    @DisplayName("remove message by id")
    void remove() {
        //Arrange
        int messageId = 1;
        Mockito.when(messageRepository.removeMessage(messageId)).thenReturn(true);
        //Act
        boolean actual = topicMessageService.remove(messageId);
        //Assert
        Mockito.verify(messageRepository, times(1)).removeMessage(messageId);
        Assertions.assertTrue(actual);
    }

    @Test
    void getAllMessage() {
        //Arrange
        Topic topic = new Topic();
        topic.setId(1);
        List<TopicMessage> test = List.of(
                new TopicMessage(1, "New Title 1", "New 1", topic),
                new TopicMessage(2, "New Title 2", "New 2", topic)
        );
        List<TopicMessageDto> expected = test.stream()
                .map(e -> new TopicMessageDto(e.getId(), e.getTopic().getId(), e.getTitle(), e.getBody())).toList();
        Mockito.when(messageRepository.getAllMessageTopic(1)).thenReturn(test);
        //Act
        List<TopicMessageDto> actual = topicMessageService.getAllMessage(1);
        //Assert
        Mockito.verify(messageRepository, times(1)).getAllMessageTopic(1);
        Assertions.assertIterableEquals(expected, actual);
    }
}