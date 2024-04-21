package crud_app.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import crud_app.dto.TopicMessageDto;
import crud_app.service.TopicMessageService;
import crud_app.service.impl.TopicMessageServiceImpl;
import crud_app.utils.JsonParser;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

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

        } else if (splits.length == 2 && splits[1].matches("-?\\d+")) {
            int messageId = Integer.parseInt(splits[1]);
            TopicMessageDto messageDto = topicMessageService.get(messageId);
            response.getWriter().write(objectMapper.writeValueAsString(messageDto));

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
        String pathInfo = request.getPathInfo();
        response.setCharacterEncoding("UTF-8");

        if (pathInfo == null || pathInfo.equals("/")) {
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
        String pathInfo = request.getPathInfo();
        response.setCharacterEncoding("UTF-8");

        if (pathInfo == null || pathInfo.equals("/")) {
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
        String pathInfo = request.getPathInfo();
        response.setCharacterEncoding("UTF-8");

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
