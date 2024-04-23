package crud_app.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * parser request body to Json
 */
public class JsonParser {
    /**
     * method parses the request body into Json
     *
     * @param request request for parse
     * @return Json string
     * @throws IOException if error read request body
     */
    public static String parseJson(HttpServletRequest request) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }
}
