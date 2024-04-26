package crud_app.utils;


import crud_app.entity.Group;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * class to convert resultSet to group
 */
public class GroupMapper {
    /**
     * method convert result set to group
     *
     * @param resultSet resultSet
     * @return group
     * @throws SQLException if resultSet read error
     */
    public static Group mapGroup(ResultSet resultSet) throws SQLException {
        return new Group(
                resultSet.getInt("id"),
                resultSet.getString("name"));
    }
}
