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
    private Button registra_btn_getcode,registra_btn_registra;//��ȡ��֤��  ע�ᰴť
    private ImageView registra_img_back;//���˰�ť

    String username,phonenum,password,confirmpassword,code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        findViews();
        onClick();
    }

    /**
     * �󶨿ؼ�
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
     * �ؼ��ĵ���¼�
     */
    private void onClick(){
        registra_btn_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonenum = registra_et_phonenum.getText().toString().trim();
                if(!TextUtils.isEmpty(phonenum)){
//                    BmobSMS.requestSMSCode(RegistrationActivity.this, phonenum, "С����", new RequestSMSCodeListener() {
//                        @Override
//                        public void done(Integer integer, BmobException e) {
//                            if (e == null){
//                                toast("��֤���ѷ���"+integer);
//                            }else {
//                                toast("����ʧ��"+integer);
//                            }
//                        }
//                    });
                    BmobSMS.requestSMSCode(phonenum, "С����", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (e == null){
                               toast("��֤���ѷ���"+integer);
                          }else {
                               toast("����ʧ��"+integer);
                          }
                        }
                    });
                }else {
                   toast("�������ֻ����룡");
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
//                            toast("ע��ɹ�");
//
//                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
//                            finish();
//                        }
//
//                        @Override
//                        public void onFailure(int i, String s) {
//                            toast("�����룺" + i + ",����ԭ��" + s);
//                        }
//                    });
                    user.signOrLogin(code, new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null){
                                toast("ע��ɹ�");
                                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                finish();
                            }else {
                                toast("ʧ��:" + e.getMessage());
                            }
                        }
                    });
                }else {
                    toast("�������벻��ȣ�");
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
