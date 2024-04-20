package crud_app.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import crud_app.dto.TopicDto;
import crud_app.service.TopicService;
import crud_app.service.impl.TopicServiceImpl;
import crud_app.utils.JsonParser;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.getWriter().write(objectMapper.writeValueAsString(topicService.getAll()));
            return;
        }

        String[] splits = pathInfo.split("/");
        if (splits.length != 2 || !splits[1].matches("-?\\d+")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String topicId = splits[1];
        TopicDto topic = topicService.get(Integer.parseInt(topicId));
        response.getWriter().write(objectMapper.writeValueAsString(topic));
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
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
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
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
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
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = pathInfo.split("/");

        if (splits.length == 2 || splits[1].matches("-?\\d+")) {
            int topicId = Integer.parseInt(splits[1]);
            topicService.remove(topicId);
            response.getWriter().write(String.format("topic by id = %d removed", topicId));
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}