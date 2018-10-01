package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatSocket extends Thread{

    Socket socket;
    DataInputStream dis;
    DataOutputStream dos ;
    String username;
    public ChatSocket (Socket socket) throws IOException {
        this.socket = socket;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos= new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            String s;
            username=dis.readUTF();
            System.out.println(username+"上线了！");
            while (this.isAlive()) {
                s=dis.readUTF();
                System.out.println(username+"说："+s);
            }
        } catch (IOException e) {
            System.out.println("链接被拒绝了！");
        }
    }
}
