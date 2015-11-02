package com.weilian.fmscms;

 

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.cn.weilian.activity.MediaPlayerDemo_Video;
import cn.nodemedia.nodemediaclient.MyApplication;
import cn.nodemedia.nodemediaclient.ShowActivity;


public class Plugin_intent extends CordovaPlugin {


    public Plugin_intent() {
    }

    CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args,
                           CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;


        if (action.equals("intent")) {

            String message = args.getString(0);
            String eid=args.getString(1);

            this.function(message,eid);
            return true;
        } else if (action.equals("login")) {

            String userinfo = args.getString(0);
            JSONObject userinfoobj=new JSONObject(userinfo);
            SharedPreferences settings = cordova.getActivity().getSharedPreferences("login", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("uid", userinfoobj.getString("id"));
            editor.putString("ucuid", userinfoobj.getString("ucuid"));
            editor.putString("username", userinfoobj.getString("username"));
            editor.putString("nickname", userinfoobj.getString("nickname"));
            MyApplication myApplication= (MyApplication) cordova.getActivity().getApplication();
            myApplication.setUserinfo(userinfoobj);
            editor.commit();
            return true;
        }else if (action.equals("logout")) {
            SharedPreferences settings = cordova.getActivity().getSharedPreferences("login", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("uid","0");
            MyApplication myApplication= (MyApplication) cordova.getActivity().getApplication();
            myApplication.setUserinfo(null);
            editor.commit();
            return true;
        }
        return false;

    }

    private void function(String message,String eid) {

        Intent intent = new Intent(cordova.getActivity(), ShowActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("roomid",message);
        bundle.putString("eid", eid);
        intent.putExtras(bundle);

        cordova.getActivity().startActivity(intent);
        //cordova.getActivity().finish();
       // cordova.startActivityForResult((CordovaPlugin) this,intent, 200);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);

        callbackContext.success("back success");


    }
}
