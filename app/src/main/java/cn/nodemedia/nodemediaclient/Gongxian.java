package cn.nodemedia.nodemediaclient;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/6.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Gongxian extends Fragment {
    private String gongxian;
    private List<Userinfo> userlist;
    private ListView listView;
    public Gongxian(String getjsoninfo) {
        this.gongxian=getjsoninfo;
        Log.i("44445",gongxian);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.gongxian,null);

        listView= (ListView) v.findViewById(R.id.gongxianlist);
        userlist=new ArrayList<Userinfo>();

        try {
            JSONObject jsongongxian=new JSONObject(gongxian);
            //tx.setText(gongxian);
            JSONArray data= (JSONArray) jsongongxian.get("data");
            for (int i=0;i<data.length();i++){
                JSONObject info= (JSONObject) data.get(i);
                //JSONArray infovoo= (JSONArray) info.get("voo");
               // JSONObject infovoo1= (JSONObject) infovoo.get(0);
                Userinfo userinfo=new Userinfo();
                if(info.getString("nickname")==null){
                    userinfo.setUsername("");
                }else {
                    userinfo.setUsername(info.getString("nickname"));
                }
                if(info.getString("ucuid")==null){
                    userinfo.setImgurl("");
                }else{
                    userinfo.setImgurl(info.getString("ucuid"));
                }
                if(info.getString("total")==null){
                    userinfo.setGongxianzhi("");
                }else{
                    userinfo.setGongxianzhi(info.getString("total"));
                }
                if(info.getString("curroomnum")==null){
                    userinfo.setRoomid("");
                }else{
                    userinfo.setRoomid(info.getString("curroomnum"));
                }


                userlist.add(userinfo);

               // Log.i("4444",info.getString("nickname"));
            }


            //tx.setText(infovoo1.getString("username"));
        } catch (JSONException e) {

            e.printStackTrace();
        }

        GongxianListView gongxianListView=new GongxianListView(listView,userlist,getActivity());
        listView.setAdapter(gongxianListView);

        return v;
    }



}
