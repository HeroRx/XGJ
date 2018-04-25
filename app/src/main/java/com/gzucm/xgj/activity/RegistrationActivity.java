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

import com.gzucm.xgj.domin.MyUser;
import com.gzucm.xgj.xgj_car.R;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;

import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/5/7.
 */
public class RegistrationActivity extends Activity {

    private EditText registra_et_user,registra_et_phonenum,registra_et_inputpassword,registra_et_confirmpassword,registra_et_verification_code;
    private Button registra_btn_getcode,registra_btn_registra;//获取验证码  注册按钮
    private ImageView registra_img_back;//后退按钮

    String username,phonenum,password,confirmpassword,code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        findViews();
        onClick();
    }

    /**
     * 绑定控件
     */
    private void findViews() {
        registra_et_user = (EditText) findViewById(R.id.registra_et_user);
        registra_et_phonenum = (EditText) findViewById(R.id.registra_et_phonenum);
        registra_et_inputpassword = (EditText) findViewById(R.id.registra_et_inputpassword);
        registra_et_confirmpassword = (EditText) findViewById(R.id.registra_et_confirmpassword);
        registra_et_verification_code = (EditText) findViewById(R.id.registra_et_verification_code);
        registra_btn_getcode = (Button) findViewById(R.id.registra_btn_getcode);
        registra_btn_registra = (Button) findViewById(R.id.registra_btn_registra);
        registra_img_back = (ImageView) findViewById(R.id.registra_img_back);
    }

    /**
     * 控件的点击事件
     */
    private void onClick(){
        registra_btn_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonenum = registra_et_phonenum.getText().toString().trim();
                if(!TextUtils.isEmpty(phonenum)){
//                    BmobSMS.requestSMSCode(RegistrationActivity.this, phonenum, "小公举", new RequestSMSCodeListener() {
//                        @Override
//                        public void done(Integer integer, BmobException e) {
//                            if (e == null){
//                                toast("验证码已发送"+integer);
//                            }else {
//                                toast("发送失败"+integer);
//                            }
//                        }
//                    });
                    BmobSMS.requestSMSCode(phonenum, "小公举", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (e == null){
                               toast("验证码已发送"+integer);
                          }else {
                               toast("发送失败"+integer);
                          }
                        }
                    });
                }else {
                   toast("请输入手机号码！");
                }
            }
        });

        registra_btn_registra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = registra_et_user.getText().toString().trim();
                password = registra_et_inputpassword.getText().toString().trim();
                confirmpassword = registra_et_confirmpassword.getText().toString().trim();
                code = registra_et_verification_code.getText().toString().trim();
                if (password.equals(confirmpassword)&&!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(confirmpassword)&&!TextUtils.isEmpty(code)){
                    MyUser user = new MyUser();
                    user.setMobilePhoneNumber(phonenum);
                    user.setUsername(username);
                    user.setPassword(password);
//                    user.signOrLogin(code, new SaveListener() {
//
//                        @Override
//                        public void onSuccess() {
//                            toast("注册成功");
//
//                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
//                            finish();
//                        }
//
//                        @Override
//                        public void onFailure(int i, String s) {
//                            toast("错误码：" + i + ",错误原因：" + s);
//                        }
//                    });
                    user.signOrLogin(code, new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null){
                                toast("注册成功");
                                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                finish();
                            }else {
                                toast("失败:" + e.getMessage());
                            }
                        }
                    });
                }else {
                    toast("密码输入不相等！");
                }

            }
        });
        registra_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void toast(String content) {
        Toast.makeText(RegistrationActivity.this, content, Toast.LENGTH_LONG).show();
    }
}
