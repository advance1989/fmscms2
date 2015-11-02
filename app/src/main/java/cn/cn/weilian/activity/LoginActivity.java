package cn.cn.weilian.activity;

import android.os.Bundle;

import org.apache.cordova.CordovaActivity;

/**
 * Created by Administrator on 2015/10/15.
 */
public class LoginActivity extends CordovaActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadUrl("file:///android_asset/www/login.html");
    }
}
