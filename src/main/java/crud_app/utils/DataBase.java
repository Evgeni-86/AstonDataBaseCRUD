package crud_app.utils;

import jakarta.servlet.ServletContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DataBase {

    private static Properties properties;
    private static Connection connection;

    public static void setProperties(Properties properties) {
        DataBase.properties = properties;
    }

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

    public static void initDataBase() {
        Connection connectionDB = DataBase.getConnection();
        try (Statement statement = connectionDB.createStatement()) {
            statement.addBatch("DROP TABLE IF EXISTS topic_messages");
            statement.addBatch("DROP TABLE IF EXISTS topics");
            statement.addBatch("""
                    CREATE TABLE topics (
                    id   SERIAL PRIMARY KEY,
                    name VARCHAR(50),
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
