package cn.nodemedia.nodemediaclient;

/**
 * Created by Administrator on 2015/10/8.
 */
public class Giftinfo {
    private String gifname;
    private String icon;
    private String price;
    private String type;
    private String sid;
    private String gid;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
    public String getGifname() {
        return gifname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGifname(String gifname) {
        this.gifname = gifname;

    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Giftinfo(String gifname, String icon, String price,String type,String sid,String gid) {
        this.gifname = gifname;
        this.icon = icon;
        this.sid=sid;
        this.price = price;
        this.gid=gid;
    }
}
