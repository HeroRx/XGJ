package com.gzucm.xgj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gzucm.xgj.xgj_car.R;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


/**
 * 找回密码功能
 * Created by Administrator on 2016/5/8.
 */
public class FindpasswordActivity extends Activity {

    //初始化控件
    private EditText findpassword_et_phonenum,findpassword_et_verification_code;
    private Button findpassword_btn_getcode,findpassword_btn_next;
    private ImageView findpassword_img_back;
    String phonenum,code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpassword);
        findViews();
        onClick();
    }

    /**
     * 绑定控件
     */
    private void findViews() {
        findpassword_et_phonenum = (EditText) findViewById(R.id.findpassword_et_phonenum);
        findpassword_et_verification_code = (EditText) findViewById(R.id.findpassword_et_verification_code);
        findpassword_btn_getcode = (Button) findViewById(R.id.findpassword_btn_getcode);
        findpassword_btn_next = (Button) findViewById(R.id.findpassword_btn_next);
        findpassword_img_back = (ImageView) findViewById(R.id.findpassword_img_back);
    }

    /**
     * 控件点击事件
     */
    private void onClick(){
        findpassword_btn_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonenum = findpassword_et_phonenum.getText().toString().trim();
                if(!TextUtils.isEmpty(phonenum)){
                    BmobSMS.requestSMSCode(phonenum, "小公举", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (e == null){
                                toast("验证码已发送" + integer);
                            }else {
                                toast("发送失败" + integer);
                            }
                        }
                    });
                }else {
                    toast("请输入手机号码！");
                }
            }
        });

        findpassword_btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = findpassword_et_verification_code.getText().toString().trim();
                phonenum = findpassword_et_phonenum.getText().toString().trim();
                if (TextUtils.isEmpty(phonenum)) {
                    toast("请输入手机号");
                }else if (TextUtils.isEmpty(code)){
                    toast("请输入验证码");
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("phonenum", phonenum);
                    bundle.putString("code", code);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(FindpasswordActivity.this, ResetActivity.class);
                    startActivity(intent);
                }
            }
        });
        findpassword_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void toast(String content) {
        Toast.makeText(FindpasswordActivity.this, content, Toast.LENGTH_LONG).show();
    }
}
