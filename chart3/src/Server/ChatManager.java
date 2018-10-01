package Server;

import Dao.Chart;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class ChatManager {

    List<ChatSocket> vector = new ArrayList<>();
    HashMap<ChatSocket,String> chartHistory=new HashMap();
    private ChatManager () { }
    private static final ChatManager cm = new ChatManager();
    public static ChatManager getChatManager() {
        return cm;
    }

    public void add(ChatSocket chatSocket) {
        vector.add(chatSocket);
    }

    public void publish(ChatSocket chatSocket,String s,String username) throws IOException {
        List<String> list=new ArrayList<>();
        for(int i = 0;i < vector.size();i++) {
            ChatSocket cs = vector.get(i);
            if(!cs.equals(chatSocket)) {
                if(cs.isAlive()){
                    cs.out(s,username,cs);
                }else{
                    if(!contains(list,chartHistory.get(cs))){
                        if(!chartHistory.get(cs).equals(username))
                        new Chart(chartHistory.get(cs),username+"："+s).add();
                    }
                }
                list.add(chartHistory.get(cs));
            }
        }
    }
    public void onlineMessage(ChatSocket chatSocket,String username) throws IOException{
        MapRemove(username);
        chartHistory.put(chatSocket,username);
        for(int i = 0;i < vector.size();i++) {
            ChatSocket cs = vector.get(i);
            if(!cs.equals(chatSocket)) {
                if(cs.isAlive())
                cs.outOnlineMessage(username,cs);
            }
        }
    }

    boolean contains(List<String> list,String str){
        for(String strs:list){
            if(str.equals(strs)){
                return true;
            }
        }
        return false;
    }

    void MapRemove(String value){
        for(Map.Entry map: chartHistory.entrySet()){
            if(map.getValue().equals(value)){
                vector.remove(map.getKey());
                chartHistory.remove(map.getKey());
                return;
            }
        }
    }
}
