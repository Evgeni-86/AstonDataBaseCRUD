package crud_app.utils;

import crud_app.entity.Topic;
import crud_app.entity.TopicMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TopicMessageMapper {
    public static TopicMessage mapTopicMessage(ResultSet resultSet) throws SQLException {
        Topic topic = new Topic(resultSet.getInt("topic_id"), resultSet.getString("name"));

        return new TopicMessage(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("body"),
                topic);
    }
}
