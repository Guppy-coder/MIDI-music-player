package com.midiPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleChatClientA {

    JTextArea incoming;
    JTextField outgoing;
    PrintWriter writer;
    Socket sock;
    BufferedReader reader;
    public void go() {
        // make gui and register a listener with the send button
        // call the setUpNetworking() method
        JFrame frame = new JFrame("Ludicrously Simple Chat Client");
        JPanel mainPanel = new JPanel();
        incoming = new JTextArea(15,50);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        outgoing = new JTextField(20);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        setUpNetworking();

        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
        frame.setSize(400,500);
        frame.setVisible(true);
    }
    private void setUpNetworking() {
        // make a Socket, then make a PrintWriter
        // assign the PrintWriter to writer instance variable
        try {
            sock = new Socket("127.0.0.1", 5000);
            writer = new PrintWriter(sock.getOutputStream()); // we want to write to server
            System.out.println("networking established");
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    public class SendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            // get the text from the text field and
            // send it to the server using the writer (a PrintWriter)
            try {
                writer.println(outgoing.getText());
                writer.flush();

            } catch(Exception ex) {
                ex.printStackTrace();
            }
            outgoing.setText("");
            outgoing.requestFocus();
        }
    } // close SendButtonListener inner class

    public class IncomingReader implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("read"  + message);
                    incoming.append(message + "\n");

                } // close while
            } catch(Exception ex) {ex.printStackTrace();}
        } // close run
    } // close inner class

    public static void main(String[] args) {
        SimpleChatClientA client = new SimpleChatClientA();
        client.go();
    }
} // close outer class