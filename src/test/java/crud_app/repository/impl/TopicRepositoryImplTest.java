package crud_app.repository.impl;

import crud_app.entity.Topic;
import crud_app.entity.TopicMessage;
import crud_app.utils.DataBase;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


class TopicRepositoryImplTest extends AbstractRepositoryTest {

    private TopicRepositoryImpl SUT = new TopicRepositoryImpl();
    private MessageRepositoryImpl messageRepository = new MessageRepositoryImpl();

    @Test
    @DisplayName("create new topic")
    void createTopic() {
        //Arrange
        Topic newTopic = new Topic("New Topic createTopic");
        //Act
        Topic actual = SUT.createTopic(newTopic);
        //Assert
        Assertions.assertTrue(newTopic.getId() > 0);
        Assertions.assertEquals(newTopic, actual);
        Assertions.assertEquals(newTopic, SUT.getTopic(newTopic.getId()));
    }

    @Test
    @DisplayName("update topic")
    void updateTopic() {
        //Arrange
        Topic newTopic = new Topic("New Topic updateTopic");
        SUT.createTopic(newTopic);
        Topic topicFromDB = SUT.getTopic(newTopic.getId());
        //Act
        topicFromDB.setName("New Topic 3 Update");
        SUT.updateTopic(topicFromDB);
        //Assert
        Assertions.assertEquals(topicFromDB, SUT.getTopic(topicFromDB.getId()));
    }

    @Test
    @DisplayName("get topic by id")
    void getTopic() {
        //Arrange
        Topic newTopic = new Topic("New Topic getTopic");
        SUT.createTopic(newTopic);
        //Act
        Topic actual = SUT.getTopic(newTopic.getId());
        //Assert
        Assertions.assertEquals(newTopic, actual);
    }

    @Test
    @DisplayName("remove topic by id")
    void removeTopic() {
        //Arrange
        Topic newTopic = new Topic("New Topic removeTopic");
        SUT.createTopic(newTopic);
        TopicMessage message = new TopicMessage("Title 1", "Message 1");
        messageRepository.createMessage(newTopic.getId(), message);
        //Act
        boolean actual = SUT.removeTopic(newTopic.getId());
        //Assert
        Assertions.assertTrue(actual);
        Exception exception = Assertions.assertThrows(RuntimeException.class,
                () -> SUT.getTopic(newTopic.getId()));
        Assertions.assertEquals("not row to map", exception.getMessage());
        Assertions.assertTrue(messageRepository.getAllMessageTopic(newTopic.getId()).isEmpty());
    }

    @Test
    @DisplayName("get all topic")
    void getAllTopic() {
        //Arrange
        String query = "DELETE FROM topics";
        try (Statement preparedStatement = DataBase.getConnection().createStatement()) {
            preparedStatement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Topic> expected = new ArrayList<>() {{
            add(new Topic("Topic1"));
            add(new Topic("Topic2"));
            add(new Topic("Topic3"));
        }};
        expected.forEach(topic -> SUT.createTopic(topic));
        //Act
        List<Topic> actual = SUT.getAllTopic();
        //Assert
        Assertions.assertIterableEquals(expected, actual);
    }
}