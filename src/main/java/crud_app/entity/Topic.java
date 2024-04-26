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
     * this topic group
     */
    private Group group;

    /**
     * topic no args constructor
     */
    public Topic() {
    }

    /**
     * topic constructor
     *
     * @param id    topic id in database
     * @param name  topic name
     * @param group topic group
     */
    public Topic(int id, String name, Group group) {
        this.id = id;
        this.name = name;
        this.group = group;
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
     * @param group topic group
     */
    public Topic(String name, Group group) {
        this.name = name;
        this.group = group;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return id == topic.id && Objects.equals(name, topic.name) && Objects.equals(group.getId(), topic.group.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, group.getId());
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", group id=" + group.getId() +
                '}';
    }
}
