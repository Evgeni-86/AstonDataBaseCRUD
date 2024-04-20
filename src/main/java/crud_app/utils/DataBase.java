package crud_app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * this class create connection and init database
 */
public class DataBase {

    /**
     * properties for connection to database
     */
    private static Properties properties;
    /**
     * connection to database
     */
    private static Connection connection;

    /**
     * constructor for set properties
     *
     * @param properties database properties
     */
    public static void setProperties(Properties properties) {
        DataBase.properties = properties;
    }

    /**
     * method create and return connection for database
     *
     * @return database connection
     */
    public static Connection getConnection() {
        if (connection != null) return connection;
        try {
            Class.forName(properties.getProperty("database.driver"));
            connection = DriverManager.getConnection(
                    properties.getProperty("database.url"),
                    properties.getProperty("database.username"),
                    properties.getProperty("database.password")
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("failed to create connection");
        }
        return connection;
    }

    /**
     * method initialisation database
     */
    public static void initDataBase() {
        Connection connectionDB = DataBase.getConnection();
        try (Statement statement = connectionDB.createStatement()) {
            statement.addBatch("DROP TABLE IF EXISTS topic_messages");
            statement.addBatch("DROP TABLE IF EXISTS topics");
            statement.addBatch("""
                    CREATE TABLE topics (
                    id   SERIAL PRIMARY KEY,
                    name VARCHAR(50) NOT NULL,
                    CONSTRAINT topic_name_unique UNIQUE (name))
                    """);
            statement.addBatch("""
                    CREATE TABLE topic_messages (
                    id SERIAL PRIMARY KEY,
                    topic_id INTEGER      NOT NULL,
                    title    VARCHAR(50) NOT NULL,
                    body     TEXT         NOT NULL,
                    CONSTRAINT message_unique UNIQUE (id, topic_id),
                    FOREIGN KEY (topic_id) REFERENCES topics (id) ON DELETE CASCADE)
                    """);
            statement.addBatch("CREATE INDEX messages_idx ON topic_messages (topic_id)");
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
