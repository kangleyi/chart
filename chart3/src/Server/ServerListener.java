package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread{
    public static void main(String[] args) {
        new ServerListener().start();
    }
    @Override
    public void run() {

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(2056);
            while(true) {
                Socket socket = serverSocket.accept();
                ChatSocket cs = new ChatSocket(socket);
                cs.start();
                if(socket.isConnected()){
                    ChatManager.getChatManager().add(cs);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}