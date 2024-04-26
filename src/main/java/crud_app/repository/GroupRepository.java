package crud_app.repository;

import crud_app.entity.Group;

import java.util.List;


/**
 * this is group repository interface
 */
public interface GroupRepository {
    /**
     * method save new group in database
     *
     * @param group group for save
     * @return saved group
     */
    Group createGroup(Group group);

    /**
     * method update group in database
     *
     * @param group group for update
     * @return updated group
     */
    Group updateGroup(Group group);

    /**
     * method read group from database by id
     *
     * @param groupId group id in database
     * @return group from database
     */
    Group getGroup(int groupId);

    /**
     * method removed group from database by id
     *
     * @param groupId group id in database
     * @return removed group
     */
    boolean removeGroup(int groupId);

    /**
     * method return all groups from database
     *
     * @return group list
     */
    List<Group> getAllGroup();
}
