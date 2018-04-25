package com.gzucm.xgj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzucm.xgj.xgj_car.R;

import java.util.List;
import java.util.Map;

/**
 * Created by XQM on 2017/2/12.
 */

public class AppointListViewAdap extends BaseAdapter{
    private List<Map<String,Object>> datas;
    private Context context;
//    private ListView MylistView;

    private AppointListViewAdap(Context context,List<Map<String,Object>> datas){
        this.context = context;
        this.datas = datas;
    }
    /**
    *组件集合，对应list.xml中的控件
    * */
    public final class ViewHolder{
        private ImageView img_touxiang;
        private ImageView img_appoint;
        private TextView tv_time_count;
        private TextView tv_name;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.list_item,null);
            viewHolder.img_touxiang = (ImageView) view.findViewById(R.id.img_people);
            viewHolder.img_appoint = (ImageView) view.findViewById(R.id.img_appoint);
            viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_title);
            viewHolder.tv_time_count = (TextView) view.findViewById(R.id.tv_describe);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //绑定数据
        viewHolder.img_touxiang.setBackgroundResource((Integer) datas.get(position).get("img_touxiang"));
        viewHolder.img_appoint.setBackgroundResource((Integer) datas.get(position).get("img_appoint"));
        viewHolder.tv_name.setText((String) datas.get(position).get("tv_name"));
        viewHolder.tv_time_count.setText((String) datas.get(position).get("tv_time_count"));
        return view;
    }
}
