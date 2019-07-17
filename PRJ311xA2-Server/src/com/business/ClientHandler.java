
package com.business;

import com.entity.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.swing.JTextArea;

public class ClientHandler implements Runnable {

    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket socket;
    private Client client;
    private JTextArea txtContent;

    /*provide the setter and getter here*/

    public ClientHandler(Socket socket) {
        this.socket = socket;

    }

    public ClientHandler(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

    }

    public ClientHandler(Socket socket, Client client, JTextArea txtContent) {
        this.socket = socket;
        this.client = client;
        this.txtContent = txtContent;
    }

    @Override
    public void run() {
        // Watch for individual connection
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            // Get user name and message
            while (true) {
                Object line = dis.readUTF();
                if (line != null) {
                    txtContent.append("\n" + client.getUsername() + ": " + line);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Send the message to Client
    public void send(Object line) throws Exception {

        // Send to the client
        dos.writeUTF("Server:" + line.toString());

        // Add message to the text area
        txtContent.append("\nMe:" + line);
    }

}
