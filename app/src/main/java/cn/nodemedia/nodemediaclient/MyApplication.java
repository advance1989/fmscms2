package cn.nodemedia.nodemediaclient;

import android.app.Application;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2015/10/6.
 */
public class MyApplication extends Application {
    private List<Message> list=null;
    private Socket socket;
    private List<Giftinfo> giftinfoList;
    private JSONObject userinfo=null;
    private String roomid=null;
    private User user=null;
    private String url="http://app.fmscms.com";
     //private String url="http://zhibo.1198.com";
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public JSONObject getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(JSONObject userinfo) {
        this.userinfo = userinfo;
    }

    public List<Giftinfo> getGiftinfoList() {
        return giftinfoList;
    }

    public void setGiftinfoList(List<Giftinfo> giftinfoList) {
        this.giftinfoList = giftinfoList;
    }

    public void setmsgList(List<Message>  list){
        this.list=list;
    }
    public List<Message> getmsgList(){
        return this.list;
    }
    public void setSocket(Socket socket){
        this.socket=socket;
    }
    public Socket getSocket(){
        return this.socket;
    }

}

