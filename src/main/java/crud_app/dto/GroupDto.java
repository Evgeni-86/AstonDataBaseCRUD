package crud_app.dto;

import crud_app.entity.Group;

import java.util.Objects;


/**
 * this is dto class for group entity
 */
public class GroupDto {
    /**
     * id group in database
     */
    private int id;
    /**
     * group name
     */
    private String name;

    /**
     * no args group dto constructor for use jackson library
     */
    public GroupDto() {
    }

    /**
     * group dto constructor
     *
     * @param name group name
     */
    public GroupDto(String name) {
        this.name = name;
    }

    /**
     * group dto constructor
     *
     * @param id   group id in database
     * @param name group name
     */
    public GroupDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * method for mapping class Group to dto
     *
     * @param group group for mapping
     * @return dto group
     */
    public static GroupDto toDTO(Group group) {
        return new GroupDto(group.getId(), group.getName());
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
        GroupDto groupDto = (GroupDto) o;
        return id == groupDto.id && Objects.equals(name, groupDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "GroupDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}