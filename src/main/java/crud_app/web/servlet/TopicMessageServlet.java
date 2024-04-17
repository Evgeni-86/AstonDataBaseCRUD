package crud_app.web.servlet;

import crud_app.service.TopicMessageService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TopicMessageServlet extends HttpServlet {

    private TopicMessageService topicMessageService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
    }
}
