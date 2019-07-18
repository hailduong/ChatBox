/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui;

import com.business.ClientHandler;
import com.business.ServerThread;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author TrongDuyDao
 */
public class ChatBox extends JDialog {

    /**
     * Creates new form ChatBox
     */
    private ClientHandler clientHandler;
    //    private ServerBox server;
    private String username;
    private JTextArea chatArea;
    private JPanel chatPanel;
    private JTextField chatField;
    private JButton sendButton;

    public static void main(String[] args) {
        new ChatBox();
    }

    public ChatBox() {
        // server = (ServerBox) parent;
        this.setContentPane(chatPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientHandler.send(chatField.getText());
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
    }

    public void setUsername(String username) {
        this.username = username;
        clientHandler = ServerThread.clients.get(username);

        clientHandler.setTxtContent(chatArea);

        new Thread(clientHandler).start();
        setTitle("Chat with " + username);
    }
}
