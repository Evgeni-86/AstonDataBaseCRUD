package crud_app.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.Map;

/**
 * this class for validation check request path
 */
public class RequestValidation {
    public static Map<String, Integer> validate(HttpServletRequest request) {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/"))
            return Collections.singletonMap("/", null);

        String[] splits = pathInfo.split("/");
        if (splits.length == 2 || splits[1].matches("-?\\d+"))
            return Collections.singletonMap("/id", Integer.parseInt(splits[1]));

        if (splits.length == 3 && splits[2].matches("-?\\d+"))
            return Collections.singletonMap("/topic/id", Integer.parseInt(splits[2]));

        return Collections.singletonMap(null, null);
    }
}
