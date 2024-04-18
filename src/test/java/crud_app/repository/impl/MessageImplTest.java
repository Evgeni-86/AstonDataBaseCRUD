package crud_app.repository.impl;

import crud_app.entity.Topic;
import crud_app.entity.TopicMessage;
import org.junit.jupiter.api.*;

import java.util.List;


class MessageImplTest extends AbstractTest {

    private MessageRepositoryImpl SUT = new MessageRepositoryImpl();
    private TopicRepositoryImpl topicRepository = new TopicRepositoryImpl();

    @Test
    @DisplayName("create new topic message")
    void createMessage() {
        //Arrange
        Topic topic = new Topic("createMessage");
        topicRepository.createTopic(topic);
        TopicMessage newTopicMessage = new TopicMessage("Title createMessage", "createMessage");
        //Act
        TopicMessage actual = SUT.createMessage(topic.getId(), newTopicMessage);
        //Assert
        Assertions.assertTrue(newTopicMessage.getId() > 0);
        Assertions.assertEquals(newTopicMessage, actual);
        Assertions.assertEquals(newTopicMessage, SUT.getMessage(newTopicMessage.getId()));
    }

    @Test
    @DisplayName("update topic message")
    void updateMessage() {
        //Arrange
        Topic topic = new Topic("updateMessage");
        topicRepository.createTopic(topic);
        TopicMessage newTopicMessage = new TopicMessage("Title updateMessage", "updateMessage");
        SUT.createMessage(topic.getId(), newTopicMessage);
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
        Topic topic = new Topic("getMessage");
        topicRepository.createTopic(topic);
        TopicMessage newTopicMessage = new TopicMessage("Title getMessage", "getMessage");
        SUT.createMessage(topic.getId(), newTopicMessage);
        //Act
        TopicMessage actual = SUT.getMessage(newTopicMessage.getId());
        //Assert
        Assertions.assertEquals(newTopicMessage, actual);
    }

    @Test
    @DisplayName("remove topic message by id")
    void removeMessage() {
        //Arrange
        Topic topic = new Topic("removeMessage");
        topicRepository.createTopic(topic);
        TopicMessage newTopicMessage = new TopicMessage("Title removeMessage", "removeMessage");
        SUT.createMessage(topic.getId(), newTopicMessage);
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
        Topic topic = new Topic("getAllMessageTopic");
        topicRepository.createTopic(topic);
        TopicMessage newTopicMessage1 = new TopicMessage("Title getAllMessageTopic", "getAllMessageTopic");
        TopicMessage newTopicMessage2 = new TopicMessage("Title getAllMessageTopic", "getAllMessageTopic");
        TopicMessage newTopicMessage3 = new TopicMessage("Title getAllMessageTopic", "getAllMessageTopic");
        SUT.createMessage(topic.getId(), newTopicMessage1);
        SUT.createMessage(topic.getId(), newTopicMessage2);
        SUT.createMessage(topic.getId(), newTopicMessage3);
        //Act
        List<TopicMessage> actual  = SUT.getAllMessageTopic(topic.getId());
        //Assert
        Assertions.assertIterableEquals(List.of(newTopicMessage1, newTopicMessage2, newTopicMessage3), actual);
    }
}