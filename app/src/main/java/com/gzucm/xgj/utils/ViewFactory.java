package com.gzucm.xgj.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.gzucm.xgj.xgj_car.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2016/7/19.
 */
public class ViewFactory {

    /**
     * 获取ImageView视图的同时加载显示url
     *
     * @param context
     * @return
     */
    public static ImageView getImageView(Context context, String url) {
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(
                R.layout.view_banner, null);
        ImageLoader.getInstance().displayImage(url, imageView);
        return imageView;
    }
}
