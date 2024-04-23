package crud_app.entity;

import java.util.Objects;

/**
 * topic entity class
 */
public class Topic {
    /**
     * topic id in database
     */
    private int id;
    /**
     * topic name
     */
    private String name;

    /**
     * topic no args constructor
     */
    public Topic() {
    }

    /**
     * topic constructor
     *
     * @param id   topic id in database
     * @param name topic name
     */
    public Topic(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * topic constructor
     *
     * @param name topic name
     */
    public Topic(String name) {
        this(0, name);
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
        Topic topic = (Topic) o;
        return id == topic.id && Objects.equals(name, topic.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
