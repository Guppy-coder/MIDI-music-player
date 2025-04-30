package com.midiPlayer;

import java.io.*;
import java.net.*;
import java.util.*;
public class VerySimpleChatServer {
    ArrayList<ObjectOutputStream> clientOutputStreams;

    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket sock;

        public ClientHandler(Socket clientSocket) {
            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } // close constructor

        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("read " + message);
                    tellEveryone(message);

                } // close while
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } // close run
    } // close inner class

    public static void main(String[] args) {
        new VerySimpleChatServer().go();
    }

    public void go() {
        clientOutputStreams = new ArrayList<>();
        try (ServerSocket serverSock = new ServerSocket(5000);) {

            while (true) {
                Socket clientSocket = serverSock.accept();
                ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("got a connection");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    } // close go

    public void tellEveryone(String message) {
        Iterator it = clientOutputStreams.iterator();
        while (it.hasNext()) {
            try {
                ObjectOutputStream writer = (ObjectOutputStream) it.next();
                writer.writeObject(message);
                writer.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } // end while

    } // close tellEveryone
}