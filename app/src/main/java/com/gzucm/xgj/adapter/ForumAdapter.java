package com.gzucm.xgj.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gzucm.xgj.bean.Forum;
import com.gzucm.xgj.utils.lazyImageload.ImageLoader;
import com.gzucm.xgj.xgj_car.R;

import java.util.List;

/**
 * Created by Administrator on 2017/2/11 0011.
 */
public class ForumAdapter extends BaseAdapter{

    private Context context;
    private List<Forum> users;
    ViewHolder viewHolder;
    Activity activity;

    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;


    public ForumAdapter(Context context,List<Forum> users){
        this.context = context;
        this.users = users;

    }
    public ForumAdapter(Activity a, List<Forum> users){
        activity = a;
        this.users = users;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    @Override
    public  View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null){
            String username;
            String user_forum;
            String updatedAt;
            username = users.get(position).getName();
            user_forum = users.get(position).getFornum_remark();
            updatedAt = users.get(position).getUpdatedAt();

            convertView = inflater.inflate(R.layout.lsit_forum, null);//实例化一个布局文件
            viewHolder = new ViewHolder();
            viewHolder.username = (TextView) convertView.findViewById(R.id.forum_tv_user);
            viewHolder.user_forum = (TextView)convertView.findViewById(R.id.forum_tv_remark);
            viewHolder.updatedAt = (TextView)convertView.findViewById(R.id.forum_tv_updatetime);
            convertView.setTag(viewHolder);


            viewHolder.username.setText(username);
            viewHolder.user_forum.setText(user_forum);
            viewHolder.updatedAt.setText(updatedAt);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    /**
     * 组件集合，对应list.xml中的控件
     * @author Administrator
     */
    public final class ViewHolder{
        public TextView username;
        public TextView user_forum;
        public TextView updatedAt;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
}

