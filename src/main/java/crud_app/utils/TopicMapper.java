package crud_app.utils;

import crud_app.entity.Topic;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * class to convert resultSet to topic
 */
public class TopicMapper {
    /**
     * method convert result set to topic
     *
     * @param resultSet resultSet
     * @return topic
     * @throws SQLException if resultSet read error
     */
    public static Topic mapTopic(ResultSet resultSet) throws SQLException {
        return new Topic(
                resultSet.getInt("topic_id"),
                resultSet.getString("topic_name"));
    }
}