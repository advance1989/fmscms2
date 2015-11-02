package cn.nodemedia.nodemediaclient;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.*;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2015/10/11.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class More extends Fragment implements View.OnClickListener {
    private String getemceeinfo_url="";
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==2){
                Drawable dw= (Drawable) msg.obj;
                emcee_head.setImageDrawable(dw);
                return;
            }
            JSONObject jsonobj= (JSONObject) msg.obj;

            try {

                if(jsonobj!=null){
                    String emceename_str=jsonobj.getString("nickname");
                    ucuid_str=jsonobj.getString("ucuid");
                    String address_str=jsonobj.getString("address");
                    String att_str=jsonobj.getString("att");
                    emcee_name.setText(emceename_str);
                    emcee_roomid.setText(roomid);
                    emcee_address.setText(address_str);
                    emcee_att.setText(att_str+"人关注");
                    getemcee_head();

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.handleMessage(msg);
        }
    };
    private ImageButton ibt1,ibt2,ibt3,ibt4;
    private ImageView emcee_head;
    private TextView emcee_att;
    private TextView emcee_name;
    private TextView emcee_roomid;
    private TextView emcee_address;
    private Message message;
    private String roomid;
    private String ucuid_str;
    private AsyncImageTask as;
    private MyApplication myApplication;

    public More(String roomid) {
        this.roomid = roomid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myApplication= (MyApplication) getActivity().getApplication();
        getemceeinfo_url=myApplication.getUrl()+"/index.php/Api/android_getemceeinfo/roomid/";
        View v=inflater.inflate(R.layout.more, null);
        emcee_att= (TextView) v.findViewById(R.id.emcee_att);
        emcee_name= (TextView) v.findViewById(R.id.emcee_name);
        emcee_roomid= (TextView) v.findViewById(R.id.emcee_roomid);
        emcee_address= (TextView) v.findViewById(R.id.emcee_address);
        emcee_head= (ImageView) v.findViewById(R.id.emcee_head);
        ibt1= (ImageButton) v.findViewById(R.id.jubao_img);
        ibt2= (ImageButton) v.findViewById(R.id.chongzhi_img);
        ibt3= (ImageButton) v.findViewById(R.id.fenxiang_img);
        ibt4= (ImageButton) v.findViewById(R.id.shangcheng_img);
        ibt1.setOnClickListener(this);
        ibt2.setOnClickListener(this);
        ibt3.setOnClickListener(this);
        ibt4.setOnClickListener(this);

        new Thread(){
            @Override
            public void run() {
                HttpClient httpClient= new DefaultHttpClient();
                HttpGet httpGet=new HttpGet(getemceeinfo_url+roomid);
                try {
                    HttpResponse httpResponse=httpClient.execute(httpGet);
                    HttpEntity httpEntity=httpResponse.getEntity();
                    String res= EntityUtils.toString(httpEntity);
                    JSONObject jsonObject=new JSONObject(res);
                    JSONObject jsonObject2=jsonObject.getJSONObject("data");
                    message=Message.obtain();
                    message.obj=jsonObject2;
                    handler.sendMessage(message);
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        }.start();
        return v;
    }

    private void getemcee_head(){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url=new URL(myApplication.getUrl()+"/passport/avatar.php?uid="+ucuid_str);
                    InputStream is=url.openStream();
                    Drawable dw=Drawable.createFromStream(is, "head.jpg");
                    message=Message.obtain();
                    message.what=2;
                    message.obj=dw;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.jubao_img:
                intent=new Intent(getActivity(),JubaoActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.fenxiang_img:
                intent= new Intent(getActivity(),FenxiangActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.chongzhi_img:
                intent= new Intent(getActivity(),ChongZhiActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.shangcheng_img:
                intent= new Intent(getActivity(),ShangChengActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }

    }
}
