package com.gzucm.xgj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gzucm.xgj.adapter.AppointAdapter;
import com.gzucm.xgj.bean.Appointment;
import com.gzucm.xgj.utils.T;
import com.gzucm.xgj.xgj_car.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static android.view.View.GONE;

/**
 * Created by XQM on 2017/2/12.
 */

public class AppointActivity extends FragmentActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    private Button search;
    private Button goback;
    private ListView listview;
    private TextView arrow_down;
    private List<Appointment> datalist;
    private ImageView img_back,img_share;
    private RelativeLayout rl_progress;
    private ListView listView;
    private TextView tv_no_data;
    private LinearLayout layout_no;

    //ShareFragment
    private Fragment shareFragment;
    private FragmentManager mfragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint);
        initView();
        initData();
        initClickListener();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_share = (ImageView) findViewById(R.id.img_share);
        rl_progress = (RelativeLayout) findViewById(R.id.progress);
        listView = (ListView) findViewById(R.id.lv_appoint);
        tv_no_data = (TextView) findViewById(R.id.tv_no);
        layout_no = (LinearLayout) findViewById(R.id.layout_no);
    }

    private void initData() {
        mfragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mfragmentManager.beginTransaction();

        showView();
        layout_no.setVisibility(GONE);
        BmobQuery<Appointment> bmobQuery = new BmobQuery<Appointment>();
        bmobQuery.order("-Created");
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        bmobQuery.findObjects(new FindListener<Appointment>() {
            @Override
            public void done(List<Appointment> list, BmobException e) {
                if (e == null){
                    AppointAdapter appointAdapter = new AppointAdapter(AppointActivity.this,list);
//                    appointAdapter.notifyDataSetChanged();
//                    T.showShort(AppointActivity.this,"查询成功");
//                    System.out.println("查询成功"+list.get(0).getImg_touxiang()+list.get(0).getTime_count()+list.get(0).getPeo_name()+
//                            list.get(0).getImg_appoint());
                    rl_progress.setVisibility(GONE);
                    appointAdapter.addAll(list);
                    listView.setAdapter(appointAdapter);
                }else {
                    T.showShort(AppointActivity.this, "查询失败1");
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    showErrorView();
                }

            }
            private void showErrorView() {
                listView.setVisibility(GONE);
                rl_progress.setVisibility(GONE);
                layout_no.setVisibility(View.VISIBLE);
                tv_no_data.setText("暂时没有数据");
            }
        });
    }
    private void showView() {
        listView.setVisibility(View.VISIBLE);
    }


    private void initClickListener() {
        img_back.setOnClickListener(this);
        img_share.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == img_back){
            finish();
        }
        if (view == img_share){
//            Intent intent = new Intent(AppointActivity.this, ShareFragment.class);
//            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(AppointActivity.this,MyAppointActivity.class);
        startActivity(intent);
    }
}
