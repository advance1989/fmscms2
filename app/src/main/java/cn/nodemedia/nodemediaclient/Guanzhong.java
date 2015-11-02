package cn.nodemedia.nodemediaclient;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Administrator on 2015/10/6.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Guanzhong extends Fragment {
    private List<Userinfo> guanzhong;
    private ListView userlistview;
    public Guanzhong(List<Userinfo> guanzhong) {
        this.guanzhong=guanzhong;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.guanzhong,null);
        userlistview= (ListView) v.findViewById(R.id.guanzhonglist);
        UserListListView userListListView=new UserListListView(userlistview,guanzhong,getActivity());
        userlistview.setAdapter(userListListView);
        return v;
    }
}
