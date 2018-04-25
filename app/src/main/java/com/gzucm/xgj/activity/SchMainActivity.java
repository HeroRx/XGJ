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

        //��ȡ�ؼ�
        initView();


        //��������
        initData();
    }

    /**
     * ��������
     */
    private void initData() {
        //�õ����ݹ�����intent����
        final Bundle b = getIntent().getExtras();
        String HeadSch = b.getString("name");
        String Sch = b.getString("name");
        String SchPhone = b.getString("phone");
        String SchLocation = b.getString("location");
        String SchScoreInt = String.valueOf(b.getInt("integer"));
        String SchScoreDec = String.valueOf(b.getInt("decimals"));


        //�����������ݸ���Ӧ�Ŀؼ�����
        mSighUp.setText("���μӻ�������ʻԱ������ѵ��ѧԱ�����ݹ������й�ѧϰ��ʻ���������йع涨������ͬ�Ļ������ڱ���׼���������ϣ�\n1����ѧ������Ա�����֤ԭ������ػ�������б��о�ס֤��ԭ��<һ����>����Сһ��׵���ڲ��մ�ͷ��12�š����̡�������Ƭ��Ϣ��ִ��\n2��ת��/���ݣ��κ���ؼ�ʻ֤���豾�˵�᯴峵��������ת��ҵ��,��Ҫ���ݵ��ڱ���ʱ���ṩ������Ч�ļ�ʻ֤��������ӡ����");
        mHeadSch.setText(HeadSch);
        mSch.setText(Sch);
        mSchPhone.setText("�绰��" + SchPhone);
        mSchLocation.setText("��ַ��"+SchLocation);
        mSchScoreInt.setText(SchScoreInt);
        mSchScoreDec.setText(SchScoreDec);

//        Bitmap bitmap = b.getParcelable("bitmap");
//        String url = b.getString("url");
//        Log.e("hhh","bb" + url);
//        imageLoader.DisplayImage(url,mSchIcon);

        //��bundle��ȡString��url,�����߳��д������ȡͼƬ��ת��Ϊbitmap
        new Thread(new Runnable() {
            @Override
            public void run() {
                //���ݱ���ͼƬ��url��ַ���õ�ͼƬ��Bitmap���ͣ�
                String url = b.getString("url");
                if(bitmap == null) {
                    bitmap = urlToBitmap(url);
                }
            }
        }).start();
        //�����߳�����ͼƬ
        mSchIcon.setImageBitmap(bitmap);
    }
    /**
     * ����URL��ȡBitmap
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
     * ��ȡ�ؼ�
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
