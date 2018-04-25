package com.gzucm.xgj.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.gzucm.xgj.adapter.SchAdapter;
import com.gzucm.xgj.bean.Sch;
import com.gzucm.xgj.xgj_car.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by Administrator on 2017/2/11 0011.
 */
public class SignUpActivity extends Activity implements View.OnClickListener{

    private Button search;
    private Button goback;

    private RadioButton default_order;
    private RadioButton descend_order;

    private ListView listview;
    private TextView arrow_down;

    public Sch schs;
//    List<Sch> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigh_up);

        //设置箭头大小
        setArrow();
        //初始化
        init();

    }

    /**
     * 设置箭头大小
     */
    private void setArrow() {
        arrow_down = (TextView)findViewById(R.id.tv_descend_order);
        Drawable nav_up=getResources().getDrawable(R.mipmap.arroe_down);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth()/4, nav_up.getMinimumHeight()/4);
        arrow_down.setCompoundDrawables(null, null, nav_up, null);
    }


    /**
     * 初始化View和数据
     */
    private void init(){
        search=(Button) this.findViewById(R.id.btn_search);
        goback=(Button)this.findViewById(R.id.btn_backward);
        default_order=(RadioButton) this.findViewById(R.id.tv_default_order);
        descend_order=(RadioButton) this.findViewById(R.id.tv_descend_order);
        goback.setOnClickListener(this);
        search.setOnClickListener(this);
        default_order.setOnClickListener(this);
        descend_order.setOnClickListener(this);
        listview = (ListView)findViewById(R.id.driver_sch_lv);

        //默认list界面
        loadDefault();
    }




    /**
     * 默认显示页面
     */
    private void loadDefault() {
        BmobQuery<Sch> bmobQuery = new BmobQuery<Sch>();
        bmobQuery.setLimit(1000);
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        bmobQuery.findObjects(new FindListener<Sch>() {
            @Override
            public void done(final List<Sch> list, BmobException e) {

                if (e == null){
                    SchAdapter schAdapter = new SchAdapter(SignUpActivity.this,list);
                    schAdapter.notifyDataSetChanged();
                    System.out.println("查询成功"+list.get(0).getSch()+list.get(0).getSch_location()+
                            list.get(0).getSch_phone()+list.get(0).getSch_location_detail()+
                            list.get(0).getSch_score_decimals() +list.get(0).getSch_score_integer()

                    );

                    System.out.println(list.get(0).getSch_icon().getFileUrl());
                    //跳转item
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                            final Intent schInfo = new Intent(SignUpActivity.this, SchMainActivity.class);
                            final Bundle b = new Bundle();
                            b.putString("name",list.get(i).getSch());
                            b.putString("phone",list.get(i).getSch_phone());
                            b.putString("location",list.get(i).getSch_location_detail());
                            b.putInt("decimals",list.get(i).getSch_score_decimals());
                            b.putInt("integer",list.get(i).getSch_score_integer());
                            b.putString("url",list.get(i).getSch_icon().getFileUrl());
                            Log.e("jj", String.valueOf(b));
                            schInfo.putExtras(b);
                            startActivity(schInfo);
                        }
                    });
                    listview.setAdapter(schAdapter);
                }

            }
        });
    }

    /**
     * 根据URL获取Bitmap
     * */
    public Bitmap urlToBitmap(final String url){

        Bitmap bitmap=null;
        URL myUrl;
        try {
            myUrl=new URL(url);
            HttpURLConnection conn=(HttpURLConnection)myUrl.openConnection();
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
     * 升序排序显示页面
     */
    public void loadDescend(){
        BmobQuery<Sch> bmobQuery = new BmobQuery<Sch>();
        //设置
        bmobQuery.order("schPrice");
        bmobQuery.setLimit(1000);
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        bmobQuery.findObjects(new FindListener<Sch>() {
            @Override
            public void done(final List<Sch> list, BmobException e) {
                if (e == null){
                    SchAdapter schAdapter = new SchAdapter(SignUpActivity.this,list);
                    schAdapter.notifyDataSetChanged();
                    System.out.println("查询成功"+list.get(0).getSch()+list.get(0).getSch_location()+
                            list.get(0).getSch_phone()+list.get(0).getsch_image());
                    //跳转item
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent schInfo = new Intent(SignUpActivity.this, SchMainActivity.class);
                            Bundle b = new Bundle();
                            b.putString("name",list.get(i).getSch());
                            b.putString("phone",list.get(i).getSch_phone());
                            b.putString("location",list.get(i).getSch_location_detail());
                            b.putInt("decimals",list.get(i).getSch_score_decimals());
                            b.putInt("integer",list.get(i).getSch_score_integer());
                            schInfo.putExtras(b);
                            startActivity(schInfo);
                        }
                    });
                    listview.setAdapter(schAdapter);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_search:
                break;
            case R.id.btn_backward:
                finish();
                break;
            case R.id.tv_default_order:
                loadDefault();
                break;
            case R.id.tv_descend_order:
                loadDescend();
                break;
            default:
                break;
        }

    }
}

