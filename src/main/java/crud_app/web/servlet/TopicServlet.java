package crud_app.web.servlet;

import crud_app.service.TopicService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class TopicServlet extends HttpServlet {

    private TopicService topicService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
