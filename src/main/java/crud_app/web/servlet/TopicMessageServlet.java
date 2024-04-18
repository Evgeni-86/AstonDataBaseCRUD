package crud_app.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import crud_app.dto.TopicDto;
import crud_app.dto.TopicMessageDto;
import crud_app.service.TopicMessageService;
import crud_app.service.impl.TopicMessageServiceImpl;
import crud_app.utils.JsonParser;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class TopicMessageServlet extends HttpServlet {

    private TopicMessageService topicMessageService = new TopicMessageServiceImpl();
    private ObjectMapper objectMapper = new ObjectMapper();


    // GET /messages/topic/id
    // GET /messages/id
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");

        if (splits.length == 3 && splits[2].matches("-?\\d+")) {
            int topicId = Integer.parseInt(splits[2]);
            List<TopicMessageDto> allMessage = topicMessageService.getAllMessage(topicId);
            response.getWriter().write(objectMapper.writeValueAsString(allMessage));

        } else if (splits.length == 2 && splits[1].matches("-?\\d+")){
            int messageId = Integer.parseInt(splits[1]);
            TopicMessageDto messageDto = topicMessageService.get(messageId);
            response.getWriter().write(objectMapper.writeValueAsString(messageDto));

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    // POST/messages/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            TopicMessageDto messageDto = objectMapper.readValue(JsonParser.parseJson(request), TopicMessageDto.class);
            TopicMessageDto result = topicMessageService.create(messageDto);
            response.getWriter().write(String.format("new topic save id = %d", result.getId()));
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    // PUT/messages/
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            TopicMessageDto messageDto = objectMapper.readValue(JsonParser.parseJson(request), TopicMessageDto.class);
            topicMessageService.update(messageDto);
            response.getWriter().write(String.format("message update id = %d", messageDto.getId()));
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    // DELETE/messages/id
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");
        if (splits.length != 2 || !splits[1].matches("-?\\d+")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int messageId = Integer.parseInt(splits[1]);
        topicMessageService.remove(messageId);
        response.getWriter().write(String.format("message by id = %d removed", messageId));
    }
}
