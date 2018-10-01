package Server;

import Dao.Chart;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

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

    public void out(String s,String username,ChatSocket socket) throws IOException {
        socket.dos.writeUTF(username+" 说: "+ s);
    }

    public void outOnlineMessage(String username,ChatSocket socket) throws IOException {
        socket.dos.writeUTF(username+" 上线了");
    }
    @Override
    public void run() {
        try {
            String s;
            username=dis.readUTF();
            ChatManager.getChatManager().onlineMessage(this,username);
            List<Chart> chartList= new Chart(username).query();
            System.out.println("查询历史记录。");
            if(chartList.size()>0){
                for(Chart c:chartList){
                    dos.writeUTF("收到历史记录：" + c.getContent());
                }
            }
            while (!socket.isClosed()) {
                s=dis.readUTF();
                System.out.println(s);
                ChatManager.getChatManager().publish(this, s,username);
            }
        } catch (IOException e) {
            System.out.println(username+"远程链接已关闭！");
        }
    }
}
