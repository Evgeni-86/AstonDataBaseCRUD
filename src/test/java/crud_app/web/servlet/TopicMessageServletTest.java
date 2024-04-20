package crud_app.web.servlet;

import crud_app.dto.TopicDto;
import crud_app.dto.TopicMessageDto;
import crud_app.AbstractTest;
import crud_app.service.TopicMessageService;
import crud_app.service.TopicService;
import crud_app.service.impl.TopicMessageServiceImpl;
import crud_app.service.impl.TopicServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

class TopicMessageServletTest extends AbstractTest {

    private TopicMessageServlet SUT;
    private HttpServletRequest request;
    private HttpServletResponse response;

    private TopicMessageService topicMessageService = new TopicMessageServiceImpl();
    private TopicService topicService = new TopicServiceImpl();

    @BeforeEach
    public void setUp() {
        SUT = new TopicMessageServlet();
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    @DisplayName("get all message by topic id")
    void doGet1() throws IOException {
        //Arrange
        TopicDto topicDto = new TopicDto("message doGet1");
        topicService.create(topicDto);
        TopicMessageDto messageDto1 = new TopicMessageDto(topicDto.getId(), "New Title 1", "New 1");
        TopicMessageDto messageDto2 = new TopicMessageDto(topicDto.getId(), "New Title 2", "New 2");
        topicMessageService.create(messageDto1);
        topicMessageService.create(messageDto2);

        Mockito.when(request.getPathInfo()).thenReturn("/topic/" + topicDto.getId());
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        //Act
        SUT.doGet(request, response);
        //Assert
        String expected = String
                .format("[{\"id\":%d,\"topicId\":%d,\"title\":\"New Title 1\",\"body\":\"New 1\"}," +
                                "{\"id\":%d,\"topicId\":%d,\"title\":\"New Title 2\",\"body\":\"New 2\"}]",
                        messageDto1.getId(), topicDto.getId(), messageDto2.getId(), topicDto.getId());
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    @DisplayName("get message by id")
    void doGet2() throws IOException {
        //Arrange
        TopicDto topicDto = new TopicDto("message doGet2");
        topicService.create(topicDto);
        TopicMessageDto messageDto = new TopicMessageDto(topicDto.getId(), "New Title 1", "New 1");
        topicMessageService.create(messageDto);

        Mockito.when(request.getPathInfo()).thenReturn("/" + messageDto.getId());
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        //Act
        SUT.doGet(request, response);
        //Assert
        String expected = String
                .format("{\"id\":%d,\"topicId\":%d,\"title\":\"New Title 1\",\"body\":\"New 1\"}",
                        messageDto.getId(), topicDto.getId());
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    @DisplayName("save new message")
    void doPost() throws IOException {
        //Arrange
        TopicDto topicDto = new TopicDto("message doPost");
        topicService.create(topicDto);

        Mockito.when(request.getPathInfo()).thenReturn("/");
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        StringReader stringReader = new StringReader(
                String.format("{\"id\":0,\"topicId\":%d,\"title\":\"New Title doPost\",\"body\":\"New doPost\"}",
                        topicDto.getId()));
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
        Assertions.assertTrue(stringWriter.toString().contains("new message save id = " + newId));
        TopicMessageDto savedMessage =
                new TopicMessageDto(newId, topicDto.getId(), "New Title doPost", "New doPost");
        Assertions.assertEquals(savedMessage, topicMessageService.getAllMessage(topicDto.getId()).get(0));
    }

    @Test
    @DisplayName("update message")
    void doPut() throws IOException {
        //Arrange
        TopicDto topicDto = new TopicDto("message doPut");
        topicService.create(topicDto);
        TopicMessageDto messageDto = new TopicMessageDto(topicDto.getId(), "New Title doPut", "doPut");
        topicMessageService.create(messageDto);

        Mockito.when(request.getPathInfo()).thenReturn("/");
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        StringReader stringReader = new StringReader(
                String.format("{\"id\":%d,\"topicId\":%d,\"title\":\"New Title doPut update\",\"body\":\"doPut update\"}",
                        messageDto.getId(), topicDto.getId()));
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        Mockito.when(request.getReader()).thenReturn(bufferedReader);
        //Act
        SUT.doPut(request, response);
        //Assert
        Assertions.assertTrue(stringWriter.toString().contains("message update id = " + messageDto.getId()));
        TopicMessageDto messageDtoUpdate =
                new TopicMessageDto(messageDto.getId(), topicDto.getId(), "New Title doPut update", "doPut update");
        Assertions.assertEquals(messageDtoUpdate, topicMessageService.get(messageDtoUpdate.getId()));
    }

    @Test
    @DisplayName("delete message by id")
    void doDelete() throws IOException {
        //Arrange
        TopicDto topicDto = new TopicDto("message doDelete");
        topicService.create(topicDto);
        TopicMessageDto messageDto = new TopicMessageDto(topicDto.getId(), "New Title doDelete", "doDelete");
        topicMessageService.create(messageDto);

        Mockito.when(request.getPathInfo()).thenReturn("/" + messageDto.getId());
        StringWriter stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        //Act
        SUT.doDelete(request, response);
        //Assert
        String expected = String.format("message by id = %d removed", messageDto.getId());
        Assertions.assertEquals(expected, stringWriter.toString());
        Exception exception = Assertions.assertThrows(RuntimeException.class,
                () -> topicMessageService.get(messageDto.getId()));
        Assertions.assertEquals("not row to map", exception.getMessage());
    }
}