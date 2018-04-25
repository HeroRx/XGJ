package com.gzucm.xgj.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzucm.xgj.utils.lazyImageload.ImageLoader;
import com.gzucm.xgj.xgj_car.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/2/10 0010.
 */
public class SchMainActivity extends Activity implements View.OnClickListener{

    private TextView mHeadSch;
    private TextView mSch;
    private TextView mSchPhone;
    private TextView mSchScoreInt;
    private TextView mSchScoreDec;
    private TextView mSighUp;
    private TextView mSchLocation;
    private Button back;
    private ImageView mSchIcon;
    ImageLoader imageLoader;
    public static Bitmap bitmap;

//    private ImageView mSchIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sch_home);

        //获取控件
        initView();


        //设置数据
        initData();
    }

    /**
     * 设置数据
     */
    private void initData() {
        //得到传递过来的intent参数
        final Bundle b = getIntent().getExtras();
        String HeadSch = b.getString("name");
        String Sch = b.getString("name");
        String SchPhone = b.getString("phone");
        String SchLocation = b.getString("location");
        String SchScoreInt = String.valueOf(b.getInt("integer"));
        String SchScoreDec = String.valueOf(b.getInt("decimals"));


        //以下设置数据给对应的控件即可
        mSighUp.setText("凡参加机动车驾驶员报名培训的学员，根据公安部有关学习驾驶机动车的有关规定，按不同的户籍所在必须准备如下资料：\n1、初学申请人员：身份证原件（异地户籍需持有本市居住证件原件<一年期>）、小一寸白底免冠彩照大头相12张、光盘、驾照相片信息回执。\n2、转籍/增驾：任何外地驾驶证都需本人到岑村车管所办理转入业务,需要增驾的在报名时需提供本地有效的驾驶证正副本复印件。");
        mHeadSch.setText(HeadSch);
        mSch.setText(Sch);
        mSchPhone.setText("电话：" + SchPhone);
        mSchLocation.setText("地址："+SchLocation);
        mSchScoreInt.setText(SchScoreInt);
        mSchScoreDec.setText(SchScoreDec);

//        Bitmap bitmap = b.getParcelable("bitmap");
//        String url = b.getString("url");
//        Log.e("hhh","bb" + url);
//        imageLoader.DisplayImage(url,mSchIcon);

        //从bundle获取String的url,在子线程中从网络获取图片，转换为bitmap
        new Thread(new Runnable() {
            @Override
            public void run() {
                //根据表中图片的url地址来得到图片（Bitmap类型）
                String url = b.getString("url");
                if(bitmap == null) {
                    bitmap = urlToBitmap(url);
                }
            }
        }).start();
        //在主线程设置图片
        mSchIcon.setImageBitmap(bitmap);
    }
    /**
     * 根据URL获取Bitmap
     * */
    public Bitmap urlToBitmap(String url){

        Bitmap bitmap=null;
        URL myUrl;
        try {
            myUrl=new URL(url);
            HttpURLConnection conn=(HttpURLConnection)myUrl.openConnection();
            conn.setConnectTimeout(50000);
            conn.connect();
            InputStream is=conn.getInputStream();
            bitmap= BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 获取控件
     */
    private void initView() {
        mHeadSch=(TextView)findViewById(R.id.tv_head_sch);
        mSch=(TextView)findViewById(R.id.tv_sch);
        mSchPhone=(TextView)findViewById(R.id.tv_sch_phone);
        mSchScoreInt=(TextView)findViewById(R.id.tv_sch_score_integer);
        mSchScoreDec=(TextView)findViewById(R.id.tv_sch_score_decimals);
        mSighUp=(TextView)findViewById(R.id.tv_sigh_up);
        mSchLocation=(TextView)findViewById(R.id.tv_sch_detail_location);
        back=(Button) findViewById(R.id.back);
        mSchIcon=(ImageView) findViewById(R.id.sch_icon);

        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
