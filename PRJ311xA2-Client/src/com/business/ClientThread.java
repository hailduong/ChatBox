/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.business;

import com.entity.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JTextArea;

/**
 * @author TrongDuyDao
 */
public class ClientThread implements Runnable, Serializable {

    //for I/O
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket socket;
    private Server server;
    private JTextArea txtContent;

    /*provide setter and getter here*/

    public ClientThread(Server server, JTextArea txtContent) {
        try {

            this.server = server;
            this.txtContent = txtContent;

            /*connect to server and get input/output stream here*/
            this.socket = new Socket(server.getHost(), server.getPort());
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        /*receive message from server and output to txtContent*/
        try {
            while (true) {
                Object line = dis.readUTF();
                if (line != null) {
                    this.txtContent.append("\n" + line);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void send(Object line) throws Exception {
        /*send a message line to server*/
        this.dos.writeUTF(line.toString());
        this.txtContent.append("\nMe: " + line);
    }

}
