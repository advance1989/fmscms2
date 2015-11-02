package cn.nodemedia.nodemediaclient;

import android.graphics.drawable.Drawable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import android.os.Handler;
import android.os.Message;
/**
 * Created by Administrator on 2015/10/7.
 */
public class AsyncImageTask {
    private Map<String,Drawable> imageMap=null;
    public AsyncImageTask(){
        super();
        if(imageMap==null){
            imageMap=new HashMap<String, Drawable>();
        }


    }
    public  Drawable loadImage(final  int id,final  String imageUrl,final ImageCallback callback ){
        if(imageMap.containsKey(imageUrl)){
            Drawable drawable=imageMap.get(imageUrl);
            if(drawable!=null){
                return drawable;
            }
        }
        final Handler handler=new Handler() {
            @Override
            public void handleMessage(Message message) {
                callback.imageLoaded((Drawable) message.obj, id);
            }
        };
        new Thread(){
            public void run(){
                super.run();
                Drawable drawable=AsyncImageTask.loadImageByUrl(imageUrl);
                imageMap.put(imageUrl,drawable);
                Message message=handler.obtainMessage(0,drawable);
                handler.sendMessage(message);
            }
        }.start();
        return null;

    }
    public static Drawable loadImageByUrl(String imageUrl){
        URL url=null;
        InputStream inputStream=null;
        try {
            url=new URL(imageUrl);
            inputStream= (InputStream) url.getContent();
            return Drawable.createFromStream(inputStream,"src");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
    public interface ImageCallback{
        public void imageLoaded(Drawable image,int id);
    }

}
