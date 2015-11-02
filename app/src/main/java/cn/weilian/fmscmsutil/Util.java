package cn.weilian.fmscmsutil;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Administrator on 2015/10/14.
 */
public class Util  {


    public static  String  MyHttprequest(String url){
        HttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);
        String res="";
        try {
            HttpResponse httpResponse=httpClient.execute(httpGet);
            HttpEntity httpEntity=httpResponse.getEntity();
            if(httpEntity!=null){

                res= EntityUtils.toString(httpEntity);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  res;
    }
    public static String MygetSharedPreferences(Context context,String filename,String key,String defaultval){
        SharedPreferences sharedPreferences=context.getSharedPreferences(filename, 0);
        return sharedPreferences.getString(key,defaultval);

    }
    public static void MysetSharedPreferences(Context context,String filename,String key,String value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(filename, 0);
        sharedPreferences.edit().putString(key,value);
    }

}
