package crud_app.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import crud_app.dto.TopicDto;
import crud_app.service.TopicService;
import crud_app.service.impl.TopicServiceImpl;
import crud_app.utils.JsonParser;
import crud_app.utils.RequestValidation;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

/**
 * topic servlet
 */
public class TopicServlet extends HttpServlet {
    /**
     * topic service interface
     */
    private TopicService topicService = new TopicServiceImpl();
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
            TopicDto topic = topicService.get(validationResult.get("/id"));
            response.getWriter().write(objectMapper.writeValueAsString(topic));
        } else if (validationResult.containsKey("/")) {
            response.getWriter().write(objectMapper.writeValueAsString(topicService.getAll()));
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
            TopicDto topic = objectMapper.readValue(JsonParser.parseJson(request), TopicDto.class);
            TopicDto result = topicService.create(topic);
            response.getWriter().write(String.format("new topic save id = %d", result.getId()));
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
            TopicDto topic = objectMapper.readValue(JsonParser.parseJson(request), TopicDto.class);
            topicService.update(topic);
            response.getWriter().write(String.format("topic update id = %d", topic.getId()));
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
            int id = validationResult.get("/id");
            topicService.remove(id);
            response.getWriter().write(String.format("topic by id = %d removed", id));
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}