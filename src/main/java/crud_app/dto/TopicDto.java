package crud_app.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import crud_app.entity.Topic;

import java.util.Objects;

public class TopicDto {
    private int id;
    private String name;

    public TopicDto() {
    }

    public TopicDto(String name) {
        this.name = name;
    }

    public TopicDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
