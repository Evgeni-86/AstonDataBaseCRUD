package crud_app.utils;

import crud_app.entity.Topic;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * class to convert result set to topic
 */
public class TopicMapper {
    /**
     * method convert result set to topic
     *
     * @param resultSet result set
     * @return topic
     * @throws SQLException if result set read error
     */
    public static Topic mapTopic(ResultSet resultSet) throws SQLException {
        return new Topic(
                resultSet.getInt("id"),
                resultSet.getString("name"));
    }
}