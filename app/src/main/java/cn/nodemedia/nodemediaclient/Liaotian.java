package cn.nodemedia.nodemediaclient;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import cn.cn.weilian.activity.LoginActivity;

/**
 * Created by Administrator on 2015/10/6.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Liaotian extends Fragment{

    private List<Message> list=null;
    private ListView listView;
    private Socket mSocket;
    private MessageListView mlv=null;
    private EditText sendmsg;
    private ImageButton sendbutt;
    private ShowActivity may;
    private FragmentManager fm;
    private android.app.FragmentTransaction ft;
    private String gifliststr=null;
    private List<Giftinfo> giftinfolist=null;
    private User user;
    private MyApplication myApplication;
    private Boolean isfirst=true;
    private GiftList giftList=null;
    public Liaotian(User user) {
        this.user = user;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myApplication = (MyApplication) getActivity().getApplication();

        if(list==null){
            list=new ArrayList<Message>();
        }

        if(myApplication.getGiftinfoList()!=null){
            giftinfolist=myApplication.getGiftinfoList();
        }else{
            giftinfolist=new ArrayList<Giftinfo>();
            /*获取礼物列表*/
            new Thread() {
                @Override
                public void run() {

                    HttpClient httpClient=new DefaultHttpClient();
                    HttpGet httpGet=new HttpGet(myApplication.getUrl()+"/index.php/Api/android_getgiflist");
                    String res="";
                    try {
                        HttpResponse httpResponse=httpClient.execute(httpGet);
                        HttpEntity httpEntity=httpResponse.getEntity();
                        if(httpEntity!=null){
                            res= EntityUtils.toString(httpEntity);

                            gifliststr=res;
                            JSONObject jsonObjectgiflist=new JSONObject(gifliststr);
                            JSONArray jsonArraygiftlist=jsonObjectgiflist.getJSONArray("data");

                            if(jsonArraygiftlist!=null){
                                for (int i=0;i<jsonArraygiftlist.length();i++){
                                    //String gifname, String icon, String price,String type
                                    JSONObject giftitem=jsonArraygiftlist.getJSONObject(i);
                                    Giftinfo giftinfo=new Giftinfo(giftitem.getString("giftname"),giftitem.getString("giftIcon"),giftitem.getString("needcoin"),giftitem.getString("giftType"),giftitem.getString("sid"),giftitem.getString("id"));
                                    giftinfolist.add(giftinfo);
                                    myApplication.setGiftinfoList(giftinfolist);

                                }

                            }


                        }else{

                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                }
            }.start();
            //myApplication.setGiftinfoList(giftinfolist);
        }


        /******************************************/

        View v=inflater.inflate(R.layout.liaotian,null);
        /*if(list==null){
            list=new ArrayList<Message>();
        }*/

        listView= (ListView) v.findViewById(R.id.listView);

        this. sendmsg= (EditText)v.findViewById(R.id.sendText);
        this. sendbutt= (ImageButton)v.findViewById(R.id.giftbtn);
        //SlidingDrawer mDrawer= (SlidingDrawer) v.findViewById(R.id.slidingDrawer);

        //listView.setDivider(null);分割线
        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendmsg.setHint(null);
            }
        });
        fm=getFragmentManager();

        ft=fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.sendbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = getActivity().getWindow().peekDecorView();
                if (v != null) {
                    InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if(giftinfolist!=null){
                    if(giftList==null){
                        giftList=new GiftList(giftinfolist,mSocket,Liaotian.this);
                    }
                    ft.replace(R.id.content,giftList);
                    ft.commit();

                }else{
                    Log.i("kong","kong");
                }

            }
        });

        final View.OnKeyListener onkey=new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.KEYCODE_ENTER){

                    if(user.getUid()=="0"||user.getUid()==""){
                        new AlertDialog.Builder(Liaotian.this.getActivity()).setTitle("请先登录").setMessage(
                                "还没有登录,请先去登录").setPositiveButton("前往登录",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(Liaotian.this.getActivity(), LoginActivity.class);
                                        startActivity(intent);getActivity().finish();

                                    }
                                }).setNegativeButton("一会再说",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                        return true;

                    }

                    String msg = sendmsg.getText().toString();
                    Sendmesg(msg);
                    sendmsg.setText("");
                    return true;

                }

                return  false;
            }
        };
        sendmsg.setOnKeyListener(onkey);
        /* MyApplication ma= (MyApplication) getActivity().getApplication();
        List<Message> ls=ma.getmsgList();

        lv.setAdapter(mlv);*/

        mlv=new MessageListView(listView,list,getActivity());
        listView.setAdapter(mlv);
        return v;
    }

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication ma= (MyApplication) getActivity().getApplication();
        mSocket=ma.getSocket();
        mSocket.on("msg", onNewMessage);
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isfirst){
            isfirst=false;
            may=(ShowActivity)getActivity();
            mSocket=may.mSocket;
            mSocket.on("msg", onNewMessage);
            mSocket.on("sendgift", onSendGift);
            connectSocket();
        }

    }

    public void Sendmesg(String msg){

        attemptSend(user.getUid(),user.getRoomid(), msg, "1", "hello", user.getNickname(), "7", "789654");
    }
    public void attemptSend(String uid,String roomid,String text,String msgType,String tousername,String username,String sid,String u_roomid) {

        if (TextUtils.isEmpty(text)) {
            return;
        }
        String message="{action:0,uid:"+uid+",roomid:"+roomid+",text:'"+text+"',msgType:"+msgType+",tousername:'"+tousername+"',username:'"+username+"',nickname:'"+username+"',sid:"+sid+",u_roomid:"+u_roomid+"}";
        Log.i("dapeng",message);
        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONObject(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("msg", jsonObject);

    }
    public void connectSocket(){
        String str_conn= "{roomnum:"+user.getRoomid()+",username:'"+user.getUsername()+"',uid:"+user.getUid()+",nickname:'"+user.getNickname()+"',ucuid:"+user.getUcuid()+"}";
        Log.i("asdasdasdad","{roomnum:"+user.getRoomid()+",username:'"+user.getUsername()+"',uid:"+user.getUid()+",nickname:'"+user.getNickname()+"',ucuid:"+user.getUcuid()+"}");
        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONObject(str_conn);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("cnn",jsonObject);
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            may.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    String roomid;

                    try {
                        username = data.getString("username");
                        message = data.getString("text");
                        roomid=data.getString("roomid");
                        Message mesobj = new Message(username, message, roomid);
                        list.add(mesobj);
                        mlv=new MessageListView(listView,list,getActivity());
                        mlv.setListmsg(list);
                        listView.setAdapter(mlv);
                        //myApplication.setmsgList(list);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // add the message to view

                }
            });

        }
    };
    /*发送礼物*/

    private Emitter.Listener onSendGift = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            may.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String giftname;
                    String Icon;
                    String count;
                    String roomid;

                    try {
                        username = data.getString("username");
                        giftname = data.getString("giftname");
                        Icon = data.getString("giftcoin");
                        count = data.getString("giftcount");
                        roomid = data.getString("roomid");
                        Message mesobj = new Message(username, "赠送" + giftname + count + "个", roomid);
                        mesobj.setGiftIcon(Icon);
                        list.add(mesobj);
                        mlv=new MessageListView(listView,list,getActivity());
                        mlv.setListmsg(list);
                        listView.setAdapter(mlv);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // add the message to view

                }
            });

        }
    };




}

