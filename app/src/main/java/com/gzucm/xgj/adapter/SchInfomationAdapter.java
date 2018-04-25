package com.gzucm.xgj.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzucm.xgj.bean.Sch;
import com.gzucm.xgj.utils.lazyImageload.ImageLoader;
import com.gzucm.xgj.xgj_car.R;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/2/11 0011.
 */
public class SchInfomationAdapter extends BaseAdapter{

    private Context context;
    private List<Sch> schs;
    ViewHolder viewHolder;

    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;



    public SchInfomationAdapter(Context context, List<Sch> schs){
        this.context = context;
        this.schs = schs;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(context.getApplicationContext());
    }

    @Override
    public  View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null){
            BmobFile sch_image;
            String information;
            information = schs.get(position).getInformation();
            sch_image = schs.get(position).getsch_image();

            Log.e("ss","" + information);
            convertView = inflater.inflate(R.layout.list_information, null);//实例化一个布局文件
            viewHolder = new ViewHolder();
            viewHolder.sch_image = (ImageView)convertView.findViewById(R.id.info_iv);
            viewHolder.information = (TextView)convertView.findViewById(R.id.info_tv);
            convertView.setTag(viewHolder);

            //利用LazyImageView加载图片
            imageLoader.DisplayImage(schs.get(position).getsch_image().getFileUrl(),viewHolder.sch_image);
            viewHolder.information.setText(information);

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
        public ImageView sch_image;
        public TextView information;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return schs.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return schs.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
}

