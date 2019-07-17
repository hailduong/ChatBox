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

    //list of all clients connected to server
    public static DefaultListModel<Client> clients = new DefaultListModel<>();
    public final String SERVER_NAME = "localhost";
    public final int PORT = 1234;
    private JPanel serverBoxPanel;
    private JList serverNameList;
    private ServerThread serverThread = null;

    public ServerBox() {
        serverNameList.setModel(clients);

        if (serverThread == null) {
            try {
                Server server = new Server(SERVER_NAME, PORT);
                serverThread = new ServerThread(server);
                new Thread(serverThread).start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        serverNameList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getClickCount() == 2) {
                    ChatBox chatBox = new ChatBox();
                    chatBox.setUsername(serverNameList.getSelectedValue().toString());
                    chatBox.setVisible(true);
                }

            }
        });
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("Server User List");
        frame.setContentPane(new ServerBox().serverBoxPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
