/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui;

import com.business.ServerThread;
import com.entity.Client;
import com.entity.Server;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * @author TrongDuyDao
 */
public class ServerBox extends javax.swing.JFrame {

    // List of all clients connected to server
    public static DefaultListModel<Client> clients = new DefaultListModel<>();
    public final String SERVER_NAME = "localhost";
    public final int PORT = 1234;

    private JPanel serverBoxPanel;
    private JList serverNameList;
    private ServerThread serverThread = null;

    public static void main(String args[]) {
        initUI();
    }

    private static void initUI() {
        JFrame frame = new JFrame("Server User List");
        frame.setContentPane(new ServerBox().serverBoxPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public ServerBox() {

        // Set the list of connected users.
        serverNameList.setModel(clients);

        // Start server thread
        if (serverThread == null) {
            try {
                Server server = new Server(SERVER_NAME, PORT);
                serverThread = new ServerThread(server);
                new Thread(serverThread).start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Bind the double click event, init the chat box for the selected user.
        serverNameList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getClickCount() == 2) {
                    // Init chat box
                    ChatBox chatBox = new ChatBox();
                    String selectedValue = serverNameList.getSelectedValue().toString();
                    // Set user name
                    chatBox.setUsername(selectedValue);
                    chatBox.setVisible(true);
                }

            }
        });
    }


}
