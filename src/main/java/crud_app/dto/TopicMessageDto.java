package crud_app.dto;

import crud_app.entity.TopicMessage;

import java.util.Objects;

/**
 * this is dto class for message entity
 */
public class TopicMessageDto {
    /**
     * id message in database
     */
    private int id;
    /**
     * this message topic id
     */
    private int topicId;
    /**
     * message title
     */
    private String title;
    /**
     * message text
     */
    private String body;

    /**
     * no args message dto constructor for use jackson library
     */
    public TopicMessageDto() {
    }

    /**
     * message dto constructor
     *
     * @param topicId this message topic id
     * @param title   message title
     * @param body    message text
     */
    public TopicMessageDto(int topicId, String title, String body) {
        this(0, topicId, title, body);
    }

    /**
     * message dto constructor
     *
     * @param id      message id in database
     * @param topicId this message topic id
     * @param title   message title
     * @param body    message text
     */
    public TopicMessageDto(int id, int topicId, String title, String body) {
        this.id = id;
        this.topicId = topicId;
        this.title = title;
        this.body = body;
    }

    /**
     * method for mapping class TopicMessage to dto
     *
     * @param topicMessage message for mapping
     * @return dto message
     */
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
