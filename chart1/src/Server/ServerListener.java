package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerListener extends Thread{
    static ChatSocket cs;
    @Override
    public void run() {

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(2056);
            Socket socket = serverSocket.accept();
            cs= new ChatSocket(socket);
            cs.start();
            while(true) {
                System.out.println("我：");
                Scanner in = new Scanner(System.in);
                try {
                    cs.dos.writeUTF("B说：" + in.next());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new ServerListener().start();
        System.out.println("B上线了！");
    }
}