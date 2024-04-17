package crud_app.utils;

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

    private static void loadProperty() {
        try (InputStream inputStreamProperty = new FileInputStream("src/main/resources/app.properties")) {
            properties = new Properties();
            properties.load(inputStreamProperty);
        } catch (IOException e) {
            throw new RuntimeException("failed to read properties file");
        }
    }

    public static Connection getConnection() {
        if (properties == null) loadProperty();
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

//    public static void initDataBase() {
//        Connection connectionDB = DataBase.getConnection();
//        try (Statement statement = connectionDB.createStatement()) {
//            statement.addBatch("DROP TABLE IF EXISTS messages");
//            statement.addBatch("DROP TABLE IF EXISTS topics");
//            statement.addBatch("""
//                    CREATE TABLE topics (
//                    id   SERIAL PRIMARY KEY,
//                    name VARCHAR(20),
//                    CONSTRAINT topic_name_unique UNIQUE (name))
//                    """);
//            statement.addBatch("""
//                    CREATE TABLE messages (
//                    id SERIAL PRIMARY KEY,
//                    topic_id INTEGER      NOT NULL,
//                    title    VARCHAR(50) NOT NULL,
//                    body     TEXT         NOT NULL,
//                    CONSTRAINT topic_messages_unique UNIQUE (id, topic_id),
//                    FOREIGN KEY (topic_id) REFERENCES topics (id) ON DELETE CASCADE)
//                    """);
//            statement.addBatch("CREATE INDEX messages_idx ON messages (topic_id)");
//            statement.executeBatch();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
