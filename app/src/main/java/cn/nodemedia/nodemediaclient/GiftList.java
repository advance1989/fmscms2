package cn.nodemedia.nodemediaclient;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.*;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.weilian.fmscmsutil.Util;

/**
 * Created by Administrator on 2015/10/8.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class GiftList extends Fragment {
    private List<Giftinfo> giftinfoList;
    private List<Giftinfo> giftinfoList1=null;
    private List<Giftinfo> giftinfoList2=null;
    private List<Giftinfo> giftinfoList3=null;
    private String priceo_one;
    private EditText gifnum;
    private Button gifzs;
    private Giftinfo giftinfo=null;
    private Socket mSocket;
    private ImageButton select_num;
    private User user;
    private MyApplication myApplication;
    private GiftListGridView giftListGridView=null,giftListGridView2=null,giftListGridView3=null;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Liaotian liaotian=null;
    private GridView gridView,gridView2,gridView3;
    public GiftList(List<Giftinfo> giftinfoList,Socket mSocket,Liaotian liaotian) {
        this.giftinfoList=giftinfoList;
        this.mSocket=mSocket;
        this.liaotian=liaotian;


    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            new AlertDialog.Builder(GiftList.this.getActivity()).setTitle("余额不足").setMessage(
                    "先去充值在来吧").setPositiveButton("去充值",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("下次吧",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //礼物分类筛选

        giftinfoList1=new ArrayList<Giftinfo>();
        giftinfoList2=new ArrayList<Giftinfo>();
        giftinfoList3=new ArrayList<Giftinfo>();
        for(int i=0;i<giftinfoList.size();i++){
            if(Integer.parseInt(giftinfoList.get(i).getSid())==1){
                giftinfoList1.add(giftinfoList.get(i));
            }else if(Integer.parseInt(giftinfoList.get(i).getSid())==2){
                giftinfoList2.add(giftinfoList.get(i));
            }else if(Integer.parseInt(giftinfoList.get(i).getSid())==3){
                giftinfoList3.add(giftinfoList.get(i));
            }
        }

        fm=getFragmentManager();

        ft=fm.beginTransaction();
        myApplication= (MyApplication) getActivity().getApplication();
        user=myApplication.getUser();
        View v=inflater.inflate(R.layout.giftlist,null);
        TabHost host = (TabHost)v.findViewById(R.id.tabhost);

        host.setup();

        TabHost.TabSpec homeSpec = host.newTabSpec("Home"); // This param will

        // be used as tabId.

        homeSpec.setIndicator("普通");//getResources().getDrawable(R.drawable.bar_bg)

        homeSpec.setContent(R.id.tab1);

        host.addTab(homeSpec);



        TabHost.TabSpec garbageSpec = host.newTabSpec("Garbage");

        garbageSpec.setIndicator("高级");

        garbageSpec.setContent(R.id.tab2);

        host.addTab(garbageSpec);



        TabHost.TabSpec maybeSpec = host.newTabSpec("Help");

        maybeSpec.setIndicator("豪华");

        maybeSpec.setContent(R.id.tab3);

        host.addTab(maybeSpec);

        TabWidget tabWidget=host.getTabWidget();
        for (int i =0; i < tabWidget.getChildCount(); i++) {
            //修改Tabhost高度和宽度
            tabWidget.getChildTabViewAt(i).setBackgroundColor(Color.rgb(255,170,71));
            //tabWidget.getChildAt(i).getLayoutParams().width = 65;
            //修改显示字体大小
            TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(15);
            tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0); //取消文字底边对齐
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE); //设置文字居中对齐
        }



        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override

            public void onTabChanged(String tabId) {

               /* Toast toast = Toast.makeText(getActivity(), tabId, Toast.LENGTH_SHORT);

                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 50);

                toast.show();*/

            }

        });

        //获取gridview
        gridView= (GridView) v.findViewById(R.id.gridView);
        gridView2= (GridView) v.findViewById(R.id.gridView_2);
        gridView3= (GridView) v.findViewById(R.id.gridView_3);
        select_num= (ImageButton) v.findViewById(R.id.select_num);
        final EditText editText= (EditText) v.findViewById(R.id.gifnum);
        final String[] num={"1","10","30","66","188","520","1314"};
        gifzs= (Button) v.findViewById(R.id.gifzs);
        select_num.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {

                select_num.setBackground(getResources().getDrawable(R.drawable.num_choose02_bg));
                final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("请选择数量");
                builder.setSingleChoiceItems(num, 1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        select_num.setBackground(getResources().getDrawable(R.drawable.num_choose01_bg));
                        editText.setText(num[i]);
                        dialogInterface.dismiss();

                    }
                });
                builder.show();

            }
        });
        gifzs.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                String num=editText.getText().toString().trim();
                if(num.isEmpty()){
                    Toast.makeText(getActivity(),"请输入正确数量", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(giftinfo==null) {
                    Toast.makeText(getActivity(),"请选择礼物", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(user.getUid()=="-1") {
                    Toast.makeText(getActivity(),"请登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                String json= null;

                json = "{uid:"+user.getUid()+",username:'"+user.getUsername()+"',eid:"+user.getEid()+",roomid:"+user.getRoomid()+",giftid:"+giftinfo.getGid()+",giftname:'"+giftinfo.getGifname()+"',giftcount:"+num+",giftcoin:'"+giftinfo.getIcon()+"'}";

                Log.i("getUid", user.getUid() + "" + json);
                try {
                    JSONObject jsonObject=new JSONObject(json);
                    sendGift(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        giftListGridView=new GiftListGridView(gridView,giftinfoList1,getActivity());
        giftListGridView2=new GiftListGridView(gridView2,giftinfoList2,getActivity());
        giftListGridView3=new GiftListGridView(gridView3,giftinfoList3,getActivity());

        gridView.setAdapter(giftListGridView);
        gridView2.setAdapter(giftListGridView2);
        gridView3.setAdapter(giftListGridView3);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                giftinfo = (Giftinfo) giftListGridView.getItem(i);
                priceo_one = giftinfo.getPrice();
                //Toast.makeText(getActivity(),giftinfo.getPrice(),Toast.LENGTH_LONG).show();

            }
        });
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                giftinfo= (Giftinfo) giftListGridView2.getItem(i);
                priceo_one=giftinfo.getPrice();
                //Toast.makeText(getActivity(),giftinfo.getPrice(),Toast.LENGTH_LONG).show();

            }
        });
        gridView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                giftinfo= (Giftinfo) giftListGridView3.getItem(i);
                priceo_one=giftinfo.getPrice();
                //Toast.makeText(getActivity(),giftinfo.getPrice(),Toast.LENGTH_LONG).show();

            }
        });
        return v;
    }
    public void sendGift(final JSONObject jsonObject){

        //礼物扣费
        new Thread(){
            @Override
            public void run() {
                try {
                    String res=Util.MyHttprequest(myApplication.getUrl()+"/index.php/Api/android_show_sendGift/uid/"+jsonObject.getString("uid")+"/toid/"+jsonObject.getString("eid")+"/gid/"+jsonObject.getString("giftid")+"/count/"+jsonObject.getString("giftcount"));
                    JSONObject resobj=new JSONObject(res);
                    if(Integer.parseInt(resobj.getString("code"))==0){
                        mSocket.emit("sendgift", jsonObject);
                        ft.replace(R.id.content, liaotian);
                        ft.commit();

                    }else{
                        Message message=Message.obtain();
                        handler.sendMessage(message);
                    }
                    Log.i("pengpeng", res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

}
