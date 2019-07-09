/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui;

import com.business.ClientHandler;
import com.business.ServerThread;

import javax.swing.*;

/**
 * @author TrongDuyDao
 */
public class ChatBox extends JDialog{

    /**
     * Creates new form ChatBox
     */
    private ClientHandler cs;
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

    }

    public void setUsername(String username) {
        this.username = username;
        cs = ServerThread.clients.get(username);
//        cs.setTxtContent(txtContent);
        new Thread(cs).start();
//        setTitle("Chat with " + cs.getClient().getUsername());
    }

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {
        try {
//            cs.send(txtMessage.getText());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
