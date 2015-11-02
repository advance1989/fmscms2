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
 * Created by Administrator on 2015/10/6.
 */
class MessageListView extends BaseAdapter {

    private List<Message> lists;
    private Context context;
    private AsyncImageTask asyncImageTask=null;
    private ListView msglistview;
    private MyApplication myApplication;
   private String url="http://app.fmscms.com";
    //  private String url="http://zhibo.1198.com";
    public MessageListView(ListView msglistview,List<Message> lists,Context context){
        this.lists=lists;
        this.context=context;
        this.msglistview=msglistview;
//        this.myApplication= (MyApplication) context.getApplicationContext();
        if(asyncImageTask==null){
            this.asyncImageTask=new AsyncImageTask();
        }


    };
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=new ViewHolder();
        LayoutInflater inflater=LayoutInflater.from(context);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.message,null);
            holder.textView= (TextView) convertView.findViewById(R.id.text_view);
            holder.imageView= (ImageView) convertView.findViewById(R.id.msg_gificon);
            holder.username= (TextView) convertView.findViewById(R.id.name_view);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.imageView.setTag(position);
        holder.textView.setText(lists.get(position).getmsg());
        holder.username.setText(lists.get(position).getUsername()+":");
        if(lists.get(position).getGiftIcon()!=null){

            Drawable drawable=asyncImageTask.loadImage(position,url+lists.get(position).getGiftIcon(), new AsyncImageTask.ImageCallback() {
                @Override
                public void imageLoaded(Drawable image, int id) {

                    if(image!=null){
                        ImageView gifticon= (ImageView) msglistview.findViewWithTag(id);
                        if(gifticon!=null&&gifticon.getTag().equals(position)){

                            gifticon.setImageDrawable(image);

                        }
                        //finalHolder.imageView.setImageDrawable(image);
                    }
                }
            });
            return convertView;

        }else{
            return convertView;
        }



    }
    class ViewHolder {
        ImageView imageView;
        TextView username;
        TextView textView;

    }
    public void setListmsg(List<Message> lists){
        this.lists=lists;
    }
}