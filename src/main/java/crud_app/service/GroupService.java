package crud_app.service;

import crud_app.dto.GroupDto;

import java.util.List;


/**
 * this is group service interface
 */
public interface GroupService {
    /**
     * method group topic
     *
     * @param group group dto
     * @return saved group dto
     */
    GroupDto create(GroupDto group);

    /**
     * method update group
     *
     * @param group group dto
     * @return updated group dto
     */
    GroupDto update(GroupDto group);

    /**
     * method get group dto by id
     *
     * @param groupId group id in database
     * @return group dto
     */
    GroupDto get(int groupId);

    /**
     * method delete group by id
     *
     * @param groupId group id in database
     * @return result (true - removed)
     */
    boolean remove(int groupId);

    /**
     * method get all groups dto
     *
     * @return list groups dto
     */
    List<GroupDto> getAll();
}