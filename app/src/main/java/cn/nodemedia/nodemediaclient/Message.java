package cn.nodemedia.nodemediaclient;

/**
 * Created by Administrator on 2015/10/3.
 */
public class Message {
    private String username;
    private String msg;
    private String rooomnum;
    private String giftIcon=null;

    public String getRooomnum() {
        return rooomnum;
    }

    public void setRooomnum(String rooomnum) {
        this.rooomnum = rooomnum;
    }

    public Message(String username,String msg,String rooomnum){
        this.username=username;
        this.msg=msg;
        this.rooomnum=rooomnum;

    }
    public String  getmsg(){

        return this.msg;
    }
    public String getUsername(){
        return this.username;
    }
    public String getGiftIcon() {
        return giftIcon;
    }

    public void setGiftIcon(String giftcoin) {
        this.giftIcon = giftcoin;
    }
}
