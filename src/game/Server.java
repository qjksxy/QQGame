package game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int port = 7700;
        for (int i = 0; i < 1000; i++) {
            System.out.println(i + "次循环");
            try {
                serverSocket = new ServerSocket(port);
                Socket socket = serverSocket.accept();
                Thread socketThread = new SocketThread(socket);
                socketThread.start();
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

