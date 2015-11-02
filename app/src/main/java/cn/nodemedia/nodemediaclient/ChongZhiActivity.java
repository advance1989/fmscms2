package cn.nodemedia.nodemediaclient;

import android.os.Bundle;

import org.apache.cordova.CordovaActivity;

/**
 * Created by Administrator on 2015/10/11.
 */
public class ChongZhiActivity extends CordovaActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadUrl("file:///android_asset/www/recharge.html");
    }
}
