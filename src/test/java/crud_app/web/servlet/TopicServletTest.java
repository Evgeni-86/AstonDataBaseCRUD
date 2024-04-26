package crud_app.web.servlet;

import crud_app.dto.GroupDto;
import crud_app.dto.TopicDto;
import crud_app.AbstractTest;
import crud_app.entity.Group;
import crud_app.repository.GroupRepository;
import crud_app.repository.impl.GroupRepositoryImpl;
import crud_app.service.TopicService;
import crud_app.service.impl.TopicServiceImpl;
import crud_app.utils.DataBase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class TopicServletTest extends AbstractTest {

    private TopicServlet SUT;
    private HttpServletRequest request;
    private HttpServletResponse response;

    private TopicService topicService = new TopicServiceImpl();

    private static GroupRepository groupRepository;
    private static Group groupForTest;

    @BeforeAll
    static void init() {
        groupRepository = new GroupRepositoryImpl();
        groupForTest = groupRepository.createGroup(new Group("TopicServletTest"));
    }

    @BeforeEach
    public void setUp() {
        SUT = new TopicServlet();
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    @DisplayName("get all topics")
    void doGet0() throws Exception {
        DataBase.initDataBase();
        //Arrange
        Group group = new Group("TopicServletTest");
        groupForTest = groupRepository.createGroup(group);
        TopicDto topic1 = new TopicDto("topic doGet1 1", groupForTest.getId());
        TopicDto topic2 = new TopicDto("topic doGet1 2", groupForTest.getId());
        topicService.create(topic1);
        topicService.create(topic2);
        Mockito.when(request.getPathInfo()).thenReturn("/");
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        //Act
        SUT.doGet(request, response);
        //Assert
        String expected = String
                .format("[{\"id\":%d,\"groupId\":%d,\"name\":\"topic doGet1 1\"},{\"id\":%d,\"groupId\":%d,\"name\":\"topic doGet1 2\"}]",
                        topic1.getId(), group.getId(), topic2.getId(), group.getId());
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    @DisplayName("get all topics by group")
    void doGet1() throws Exception {
        DataBase.initDataBase();
        //Arrange
        Group group = new Group("TopicServletTest");
        groupForTest = groupRepository.createGroup(group);
        TopicDto topic1 = new TopicDto("topic doGet1 1", groupForTest.getId());
        TopicDto topic2 = new TopicDto("topic doGet1 2", groupForTest.getId());
        topicService.create(topic1);
        topicService.create(topic2);
        Mockito.when(request.getPathInfo()).thenReturn("/group/" + groupForTest.getId());
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        //Act
        SUT.doGet(request, response);
        //Assert
        String expected = String
                .format("[{\"id\":%d,\"groupId\":%d,\"name\":\"topic doGet1 1\"},{\"id\":%d,\"groupId\":%d,\"name\":\"topic doGet1 2\"}]",
                        topic1.getId(), group.getId(), topic2.getId(), group.getId());
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    @DisplayName("get topic by id")
    void doGet2() throws IOException {
        //Arrange
        TopicDto topic = new TopicDto("topic doGet2", groupForTest.getId());
        topicService.create(topic);
        Mockito.when(request.getPathInfo()).thenReturn("/" + topic.getId());
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        //Act
        SUT.doGet(request, response);
        //Assert
        String expected = String.format("{\"id\":%d,\"groupId\":%d,\"name\":\"topic doGet2\"}", topic.getId(), groupForTest.getId());
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    @DisplayName("save new topic")
    void doPost() throws IOException {
        //Arrange
        Mockito.when(request.getPathInfo()).thenReturn("/");

        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        StringReader stringReader = new StringReader(String.format("{\"groupId\":%d,\"name\":\"topic doPost\"}", groupForTest.getId()));
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        Mockito.when(request.getReader()).thenReturn(bufferedReader);
        //Act
        SUT.doPost(request, response);
        //Assert
        String number = stringWriter.toString().chars()
                .filter(e -> Character.isDigit(e))
                .mapToObj(e -> String.valueOf((char) e))
                .collect(Collectors.joining());
        int newId = Integer.parseInt(number);
        Assertions.assertTrue(stringWriter.toString().contains("new topic save id = " + newId));
        TopicDto savedTopic = new TopicDto(newId, groupForTest.getId(), "topic doPost");
        Assertions.assertEquals(savedTopic, topicService.get(newId));
    }

    @Test
    @DisplayName("update topic")
    void doPut() throws IOException {
        //Arrange
        TopicDto topicDto = new TopicDto("topic doPut", groupForTest.getId());
        topicService.create(topicDto);

        Mockito.when(request.getPathInfo()).thenReturn("/");
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        StringReader stringReader = new StringReader(String.format("{\"id\":%d,\"groupId\":%d,\"name\":\"topic doPut update\"}",
                topicDto.getId(), groupForTest.getId()));
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        Mockito.when(request.getReader()).thenReturn(bufferedReader);
        //Act
        SUT.doPut(request, response);
        //Assert
        Assertions.assertTrue(stringWriter.toString().contains("topic update id = " + topicDto.getId()));
        TopicDto topicUpdate = new TopicDto(topicDto.getId(), groupForTest.getId(), "topic doPut update");
        Assertions.assertEquals(topicUpdate, topicService.get(topicUpdate.getId()));
    }

    @Test
    @DisplayName("delete topic by id")
    void doDelete() throws IOException {
        //Arrange
        TopicDto topic = new TopicDto("topic doDelete", groupForTest.getId());
        topicService.create(topic);
        Mockito.when(request.getPathInfo()).thenReturn("/" + topic.getId());
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        //Act
        SUT.doDelete(request, response);
        //Assert
        String expected = String.format("topic by id = %d removed", topic.getId());
        Assertions.assertEquals(expected, stringWriter.toString());
        Exception exception = Assertions.assertThrows(RuntimeException.class,
                () -> topicService.get(topic.getId()));
        Assertions.assertEquals("not row to map", exception.getMessage());
    }
}