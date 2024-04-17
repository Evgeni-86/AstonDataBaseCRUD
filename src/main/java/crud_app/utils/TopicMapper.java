package crud_app.utils;

import crud_app.entity.Topic;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TopicMapper {
    public static Topic mapTopic(ResultSet resultSet) throws SQLException {
        return new Topic(
                resultSet.getInt("id"),
                resultSet.getString("name"));
    }
}