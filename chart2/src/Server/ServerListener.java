package Server;

import Dao.Chart;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ServerListener extends Thread{
    static ChatSocket cs;
    @Override
    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(2056);
            while (true){
                Socket socket = serverSocket.accept();
                cs= new ChatSocket(socket);
                cs.start();
                List<Chart> chartList= new Chart("A").query();
                System.out.println("查询历史记录。");
                if(chartList.size()>0){
                    for(Chart c:chartList){
                        cs.dos.writeUTF("历史记录：" + c.getContent());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new ServerListener().start();
        System.out.println("B上线了！");
        while (true) {
            System.out.println("我：");
            Scanner in = new Scanner(System.in);
            try {
                String x = in.next();
                if (cs == null || !cs.isAlive()) {
                    new Chart("A", "A:"+x).add();
                } else
                    cs.dos.writeUTF("B说：" + x);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}