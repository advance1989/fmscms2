package cn.nodemedia.nodemediaclient;

/**
 * Created by Administrator on 2015/10/9.
 */
public class User {
    private String  uid=null;
    private String  username=null;
    private String  nickname=null;
    private String  roomid=null;
    private String  eid=null;
    private String  ucuid=null;
    public User(String uid, String username, String nickname, String roomid, String eid,String ucuid) {
        this.uid = uid;
        this.username = username;
        this.nickname = nickname;
        this.roomid = roomid;
        this.eid = eid;
        this.ucuid=ucuid;
    }
    public String getUcuid() {
        return ucuid;
    }

    public void setUcuid(String ucuid) {
        this.ucuid = ucuid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getUsername() {
        return username;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }
}
