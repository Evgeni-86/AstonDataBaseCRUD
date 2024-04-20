package crud_app.entity;

import java.util.Objects;

/**
 * message entity class
 */
public class TopicMessage {
    /**
     * message id in database
     */
    private int id;
    /**
     * message title
     */
    private String title;
    /**
     * message text
     */
    private String body;
    /**
     * this message topic
     */
    private Topic topic;

    /**
     * message constructor
     *
     * @param id    message id in database
     * @param title message title
     * @param body  message text
     * @param topic this message topic
     */
    public TopicMessage(int id, String title, String body, Topic topic) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.topic = topic;
    }

    /**
     * message constructor
     *
     * @param id    message id in database
     * @param title message title
     * @param body  message text
     */
    public TopicMessage(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    /**
     * message constructor
     *
     * @param title message title
     * @param body  message text
     * @param topic this message topic
     */
    public TopicMessage(String title, String body, Topic topic) {
        this(0, title, body, topic);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicMessage message = (TopicMessage) o;
        return id == message.id && Objects.equals(title, message.title)
                && Objects.equals(body, message.body) && Objects.equals(topic.getId(), message.topic.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, body, topic.getId());
    }

    @Override
    public String toString() {
        return "TopicMessage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", topic id=" + topic.getId() +
                '}';
    }
}