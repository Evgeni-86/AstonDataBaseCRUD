package crud_app.service.impl;

import crud_app.dto.GroupDto;
import crud_app.dto.TopicDto;
import crud_app.entity.Group;
import crud_app.entity.Topic;
import crud_app.entity.TopicMessage;
import crud_app.repository.GroupRepository;
import crud_app.repository.TopicRepository;
import crud_app.repository.impl.GroupRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicServiceImplTest {
    @Mock
    private TopicRepository topicRepository;
    @InjectMocks
    private TopicServiceImpl topicService = new TopicServiceImpl();
    private static Group groupForTest = new Group(1, "TopicServiceImplTest");

    @Test
    @DisplayName("create new topic")
    public void create() {
        //Arrange
        TopicDto topicDto = new TopicDto(1, groupForTest.getId(), "Test Topic");
        Topic newTopic = new Topic(topicDto.getId(), topicDto.getName(), groupForTest);
        Topic savedTopic = new Topic(1, "Test Topic", groupForTest);
        Mockito.when(topicRepository.createTopic(newTopic)).thenReturn(savedTopic);
        //Act
        TopicDto actual = topicService.create(topicDto);
        //Assert
        Mockito.verify(topicRepository, times(1)).createTopic(newTopic);
        assertEquals(savedTopic.getId(), actual.getId());
        assertEquals(savedTopic.getName(), actual.getName());
    }

    @Test
    @DisplayName("update topic")
    void update() {
        //Arrange
        TopicDto topicDto = new TopicDto(1,groupForTest.getId(), "Test Topic");
        Topic updateTopic = new Topic(topicDto.getId(), topicDto.getName(), groupForTest);
        Topic savedTopic = new Topic(1,"Test Topic", groupForTest);
        Mockito.when(topicRepository.updateTopic(updateTopic)).thenReturn(savedTopic);
        //Act
        TopicDto actual = topicService.update(topicDto);
        //Assert
        Mockito.verify(topicRepository, times(1)).updateTopic(updateTopic);
        assertEquals(topicDto, actual);
    }

    @Test
    @DisplayName("get topic by id")
    void get() {
        //Arrange
        int topicId = 1;
        TopicDto topicDto = new TopicDto(1, groupForTest.getId(),"Test Topic");
        Topic topic = new Topic(1, "Test Topic", groupForTest);
        Mockito.when(topicRepository.getTopic(topicId)).thenReturn(topic);
        //Act
        TopicDto actual = topicService.get(topicId);
        //Assert
        Mockito.verify(topicRepository, times(1)).getTopic(topicId);
        assertEquals(topicDto, actual);
    }

    @Test
    @DisplayName("remove topic by id")
    void remove() {
        //Arrange
        int topicId = 1;
        Mockito.when(topicRepository.removeTopic(topicId)).thenReturn(true);
        //Act
        boolean actual = topicService.remove(topicId);
        //Assert
        Mockito.verify(topicRepository, times(1)).removeTopic(topicId);
        Assertions.assertTrue(actual);
    }

    @Test
    @DisplayName("get all topics")
    void getAll() {
        //Arrange
        Group group = new Group(1, "group");
        List<Topic> test = List.of(
                new Topic(1, "New Title 1", group),
                new Topic(2, "New Title 2", group)
        );
        List<TopicDto> expected = test.stream().map(e -> new TopicDto(e.getId(), e.getGroup().getId(), e.getName())).toList();
        Mockito.when(topicRepository.getAllTopic()).thenReturn(test);
        //Act
        List<TopicDto> actual = topicService.getAllTopic();
        //Assert
        Mockito.verify(topicRepository, times(1)).getAllTopic();
        Assertions.assertIterableEquals(expected, actual);
    }
}