package crud_app.service.impl;

import crud_app.dto.GroupDto;
import crud_app.dto.TopicDto;
import crud_app.entity.Group;
import crud_app.entity.Topic;
import crud_app.repository.GroupRepository;
import crud_app.repository.TopicRepository;
import crud_app.repository.impl.GroupRepositoryImpl;
import crud_app.repository.impl.TopicRepositoryImpl;
import crud_app.service.GroupService;

import java.util.Comparator;
import java.util.List;

/**
 * this is implementation group service interface
 */
public class GroupServiceImpl implements GroupService {
    /**
     * topic repository interface
     */
    private GroupRepository groupRepository = new GroupRepositoryImpl();

    /**
     * method save group
     *
     * @param group group dto
     * @return saved group dto
     */
    @Override
    public GroupDto create(GroupDto group) {
        Group newGroup = new Group(group.getId(), group.getName());
        Group result = groupRepository.createGroup(newGroup);
        group.setId(result.getId());
        return group;
    }

    /**
     * method update group
     *
     * @param group group dto
     * @return updated group dto
     */
    @Override
    public GroupDto update(GroupDto group) {
        Group updateGroup = new Group(group.getId(), group.getName());
        groupRepository.updateGroup(updateGroup);
        return group;
    }

    /**
     * method get group dto by id
     *
     * @param groupId group id in database
     * @return group dto
     */
    @Override
    public GroupDto get(int groupId) {
        Group group = groupRepository.getGroup(groupId);
        return GroupDto.toDTO(group);
    }

    /**
     * method delete group by id
     *
     * @param groupId group id in database
     * @return result (true - removed)
     */
    @Override
    public boolean remove(int groupId) {
        return groupRepository.removeGroup(groupId);
    }

    /**
     * method get all group dto
     *
     * @return list group dto
     */
    @Override
    public List<GroupDto> getAll() {
        return groupRepository.getAllGroup().stream()
                .map(GroupDto::toDTO)
                .sorted(Comparator.comparingInt(GroupDto::getId))
                .toList();
    }
}
