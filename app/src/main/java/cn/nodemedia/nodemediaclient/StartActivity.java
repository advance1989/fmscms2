package cn.nodemedia.nodemediaclient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Administrator on 2015/10/10.
 */
public class StartActivity extends Activity {
    private String url="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        final View view = View.inflate(this, R.layout.start, null);
        setContentView(view);
        MyApplication myApplication= (MyApplication) getApplication();
        url=myApplication.getUrl()+"/index.php/Api/getcdnl";
        //渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
        aa.setDuration(3000);
        view.startAnimation(aa);
        final SharedPreferences sharedPreferences=getSharedPreferences("configfms",0);

        aa.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationStart(Animation animation) {
                new Thread(){
                    @Override
                    public void run() {
                        HttpClient httpClient=new DefaultHttpClient();
                        HttpGet httpGet=new HttpGet(url);

                        try {
                            HttpResponse httpResponse=httpClient.execute(httpGet);
                            HttpEntity httpEntity=httpResponse.getEntity();
                            String res= EntityUtils.toString(httpEntity);

                            if(!res.isEmpty()){
                                SharedPreferences.Editor edit=sharedPreferences.edit();
                                Log.i("startactivity cdnl", res);
                                edit.putString("cdnl",res);
                                edit.commit();


                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
                }.start();

            }

        });


    }

    /**
     * 跳转到...
     */
    private void redirectTo(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
