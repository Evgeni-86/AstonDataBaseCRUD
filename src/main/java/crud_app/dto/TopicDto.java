package crud_app.dto;

import crud_app.entity.Topic;

import java.util.Objects;

/**
 * this is dto class for topic entity
 */
public class TopicDto {
    /**
     * id topic in database
     */
    private int id;
    /**
     * topic name
     */
    private String name;

    /**
     * no args topic dto constructor for use jackson library
     */
    public TopicDto() {
    }

    /**
     * topic dto constructor
     *
     * @param name topic name
     */
    public TopicDto(String name) {
        this.name = name;
    }

    /**
     * topic dto constructor
     *
     * @param id   topic id in database
     * @param name topic name
     */
    public TopicDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * method for mapping class Topic to dto
     *
     * @param topic topic for mapping
     * @return dto topic
     */
    public static TopicDto toDTO(Topic topic) {
        return new TopicDto(topic.getId(), topic.getName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicDto topicDto = (TopicDto) o;
        return id == topicDto.id && Objects.equals(name, topicDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "TopicDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
