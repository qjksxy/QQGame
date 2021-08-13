package game;

import java.io.*;
import java.net.Socket;

public class SocketThread extends Thread {
    private Socket socket;

    public SocketThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            byte[] b = new byte[1024];
            int msgLen = dataInputStream.read(b);
            String clientMsg = new String(b, 0, msgLen);
            String serverMsg = Core.core(clientMsg);
            if (serverMsg.equals("")) {
                serverMsg = "Server：No information to response.";
            }
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF(serverMsg);
            dataOutputStream.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
