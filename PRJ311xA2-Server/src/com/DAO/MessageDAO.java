package com.DAO;

import com.context.DBContext;
import com.model.MessageDetail;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class MessageDAO {

    static MessageDAO instance;

    static public enum EMessageType {
        LOGIN,
        LOGOUT,
        MESSAGE
    }

    public static MessageDAO getInstance() {
        if (instance == null) instance = new MessageDAO();
        return instance;
    }

    public void addMessageDetail(MessageDetail message) throws Exception {
        String SQL = "INSERT INTO MessageDetail (fromUser, toUser, dateCreated, content, messageType) values(?, ?, ?, ?, ?)";
        Connection connection = new DBContext().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, message.fromUser);
        preparedStatement.setString(2, message.toUser);
        preparedStatement.setDate(3, new Date(message.dateCreated.getTime()));
        preparedStatement.setString(4, message.content);

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

    public List<MessageDetail> getAllMessagesForUser(String userName) throws Exception {
        String SQL = "SELECT * FROM MessageDetail WHERE fromUser = ? OR toUser = ? ORDER BY messageId LIMIT 101";
        Connection connection = new DBContext().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, userName);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<MessageDetail> messageDetailList = new ArrayList<MessageDetail>();
        while (resultSet.next()) {
            String fromUser = resultSet.getString("fromUser");
            String toUser = resultSet.getString("toUser");
            String content = resultSet.getString("content");
            String messageType = resultSet.getString("messageType");
            Date dateCreated = resultSet.getDate("dateCreated");
            MessageDetail messageDetail = new MessageDetail(fromUser, toUser, content, messageType, dateCreated);
            messageDetailList.add(messageDetail);
        }

        preparedStatement.close();
        connection.close();

        return messageDetailList;
    }
}
