//package com.midiPlayer;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class Server {
//
//    private final int PORT_NUMBER = 5000;
//
//    public Server() {
//    }
//
//    private void startServer(){
//        try(ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
//            System.out.println("Listening on port: " + PORT_NUMBER);
//            while (true) {
//                Socket clientSocket = serverSocket.accept();
//                ClientHandler client = new ClientHandler(clientSocket);
//
//                Thread thread = new Thread(client);
//                thread.start();
//            }
//        }catch (IOException e){
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public static void main(String[] args) {
//        Server server = new Server();
//        server.startServer();
//    }
//}
