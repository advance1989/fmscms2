package cn.nodemedia.nodemediaclient;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2015/10/7.
 */
public class UserListListView extends BaseAdapter {
    private ListView listView;
    private List<Userinfo> infolist;
    private Context context;
    private AsyncImageTask imageTask;
    private MyApplication myApplication;
    public UserListListView(ListView listView,List<Userinfo> infolist,Context context) {
        this.listView=listView;
        this.infolist=infolist;
        this.context=context;
        this.myApplication= (MyApplication) context.getApplicationContext();
        this.imageTask=new AsyncImageTask();
    }

    @Override
    public int getCount() {
        return infolist.size();
    }

    @Override
    public Object getItem(int position) {
        return infolist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        if(convertView ==null){
            convertView=  layoutInflater.inflate(R.layout.userinfo, null);
            holder.user_text= (TextView) convertView.findViewById(R.id.userinfo_text);
            holder. imageView= (ImageView) convertView.findViewById(R.id.userinfo_image);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.imageView.setTag(position);
        Userinfo  userinfo=infolist.get(position);
        Drawable drawable=imageTask.loadImage(position, myApplication.getUrl()+"/passport/avatar.php?uid="+infolist.get(position).getimgurl()+"&amp;size=middle", new AsyncImageTask.ImageCallback() {
            @Override
            public void imageLoaded(Drawable image, int id) {
                if(image!=null){
                    ImageView mainBg= (ImageView) listView.findViewWithTag(id);
                    if(mainBg!=null){
                        mainBg.setImageDrawable(image);
                    }
                }
            }
        });
        holder.user_text.setText(infolist.get(position).getUsername());
        //"http://b.51miao.com.cn/passport/avatar.php?uid=0&amp;size=middle"
        return convertView;
    }
    class ViewHolder {
        ImageView imageView;
        TextView user_text;

    }

}
