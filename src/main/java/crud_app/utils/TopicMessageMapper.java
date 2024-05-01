package crud_app.utils;

import crud_app.entity.TopicMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * class to convert resultSet to topic message
 */
public class TopicMessageMapper {
    /**
     * method convert result set to topic message
     *
     * @param resultSet result set
     * @return topic message
     * @throws SQLException if result set read error
     */
    public static TopicMessage mapTopicMessage(ResultSet resultSet) throws SQLException {
        return new TopicMessage(
                resultSet.getInt("message_id"),
                resultSet.getString("message_title"),
                resultSet.getString("message_body"));
    }
}
