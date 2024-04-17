package crud_app.entity;

import java.util.Objects;

public class TopicMessage {
    private int id;
    private String title;
    private String body;

    public TopicMessage(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public TopicMessage(String title, String body) {
        this(0, title, body);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        TopicMessage that = (TopicMessage) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, body);
    }

    @Override
    public String toString() {
        return "TopicMessage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}