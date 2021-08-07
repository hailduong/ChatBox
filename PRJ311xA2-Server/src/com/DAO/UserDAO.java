package com.DAO;

import com.context.DBContext;
import com.model.User;;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public List<User> getAll() throws Exception {
        // Query data
        String selectQuery = "SELECT * FROM User";
        Connection connection = new DBContext().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        // Add to user list
        List<User> userList = new ArrayList<User>();
        while (resultSet.next()) {
            String userName = resultSet.getString("userName");
            String displayName = resultSet.getString("displayName");
            User newUser = new User(userName, displayName);
            userList.add(newUser);
        }

        // Clean up
        resultSet.close();
        connection.close();

        // Return data
        return userList;
    }

    public void addUser(User newUser) throws Exception {
        // Check if user exists
        List<User> userList = getAll();
        boolean isExisted = false;
        for (User user : userList) {
            if (user.userName.equalsIgnoreCase(newUser.userName)) {
                isExisted = true;
            }
        }

        // Insert the new user if not existed
        if (!isExisted) {
            String insertQuery = "INSERT INTO USER VALUES(?, ?)";
            Connection connection = new DBContext().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, newUser.userName);
            preparedStatement.setString(2, newUser.displayName);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }
    }
}
