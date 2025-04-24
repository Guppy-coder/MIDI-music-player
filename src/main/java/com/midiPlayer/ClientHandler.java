package com.midiPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    private PrintWriter out;
    private BufferedReader in;
    public static ArrayList<Socket> sockets = new ArrayList<>();

    public ClientHandler(Socket socket){
        try {
            this.out = new PrintWriter(socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sockets.add(socket);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String message = "";
        while (true){
            try {
                message = in.readLine();
                for (Socket socket: sockets){
                    out.println(message);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
