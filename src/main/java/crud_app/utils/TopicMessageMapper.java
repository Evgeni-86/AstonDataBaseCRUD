package crud_app.utils;

import crud_app.entity.TopicMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TopicMessageMapper {
    private int id;
    private String title;
    private String body;

    public static TopicMessage mapTopicMessage(ResultSet resultSet) throws SQLException {
        return new TopicMessage(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("body"));
    }
}
