package crud_app.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import crud_app.dto.TopicDto;
import crud_app.service.TopicService;
import crud_app.service.impl.TopicServiceImpl;
import crud_app.utils.DataBase;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class TopicServlet extends HttpServlet {

    private TopicService topicService = new TopicServiceImpl();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("start init servlet complete");
        super.init(config);
        try {
            ServletContext servletContext = this.getServletContext();
            InputStream inputStreamProperty = servletContext.getResourceAsStream("/WEB-INF/classes/app.properties");
            Properties properties = new Properties();
            properties.load(inputStreamProperty);
            DataBase.setProperties(properties);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read properties file");
        }
        DataBase.initDataBase();
        TopicDto topic1 = new TopicDto("Topic 1");
        TopicDto topic2 = new TopicDto("Topic 2");
        topicService.save(topic1);
        topicService.save(topic2);
        System.out.println("stop init servlet complete");
    }

    // GET/topics/
    // GET/topics/id
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

    // POST/topics/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            TopicDto topic = objectMapper.readValue(parseJson(request), TopicDto.class);
            TopicDto result = topicService.save(topic);
            response.getWriter().write(String.format("new topic save id = %d", result.getId()));
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    // PUT/topics/id
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

        String topicId = splits[1];
        TopicDto topic = objectMapper.readValue(parseJson(request), TopicDto.class);
        if (Integer.parseInt(topicId) != topic.getId()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        topicService.update(topic);
        response.getWriter().write(String.format("topic update id = %d", topic.getId()));
    }

    // DELETE/topics/id
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

        int topicId = Integer.parseInt(splits[1]);
        topicService.remove(topicId);
        response.getWriter().write(String.format("topic by id = %d removed", topicId));
    }

    private String parseJson(HttpServletRequest request) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }
}