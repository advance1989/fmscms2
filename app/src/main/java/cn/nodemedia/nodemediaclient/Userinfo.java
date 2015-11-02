package cn.nodemedia.nodemediaclient;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2015/10/7.
 */
public class Userinfo {
    private String username;
    private String  age;
    private String imgurl;
    private String gongxianzhi;
    private String roomid;
    private String uid;


    public Userinfo(String username, String imgurl, String gongxianzhi, String roomid) {
        this.username = username;
        this.imgurl = imgurl;
        this.gongxianzhi = gongxianzhi;
        this.roomid = roomid;
    }
    public Userinfo() {

    }

    public void setuid(String uid) {
        this.uid = uid;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setGongxianzhi(String gongxianzhi) {
        this.gongxianzhi = gongxianzhi;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }
    public String getUsername() {

        return username;
    }
    public String getuid() {
        return uid;
    }
    public String getAge() {
        return age;
    }

    public String getimgurl() {
        return imgurl;
    }

    public String getGongxianzhi() {
        return gongxianzhi;
    }

    public String getRoomid() {
        return roomid;
    }
}
