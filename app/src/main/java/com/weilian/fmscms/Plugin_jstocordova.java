package com.weilian.fmscms;

 

import android.content.Intent;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import cn.nodemedia.nodemediaclient.ShowActivity;


public class Plugin_jstocordova extends CordovaPlugin {


    public Plugin_jstocordova() {
    }

    CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args,
                           CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        Log.i("123", action);

        if (action.equals("jstocordova")) {

            String message = args.getString(0);
            this.function(message);
            return true;
        }
        return false;

    }

    private void function(String message) {

        Log.i("123", cordova.getActivity().toString());
        Intent intent = new Intent(cordova.getActivity(), ShowActivity.class);

        cordova.startActivityForResult((CordovaPlugin) this,intent, 200);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);

        callbackContext.success("back success");


    }
}
