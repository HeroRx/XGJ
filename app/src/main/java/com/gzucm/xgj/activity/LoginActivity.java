package com.gzucm.xgj.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gzucm.xgj.domin.MyUser;
import com.gzucm.xgj.xgj_car.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;


/**
 * Created by Administrator on 2016/5/7.
 */
public class LoginActivity extends Activity {
    private EditText login_et_user,login_et_password;
    private ImageView login_img_visible,login_img_forgetpassword;
    private Button login_btn_login;
    String username,password;
    private boolean sign = false;


    public static final int MESSAGE_FAIL = 1;
    public static final int MESSAGE_SUCCEED = 2;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        onClick();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_SUCCEED:
                    progressDialog.dismiss();
                    toast("登录成功");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    break;
                case MESSAGE_FAIL:
                    progressDialog.dismiss();
                    toast("用户名密码错误");
                    break;
            }
        }
    };
    /**
     * 绑定控件
     */
    private void findViews() {
        login_et_user = (EditText) findViewById(R.id.login_et_user);
        login_et_password = (EditText) findViewById(R.id.login_et_password);
        login_btn_login = (Button) findViewById(R.id.login_btn_login);
        login_img_visible = (ImageView) findViewById(R.id.login_img_visible);
        login_img_forgetpassword = (ImageView) findViewById(R.id.login_img_forgetpassword);
    }

    /**
     * 控件点击事件
     */
    private void onClick(){
         login_btn_login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 username = login_et_user.getText().toString().trim();
                 password = login_et_password.getText().toString().trim();
                 if (TextUtils.isEmpty(username)) {
                     toast("请输入用户名或手机号");
                 } else if (TextUtils.isEmpty(password)) {
                     toast("请输入密码");
                 }else {
                     progressDialog = ProgressDialog.show(LoginActivity.this, "", "正在登录,请稍候！");
                     new Thread(new Runnable() {
                         @Override
                         public void run() {
                             MyUser.loginByAccount(username, password, new LogInListener<MyUser>() {
                                 @Override
                                 public void done(MyUser myUser, BmobException e) {
                                     if (myUser != null) {
                                         Message message = new Message();
                                         message.what = MESSAGE_SUCCEED;
                                         handler.sendMessage(message);
                                     } else {
                                         Message message = new Message();
                                         message.what = MESSAGE_FAIL;
                                         handler.sendMessage(message);
                                     }
                                 }
                             });
                         }
                     }).start();

                 }
             }
         });

        //设置密码可见或不可见
        login_img_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sign){
                    login_et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    login_img_visible.setImageResource(R.mipmap.img_password_invisible);
                }else {
                    login_et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    login_img_visible.setImageResource(R.mipmap.img_password_visible);
                }
                sign = !sign;
                login_et_password.postInvalidate();
            }
        });

        login_img_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,FindpasswordActivity.class));
            }
        });
    }
    private void toast(String content) {
        Toast.makeText(LoginActivity.this, content, Toast.LENGTH_LONG).show();
    }
}
