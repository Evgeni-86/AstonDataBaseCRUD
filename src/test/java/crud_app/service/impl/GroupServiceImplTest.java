package crud_app.service.impl;

import crud_app.dto.GroupDto;
import crud_app.entity.Group;
import crud_app.repository.GroupRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {
    @Mock
    private GroupRepository groupRepository;
    @InjectMocks
    private GroupServiceImpl groupService = new GroupServiceImpl();

    @Test
    @DisplayName("create new group")
    public void create() {
        //Arrange
        GroupDto groupDto = new GroupDto(1, "Test group");
        Group newGroup = new Group(groupDto.getId(), groupDto.getName());
        Group savedGroup = new Group(1, "Test group");
        Mockito.when(groupRepository.createGroup(newGroup)).thenReturn(savedGroup);
        //Act
        GroupDto actual = groupService.create(groupDto);
        //Assert
        Mockito.verify(groupRepository, times(1)).createGroup(newGroup);
        assertEquals(savedGroup.getId(), actual.getId());
        assertEquals(savedGroup.getName(), actual.getName());
    }

    @Test
    @DisplayName("update group")
    void update() {
        //Arrange
        GroupDto groupDto = new GroupDto(1, "Test group");
        Group updateGroup = new Group(groupDto.getId(), groupDto.getName());
        Group savedGroup = new Group(1, "Test group");
        Mockito.when(groupRepository.updateGroup(updateGroup)).thenReturn(savedGroup);
        //Act
        GroupDto actual = groupService.update(groupDto);
        //Assert
        Mockito.verify(groupRepository, times(1)).updateGroup(updateGroup);
        assertEquals(groupDto, actual);
    }

    @Test
    @DisplayName("get group by id")
    void get() {
        //Arrange
        int groupId = 1;
        GroupDto groupDto = new GroupDto(1, "Test group");
        Group group = new Group(1, "Test group");
        Mockito.when(groupRepository.getGroup(groupId)).thenReturn(group);
        //Act
        GroupDto actual = groupService.get(groupId);
        //Assert
        Mockito.verify(groupRepository, times(1)).getGroup(groupId);
        assertEquals(groupDto, actual);
    }

    @Test
    @DisplayName("remove group by id")
    void remove() {
        //Arrange
        int groupId = 1;
        Mockito.when(groupRepository.removeGroup(groupId)).thenReturn(true);
        //Act
        boolean actual = groupService.remove(groupId);
        //Assert
        Mockito.verify(groupRepository, times(1)).removeGroup(groupId);
        Assertions.assertTrue(actual);
    }

    @Test
    @DisplayName("get all groups")
    void getAll() {
        //Arrange
        List<Group> test = List.of(
                new Group(1, "New Title 1"),
                new Group(2, "New Title 2")
        );
        List<GroupDto> expected = test.stream().map(e -> new GroupDto(e.getId(), e.getName())).toList();
        Mockito.when(groupRepository.getAllGroup()).thenReturn(test);
        //Act
        List<GroupDto> actual = groupService.getAll();
        //Assert
        Mockito.verify(groupRepository, times(1)).getAllGroup();
        Assertions.assertIterableEquals(expected, actual);
    }
}