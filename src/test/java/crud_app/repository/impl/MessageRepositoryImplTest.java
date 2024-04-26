package crud_app.repository.impl;

import crud_app.AbstractTest;
import crud_app.entity.Group;
import crud_app.entity.Topic;
import crud_app.entity.TopicMessage;
import crud_app.repository.GroupRepository;
import org.junit.jupiter.api.*;

import java.util.List;


class MessageRepositoryImplTest extends AbstractTest {

    private MessageRepositoryImpl SUT = new MessageRepositoryImpl();
    private TopicRepositoryImpl topicRepository = new TopicRepositoryImpl();

    private static Group groupForTest;

    @BeforeAll
    static void init() {
        GroupRepository groupRepository = new GroupRepositoryImpl();
        groupForTest = groupRepository.createGroup(new Group("MessageRepositoryImplTest"));
    }

    @Test
    @DisplayName("create new topic message")
    void createMessage() {
        //Arrange
        Topic topic = new Topic("createMessage", groupForTest);
        topicRepository.createTopic(topic);
        TopicMessage newTopicMessage = new TopicMessage("Title createMessage", "createMessage", topic);
        //Act
        TopicMessage actual = SUT.createMessage(newTopicMessage);
        //Assert
        Assertions.assertTrue(newTopicMessage.getId() > 0);
        Assertions.assertEquals(newTopicMessage, actual);
        Assertions.assertEquals(newTopicMessage, SUT.getMessage(newTopicMessage.getId()));
    }

    @Test
    @DisplayName("update topic message")
    void updateMessage() {
        //Arrange
        Topic topic = new Topic("updateMessage", groupForTest);
        topicRepository.createTopic(topic);
        TopicMessage newTopicMessage = new TopicMessage("Title updateMessage", "updateMessage", topic);
        SUT.createMessage(newTopicMessage);
        TopicMessage topicMessageFromDB = SUT.getMessage(newTopicMessage.getId());
        //Act
        topicMessageFromDB.setTitle("Title updateMessage update");
        topicMessageFromDB.setBody("updateMessage update");
        SUT.updateMessage(topicMessageFromDB);
        //Assert
        Assertions.assertEquals(topicMessageFromDB, SUT.getMessage(topicMessageFromDB.getId()));
    }

    @Test
    @DisplayName("get topic message by id")
    void getMessage() {
        //Arrange
        Topic topic = new Topic("getMessage", groupForTest);
        topicRepository.createTopic(topic);
        TopicMessage newTopicMessage = new TopicMessage("Title getMessage", "getMessage", topic);
        SUT.createMessage(newTopicMessage);
        //Act
        TopicMessage actual = SUT.getMessage(newTopicMessage.getId());
        //Assert
        Assertions.assertEquals(newTopicMessage, actual);
    }

    @Test
    @DisplayName("remove topic message by id")
    void removeMessage() {
        //Arrange
        Topic topic = new Topic("removeMessage", groupForTest);
        topicRepository.createTopic(topic);
        TopicMessage newTopicMessage = new TopicMessage("Title removeMessage", "removeMessage", topic);
        SUT.createMessage(newTopicMessage);
        //Act
        boolean actual = SUT.removeMessage(newTopicMessage.getId());
        //Assert
        Assertions.assertTrue(actual);
        Exception exception = Assertions.assertThrows(RuntimeException.class,
                () -> SUT.getMessage(newTopicMessage.getId()));
        Assertions.assertEquals("not row to map", exception.getMessage());
    }

    @Test
    @DisplayName("get all message topic by id")
    void getAllMessageTopic() {
        //Arrange
        Topic topic = new Topic("getAllMessageTopic", groupForTest);
        topicRepository.createTopic(topic);
        TopicMessage newTopicMessage1 = new TopicMessage("Title getAllMessageTopic", "getAllMessageTopic", topic);
        TopicMessage newTopicMessage2 = new TopicMessage("Title getAllMessageTopic", "getAllMessageTopic", topic);
        TopicMessage newTopicMessage3 = new TopicMessage("Title getAllMessageTopic", "getAllMessageTopic", topic);
        SUT.createMessage(newTopicMessage1);
        SUT.createMessage(newTopicMessage2);
        SUT.createMessage(newTopicMessage3);
        //Act
        List<TopicMessage> actual  = SUT.getAllMessageTopic(topic.getId());
        //Assert
        Assertions.assertIterableEquals(List.of(newTopicMessage1, newTopicMessage2, newTopicMessage3), actual);
    }
}