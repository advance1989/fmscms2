

package cn.nodemedia.nodemediaclient;

import android.os.Bundle;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaActivity;

public class MainActivity extends CordovaActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Set by <content src="index.html" /> in config.xml
        // Set by <content src="index.html" /> in config.xml file:///
        ///loadUrl("file:///android_asset/jubao.html");
        loadUrl(Config.getStartUrl());
    }
}
