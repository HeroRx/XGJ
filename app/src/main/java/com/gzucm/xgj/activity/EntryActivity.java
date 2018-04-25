package com.gzucm.xgj.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.gzucm.xgj.domin.MyUser;
import com.gzucm.xgj.utils.T;
import com.gzucm.xgj.xgj_car.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 *��ʼ��Bmob,������ת����¼��ע����棬���Ҽ���û��Ƿ����
 * Created by Administrator on 2016/5/7.
 */
public class EntryActivity extends Activity {
//    private final static String APP_ID = "d63c6ef692d735d39691f785b7f3279f";
    private final static String APP_ID = "d1e5f3d46d879fca970651a5b377219c";
    private ImageView img_entry_login,img_entry_registra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        //��ʼ��Bmob��ID
        Bmob.initialize(EntryActivity.this,APP_ID);
        img_entry_login = (ImageView) findViewById(R.id.img_entry_login);
        img_entry_registra = (ImageView) findViewById(R.id.img_entry_registra);
        //����û��Ƿ����
        currentUser();

        img_entry_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EntryActivity.this,LoginActivity.class));
            }
        });

        img_entry_registra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EntryActivity.this,RegistrationActivity.class));
            }
        });
    }

    /**
     * ����û��Ƿ����
     */
    private void currentUser(){
        MyUser userInfo =  BmobUser.getCurrentUser(MyUser.class);
        if (userInfo != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(EntryActivity.this);
            builder.setMessage("��⵽�û�����").setTitle("����").setPositiveButton("��", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(EntryActivity.this,MainActivity.class));
                    finish();
                }
            }).setNegativeButton("��", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();
            AlertDialog alertDialog = builder.create();
            T.showLong(EntryActivity.this,"�û�����");
//            Toast.makeText(EntryActivity.this, "�û�����", Toast.LENGTH_LONG).show();
        }
        else
        {
        }
    }
}
