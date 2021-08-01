package com.DAO;

import com.context.DBContext;
import com.model.MessageDetail;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

enum EMessageType {
    LOGIN,
    LOGOUT,
    MESSAGE
}

public class MessageDAO {
    public void addMessageDetail(MessageDetail message) throws Exception {
        String SQL = "INSERT INTO MessageDetail values(?, ?, ?, ?, ?)";
        Connection connection = new DBContext().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, message.fromUser);
        preparedStatement.setString(2, message.toUser);
        preparedStatement.setDate(3, new Date(message.dateCreated.getTime()));
        preparedStatement.setString(3, message.content);

        if (message.messageType.equals(EMessageType.LOGIN.toString())) {
            preparedStatement.setString(5, "Login");
        } else if (message.messageType.equals(EMessageType.LOGOUT.toString())) {
            preparedStatement.setString(5, "Logout");
        } else {
            preparedStatement.setString(5, "Message");
        }

        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }
}
