package cn.nodemedia.nodemediaclient;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2015/10/8.
 */
public class GiftListGridView extends BaseAdapter {
    private GridView giftlistview;
    private List<Giftinfo> giftlist;
    private Context context;
    private AsyncImageTask imageTask=null;
    private MyApplication myApplication;


    public GiftListGridView(GridView giftlistview,List<Giftinfo> giftlist,Context context) {
        this.giftlist=giftlist;
        this.giftlistview = giftlistview;
        this.context=context;
        this.myApplication= (MyApplication) context.getApplicationContext();
        if(imageTask==null){
            this.imageTask=new AsyncImageTask();
        }

    }

    @Override
    public int getCount() {
        return giftlist.size();
    }

    @Override
    public Object getItem(int i) {
        return giftlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder=new ViewHolder();
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        if(convertView ==null){
            convertView=  layoutInflater.inflate(R.layout.gift_item, null);

            holder.gif_imageView= (ImageView) convertView.findViewById(R.id.gif_imageView);
            holder.gift_text= (TextView) convertView.findViewById(R.id.gift_text);

            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.gif_imageView.setTag(position);
        holder.gift_text.setText(giftlist.get(position).getPrice()+"秀币");
        Giftinfo giftList=giftlist.get(position);
        Drawable drawable=imageTask.loadImage(position, myApplication.getUrl()+giftlist.get(position).getIcon(), new AsyncImageTask.ImageCallback() {
            @Override
            public void imageLoaded(Drawable image, int id) {
                if(image!=null){
                    ImageView mainBg= (ImageView) giftlistview.findViewWithTag(id);
                    if(mainBg!=null){
                        mainBg.setImageDrawable(image);
                    }
                }
            }
        });

        return convertView;
    }
    class ViewHolder {
        ImageView gif_imageView;
        TextView gift_text;

    }


}
