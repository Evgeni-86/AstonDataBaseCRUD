package crud_app.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import crud_app.dto.GroupDto;
import crud_app.service.GroupService;
import crud_app.service.impl.GroupServiceImpl;
import crud_app.utils.JsonParser;
import crud_app.utils.RequestValidation;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;


/**
 * group servlet
 */
public class GroupServlet extends HttpServlet {
    /**
     * group service interface
     */
    private GroupService groupService = new GroupServiceImpl();
    /**
     * object mapper
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * method to process the rest get group request
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
        Integer id;
        if ((id = validationResult.get("/id")) != null) {
            GroupDto groupDto = groupService.get(id);
            response.getWriter().write(objectMapper.writeValueAsString(groupDto));
        } else if (validationResult.containsKey("/")) {
            response.getWriter().write(objectMapper.writeValueAsString(groupService.getAll()));
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * method to process the rest post group request
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
            GroupDto groupDto = objectMapper.readValue(JsonParser.parseJson(request), GroupDto.class);
            GroupDto result = groupService.create(groupDto);
            response.getWriter().write(String.format("new group save id = %d", result.getId()));
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * method to process the rest put group request
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
            GroupDto groupDto = objectMapper.readValue(JsonParser.parseJson(request), GroupDto.class);
            groupService.update(groupDto);
            response.getWriter().write(String.format("group update id = %d", groupDto.getId()));
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * method to process the rest delete group request
     *
     * @param request  request
     * @param response response
     * @throws IOException if error
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");

        Map<String, Integer> validationResult = RequestValidation.validate(request);
        Integer id;
        if ((id = validationResult.get("/id")) != null) {
            groupService.remove(id);
            response.getWriter().write(String.format("group by id = %d removed", id));
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
