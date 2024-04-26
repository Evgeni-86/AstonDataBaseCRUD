package crud_app.repository.impl;

import crud_app.AbstractTest;
import crud_app.entity.Group;
import crud_app.utils.DataBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class GroupRepositoryImplTest extends AbstractTest {

    private GroupRepositoryImpl SUT = new GroupRepositoryImpl();

    @Test
    @DisplayName("create new group")
    void createGroup() {
        //Arrange
        Group newGroup = new Group("createGroup");
        //Act
        Group actual = SUT.createGroup(newGroup);
        //Assert
        Assertions.assertTrue(newGroup.getId() > 0);
        Assertions.assertEquals(newGroup, actual);
        Assertions.assertEquals(newGroup, SUT.getGroup(newGroup.getId()));
    }

    @Test
    @DisplayName("update group")
    void updateGroup() {
        //Arrange
        Group newGroup = new Group("updateGroup");
        SUT.createGroup(newGroup);
        Group groupFromDB = SUT.getGroup(newGroup.getId());
        //Act
        groupFromDB.setName("updateGroup Update");
        SUT.updateGroup(groupFromDB);
        //Assert
        Assertions.assertEquals(groupFromDB, SUT.getGroup(groupFromDB.getId()));
    }

    @Test
    @DisplayName("get group by id")
    void getGroup() {
        //Arrange
        Group newGroup = new Group("getGroup");
        SUT.createGroup(newGroup);
        //Act
        Group actual = SUT.getGroup(newGroup.getId());
        //Assert
        Assertions.assertEquals(newGroup, actual);
    }

    @Test
    @DisplayName("remove group by id")
    void removeGroup() {
        //Arrange
        Group newGroup = new Group("removeGroup");
        SUT.createGroup(newGroup);
        //Act
        boolean actual = SUT.removeGroup(newGroup.getId());
        //Assert
        Assertions.assertTrue(actual);
        Exception exception = Assertions.assertThrows(RuntimeException.class,
                () -> SUT.getGroup(newGroup.getId()));
        Assertions.assertEquals("not row to map", exception.getMessage());
    }

    @Test
    @DisplayName("get all group")
    void getAllGroup() {
        //Arrange
        DataBase.initDataBase();
        List<Group> expected = new ArrayList<>() {{
            add(new Group("getAllGroup Group1"));
            add(new Group("getAllGroup Group2"));
            add(new Group("getAllGroup Group3"));
        }};
        expected.forEach(group -> SUT.createGroup(group));
        //Act
        List<Group> actual = SUT.getAllGroup();
        //Assert
        Assertions.assertIterableEquals(expected, actual);
    }
}