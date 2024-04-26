package crud_app.utils;

import crud_app.web.servlet.TopicServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RequestValidationTest {

    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        request = Mockito.mock(HttpServletRequest.class);
    }

    @Test
    void validateAll() {
        Mockito.when(request.getPathInfo()).thenReturn("/");
        Map<String, Integer> actual = RequestValidation.validate(request);
        Assertions.assertEquals(Collections.singletonMap("/", null), actual);
    }
    @Test
    void validateId() {
        Mockito.when(request.getPathInfo()).thenReturn("/" + 1);
        Map<String, Integer> actual = RequestValidation.validate(request);
        Assertions.assertEquals(Collections.singletonMap("/id", 1), actual);
    }
    @Test
    void validateAllGroup() {
        Mockito.when(request.getPathInfo()).thenReturn("/group/" + 1);
        Map<String, Integer> actual = RequestValidation.validate(request);
        Assertions.assertEquals(Collections.singletonMap("/group/id", 1), actual);
    }

    @Test
    void validateAllTopic() {
        Mockito.when(request.getPathInfo()).thenReturn("/topic/" + 1);
        Map<String, Integer> actual = RequestValidation.validate(request);
        Assertions.assertEquals(Collections.singletonMap("/topic/id", 1), actual);
    }
}