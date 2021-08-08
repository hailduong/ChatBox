package com.ui;

import com.DAO.MessageDAO;
import com.DAO.UserDAO;
import com.business.ClientThread;
import com.entity.Client;
import com.entity.Server;
import com.model.MessageDetail;
import com.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientBox {
    private JTextField hostIPTextField;
    private JTextField portTextField;
    private JTextField userNameTextField;
    private JButton connectButton;
    private JTextArea chatArea;
    private JTextField chatBoxField;
    private JButton sendButton;
    private JLabel statusLabel;
    private JPanel ClientBoxPanel;

    private ClientThread clientThread = null;

    public ClientBox() {
        /**
         * Clicking the `Connect` button, would connect the Client chat box to the server.
         */
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clientThread == null) {
                    try {

                        // Init the `Client` entity and `Server` entity
                        String userName = userNameTextField.getText();
                        Client client = new Client(userNameTextField.getText(), "");
                        Server server = new Server(hostIPTextField.getText(), Integer.parseInt(portTextField.getText()));

                        // Open a socket and connect to server, with a thread
                        clientThread = new ClientThread(server, chatArea);
                        Thread thread = new Thread(clientThread);
                        thread.start();

                        // Inform when the client is connected to the server
                        clientThread.send(client.getUsername());
                        statusLabel.setText("Connected to server!");
                        connectButton.setEnabled(false);

                        // Add user to db
                        User user = new User(userName, userName);
                        UserDAO.getInstance().addUser(user);

                        // Get chat history
                        List<MessageDetail> messageDetailList = MessageDAO.getInstance().getAllMessagesForUser(userName);
                        for (MessageDetail messageDetail : messageDetailList) {
                            String fromUser = messageDetail.fromUser;
                            String messageContent = messageDetail.content;
                            chatArea.append("\n" + fromUser + ": " + messageContent);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        /**
         * Clicking the `Send` button, would send the message
         */
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Send the message
                    String message = chatBoxField.getText();
                    clientThread.send(message);

                    // Clear the input box
                    chatBoxField.setText("");
                } catch (Exception ex) {
                    Logger.getLogger(ClientBox.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public static void main(String args[]) {
        // Init UI
        JFrame frame = new JFrame("Client Chat Box");
        frame.setContentPane(new ClientBox().ClientBoxPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(550, 300));
        frame.pack();
        frame.setVisible(true);
    }
}
