package crud_app.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import crud_app.dto.TopicMessageDto;
import crud_app.service.TopicMessageService;
import crud_app.service.impl.TopicMessageServiceImpl;
import crud_app.utils.JsonParser;
import crud_app.utils.RequestValidation;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * topic message servlet
 */
public class TopicMessageServlet extends HttpServlet {
    /**
     * topic message service interface
     */
    private TopicMessageService topicMessageService = new TopicMessageServiceImpl();
    /**
     * object mapper
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * method to process the rest get request
     *
     * @param request  request
     * @param response response
     * @throws IOException if error
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Integer> validationResult = RequestValidation.validate(request);
        if (validationResult.containsKey("/id")) {
            int messageId = validationResult.get("/id");
            TopicMessageDto messageDto = topicMessageService.get(messageId);
            response.getWriter().write(objectMapper.writeValueAsString(messageDto));
        } else if (validationResult.containsKey("/topic/id")) {
            int topicId = validationResult.get("/topic/id");
            List<TopicMessageDto> allMessage = topicMessageService.getAllMessage(topicId);
            response.getWriter().write(objectMapper.writeValueAsString(allMessage));
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * method to process the rest post request
     *
     * @param request  request
     * @param response response
     * @throws IOException if error
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");

        Map<String, Integer> validationResult = RequestValidation.validate(request);
        if (validationResult.containsKey("/")) {
            TopicMessageDto messageDto = objectMapper.readValue(JsonParser.parseJson(request), TopicMessageDto.class);
            TopicMessageDto result = topicMessageService.create(messageDto);
            response.getWriter().write(String.format("new message save id = %d", result.getId()));
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * method to process the rest put request
     *
     * @param request  request
     * @param response response
     * @throws IOException if error
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");

        Map<String, Integer> validationResult = RequestValidation.validate(request);
        if (validationResult.containsKey("/")) {
            TopicMessageDto messageDto = objectMapper.readValue(JsonParser.parseJson(request), TopicMessageDto.class);
            topicMessageService.update(messageDto);
            response.getWriter().write(String.format("message update id = %d", messageDto.getId()));
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * method to process the rest delete request
     *
     * @param request  request
     * @param response response
     * @throws IOException if error
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");

        Map<String, Integer> validationResult = RequestValidation.validate(request);
        if (validationResult.containsKey("/id")) {
            int messageId = validationResult.get("/id");
            topicMessageService.remove(messageId);
            response.getWriter().write(String.format("message by id = %d removed", messageId));
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
