package crud_app.dto;

import crud_app.entity.Topic;
import crud_app.entity.TopicMessage;

import java.util.Objects;

public class TopicMessageDto {
    private int id;
    private int topicId;
    private String title;
    private String body;

    public TopicMessageDto() {
    }

    public TopicMessageDto(int topicId, String title, String body) {
        this(0, topicId, title, body);
    }

    public TopicMessageDto(int id, int topicId, String title, String body) {
        this.id = id;
        this.topicId = topicId;
        this.title = title;
        this.body = body;
    }

    public static TopicMessageDto toDTO(TopicMessage topicMessage) {
        return new TopicMessageDto(topicMessage.getId(), topicMessage.getTopic().getId(),
                topicMessage.getTitle(), topicMessage.getBody());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
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
        TopicMessageDto that = (TopicMessageDto) o;
        return id == that.id && topicId == that.topicId && Objects.equals(title, that.title) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topicId, title, body);
    }

    @Override
    public String toString() {
        return "TopicMessageDto{" +
                "id=" + id +
                ", topicId=" + topicId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
