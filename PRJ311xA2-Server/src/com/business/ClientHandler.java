
package com.business;

import com.DAO.MessageDAO;
import com.entity.Client;
import com.model.MessageDetail;

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

    public void setTxtContent(JTextArea txtContent) {
        this.txtContent = txtContent;
    }

    public Client getClient() {
        return client;
    }

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
                Object message = dis.readUTF();
                if (message != null) {
                    txtContent.append("\n" + client.getUsername() + ": " + message);

                    // Save message to DB
                    MessageDetail messageDetail = new MessageDetail(client.getUsername(), "server", message.toString(), MessageDAO.EMessageType.MESSAGE.toString());
                    MessageDAO.getInstance().addMessageDetail(messageDetail);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Send the message to Client
    public void send(Object message) throws Exception {

        // Send to the client
        dos.writeUTF("Server:" + message.toString());

        // Add message to the text area
        txtContent.append("\nMe:" + message);
    }

}
