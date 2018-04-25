package com.gzucm.xgj.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzucm.xgj.bean.Sch;
import com.gzucm.xgj.utils.lazyImageload.ImageLoader;
import com.gzucm.xgj.xgj_car.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/2/11 0011.
 */
public class SchAdapter extends BaseAdapter{

    private Context context;
    private List<Sch> schs;
    ViewHolder viewHolder;

    //lazyImageLoad
    private Activity activity;

    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;


    public SchAdapter(Context context,List<Sch> schs){
        this.context = context;
        this.schs = schs;

    }
    public SchAdapter(Activity a,List<Sch> schs){
        activity = a;
        this.schs = schs;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    @Override
    public  View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null){
            BmobFile sch_image;
            String sch_name;
            String sch_location;
            String sch_phone;
            sch_name = schs.get(position).getSch();
            sch_location = schs.get(position).getSch_location();
            sch_phone = schs.get(position).getSch_phone();
            sch_image = schs.get(position).getsch_image();
            System.out.println(sch_image.getFileUrl());
//            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_sign_up, null);//实例化一个布局文件
            viewHolder = new ViewHolder();
            viewHolder.sch_image = (ImageView)convertView.findViewById(R.id.image_sch);
            viewHolder.sch_name = (TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.sch_location = (TextView)convertView.findViewById(R.id.tv_location);
            viewHolder.sch_phone = (TextView)convertView.findViewById(R.id.tv_phone);
            convertView.setTag(viewHolder);

            //利用LazyImageView加载图片
            imageLoader.DisplayImage(schs.get(position).getsch_image().getFileUrl(),viewHolder.sch_image);
            viewHolder.sch_name.setText(sch_name);
            viewHolder.sch_location.setText("所在区域：" + sch_location);
            viewHolder.sch_phone.setText("咨询电话：" + sch_phone);

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
        public TextView sch_name;
        public TextView sch_location;
        public TextView sch_phone;
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

    //根据图片的url地址得到图片
    public Bitmap getPicture(String path){
        Bitmap bm=null;
        try{
            URL url=new URL(path);
            URLConnection connection=url.openConnection();
            connection.connect();
            InputStream inputStream=connection.getInputStream();
            bm= BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        bm = BitmapZoom.resizeImage(bm , 800, 800);//修改图片大小
        return  bm;
    }


}

