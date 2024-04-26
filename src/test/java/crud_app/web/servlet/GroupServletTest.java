package crud_app.web.servlet;

import crud_app.AbstractTest;
import crud_app.dto.GroupDto;
import crud_app.dto.TopicDto;
import crud_app.dto.TopicMessageDto;
import crud_app.entity.Group;
import crud_app.repository.GroupRepository;
import crud_app.repository.impl.GroupRepositoryImpl;
import crud_app.service.GroupService;
import crud_app.service.TopicMessageService;
import crud_app.service.TopicService;
import crud_app.service.impl.GroupServiceImpl;
import crud_app.service.impl.TopicMessageServiceImpl;
import crud_app.service.impl.TopicServiceImpl;
import crud_app.utils.DataBase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GroupServletTest extends AbstractTest {

    private GroupServlet SUT;
    private HttpServletRequest request;
    private HttpServletResponse response;

    private GroupService groupService = new GroupServiceImpl();
    private TopicService topicService = new TopicServiceImpl();
    private TopicMessageService topicMessageService = new TopicMessageServiceImpl();

    private static GroupRepository groupRepository;
    private static Group groupForTest;

    @BeforeAll
    static void init() {
        groupRepository = new GroupRepositoryImpl();
        groupForTest = groupRepository.createGroup(new Group("GroupServletTest"));
    }

    @BeforeEach
    public void setUp() {
        SUT = new GroupServlet();
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    @DisplayName("get all groups")
    public void doGet1() throws Exception {
        DataBase.initDataBase();
        //Arrange
        GroupDto group1 = new GroupDto("group doGet1 1");
        GroupDto group2 = new GroupDto("group doGet1 2");
        groupService.create(group1);
        groupService.create(group2);
        Mockito.when(request.getPathInfo()).thenReturn("/");
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        //Act
        SUT.doGet(request, response);
        //Assert
        String expected = String
                .format("[{\"id\":%d,\"name\":\"group doGet1 1\"},{\"id\":%d,\"name\":\"group doGet1 2\"}]",
                        group1.getId(), group2.getId());
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    @DisplayName("get group by id")
    void doGet2() throws IOException {
        //Arrange
        GroupDto group = new GroupDto("group doGet2");
        groupService.create(group);
        Mockito.when(request.getPathInfo()).thenReturn("/" + group.getId());
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        //Act
        SUT.doGet(request, response);
        //Assert
        String expected = String.format("{\"id\":%d,\"name\":\"group doGet2\"}", group.getId());
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    @DisplayName("save new group")
    void doPost() throws IOException {
        //Arrange
        Mockito.when(request.getPathInfo()).thenReturn("/");

        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        StringReader stringReader = new StringReader("{\"name\":\"group doPost\"}");
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        Mockito.when(request.getReader()).thenReturn(bufferedReader);
        //Act
        SUT.doPost(request, response);
        //Assert
        String number = stringWriter.toString().chars()
                .filter(Character::isDigit)
                .mapToObj(e -> String.valueOf((char) e))
                .collect(Collectors.joining());
        int newId = Integer.parseInt(number);
        Assertions.assertTrue(stringWriter.toString().contains("new group save id = " + newId));
        GroupDto savedGroup = new GroupDto(newId, "group doPost");
        Assertions.assertEquals(savedGroup, groupService.get(newId));
    }

    @Test
    @DisplayName("update group")
    void doPut() throws IOException {
        //Arrange
        GroupDto groupDto = new GroupDto("group doPut");
        groupService.create(groupDto);

        Mockito.when(request.getPathInfo()).thenReturn("/");
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        StringReader stringReader = new StringReader(String.format("{\"id\":%d,\"name\":\"group doPut update\"}", groupDto.getId()));
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        Mockito.when(request.getReader()).thenReturn(bufferedReader);
        //Act
        SUT.doPut(request, response);
        //Assert
        Assertions.assertTrue(stringWriter.toString().contains("group update id = " + groupDto.getId()));
        GroupDto groupUpdate = new GroupDto(groupDto.getId(), "group doPut update");
        Assertions.assertEquals(groupUpdate, groupService.get(groupUpdate.getId()));
    }

    @Test
    @DisplayName("delete group by id")
    void doDelete() throws IOException {
        //Arrange
        GroupDto groupDto = new GroupDto("group doDelete");
        groupService.create(groupDto);
        TopicDto topicDto = new TopicDto("new topic", groupDto.getId());
        topicService.create(topicDto);
        TopicMessageDto topicMessageDto = new TopicMessageDto(topicDto.getId(), "title", "body");
        topicMessageService.create(topicMessageDto);
        Mockito.when(request.getPathInfo()).thenReturn("/" + groupDto.getId());
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        //Act
        SUT.doDelete(request, response);
        //Assert
        String expected = String.format("group by id = %d removed", groupDto.getId());
        Assertions.assertEquals(expected, stringWriter.toString());
        Exception exception = Assertions.assertThrows(RuntimeException.class,
                () -> groupService.get(groupDto.getId()));
        Assertions.assertEquals("not row to map", exception.getMessage());
    }
}