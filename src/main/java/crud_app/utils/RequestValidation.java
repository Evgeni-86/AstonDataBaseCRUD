package crud_app.utils;

import jakarta.servlet.http.HttpServletRequest;

public class RequestValidation {

    public static int validate(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return 0;
        }

        String[] splits = pathInfo.split("/");
        if (splits.length == 2 || splits[1].matches("-?\\d+")) {
            return Integer.parseInt(splits[1]);
        }

        return -1;
    }
}
