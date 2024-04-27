package crud_app.entity;

import java.util.List;
import java.util.Objects;


/**
 * group entity class
 */
public class Group {
    /**
     * group id in database
     */
    private int id;
    /**
     * group name
     */
    private String name;
    /**
     * list topics
     */
    private List<Topic> topics;

    /**
     * group no args constructor
     */
    public Group() {
    }

    /**
     * group constructor
     *
     * @param id   group id in database
     * @param name group name
     */
    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * group constructor
     *
     * @param name group name
     */
    public Group(String name) {
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

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id && Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
