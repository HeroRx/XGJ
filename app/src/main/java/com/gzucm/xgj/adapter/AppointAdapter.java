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

import com.gzucm.xgj.bean.Appointment;
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
 * Created by XQM on 2017/2/12.
 */

public class AppointAdapter extends BaseAdapter {

    private Context context;
    private List<Appointment> appointments;
    AppointAdapter.ViewHolder viewHolder;

    //lazyImageLoad
    private Activity activity;

    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;


    public AppointAdapter(Context context,List<Appointment> appointments){
        this.context = context;
        this.appointments = appointments;

    }
    public AppointAdapter(Activity a,List<Appointment> appointments){
        activity = a;
        this.appointments = appointments;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }
    public void addAll(List<Appointment> elem) {
        appointments.addAll(elem);
        notifyDataSetChanged();
    }

    @Override
    public  View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null){
            BmobFile img_touxiang;
            BmobFile img_appoint;
            String str_name;
            String str_time_count;
            str_name = appointments.get(position).getPeo_name();
            str_time_count = appointments.get(position).getTime_count();
            img_appoint = appointments.get(position).getImg_appoint();
            img_touxiang = appointments.get(position).getImg_touxiang();
            System.out.println(img_appoint.getFileUrl());
            System.out.println(img_touxiang.getFileUrl());
//            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, null);//实例化一个布局文件
            viewHolder = new AppointAdapter.ViewHolder();
            viewHolder.img_touxiang = (ImageView) convertView.findViewById(R.id.img_people);
            viewHolder.img_appoint = (ImageView) convertView.findViewById(R.id.img_appoint);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_time_count = (TextView) convertView.findViewById(R.id.tv_describe);
            convertView.setTag(viewHolder);


//            //不能直接在主线程中进行从网络端获取图片，而需要单独开一个子线程完成从网络端获取图片
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    //根据表中图片的url地址来得到图片（Bitmap类型）
//                    final Bitmap bitmap=getPicture(schs.get(position).getsch_image().getFileUrl());
//                    viewHolder.sch_image.post(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            viewHolder.sch_image.setImageBitmap(bitmap);//将图片放到视图中去
//                        }
//                    });
//                }
//            }).start();

            //利用LazyImageView加载图片
            imageLoader.DisplayImage(appointments.get(position).getImg_touxiang().getFileUrl(),viewHolder.img_touxiang);
            imageLoader.DisplayImage(appointments.get(position).getImg_appoint().getFileUrl(),viewHolder.img_appoint);
            viewHolder.tv_name.setText(str_name);
            viewHolder.tv_time_count.setText(str_time_count);

        }else {
            viewHolder = (AppointAdapter.ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    /**
     * 组件集合，对应list.xml中的控件
     */
    public final class ViewHolder {
        private ImageView img_touxiang;
        private ImageView img_appoint;
        private TextView tv_time_count;
        private TextView tv_name;
    }

    @Override
    public int getCount() {
        return appointments.size();
    }

    @Override
    public Object getItem(int position) {
        return appointments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    //根据图片的url地址得到图片
    public Bitmap getPicture(String path) {
        Bitmap bm = null;
        try {
            URL url = new URL(path);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            bm = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        bm = BitmapZoom.resizeImage(bm , 800, 800);//修改图片大小
        return bm;
    }
}
