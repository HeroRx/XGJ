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

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by Administrator on 2016/5/8.
 */
public class ResetActivity extends Activity {
    private EditText resetpassword_et_inputpassword,resetpassword_et_comfirmpassword;
    private Button resetpassword_btn_finish;
//    private ImageView resetpassword_img_back;
    String reset_phonenum,reset_code,new_password,confirm_newpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        findViews();
        onClick();
    }

    /**
     * 绑定控件
     */
    private void findViews() {
        resetpassword_et_inputpassword = (EditText) findViewById(R.id.resetpassword_et_inputpassword);
        resetpassword_et_comfirmpassword = (EditText) findViewById(R.id.resetpassword_et_comfirmpassword);
        resetpassword_btn_finish = (Button) findViewById(R.id.resetpassword_btn_finish);
//        resetpassword_img_back = (ImageView) findViewById(R.id.resetpassword_img_back);
    }
    private void onClick(){
        resetpassword_btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_password = resetpassword_et_inputpassword.getText().toString().trim();
                confirm_newpassword = resetpassword_et_comfirmpassword.getText().toString().trim();
                if (new_password.equals(confirm_newpassword)&&!TextUtils.isEmpty(new_password)&&!TextUtils.isEmpty(confirm_newpassword)){
                    Bundle bundle = ResetActivity.this.getIntent().getExtras();
                    reset_phonenum = bundle.getString("phonenum");
                    reset_code = bundle.getString("code");

                    MyUser.resetPasswordBySMSCode(reset_code, reset_phonenum, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                toast("密码重置成功！");
                                startActivity(new Intent(ResetActivity.this, LoginActivity.class));
                                finish();
                            }else{
                                toast("重置密码失败!"+"code ="+e.getErrorCode()+",msg = "+e.getLocalizedMessage());
                            }
                        }
                    });
                }else if (TextUtils.isEmpty(new_password)||TextUtils.isEmpty(confirm_newpassword)){
                    toast("请输入密码！");
                } else {
                    toast("请输入相同的密码！");
                }


            }
        });
//        resetpassword_img_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ResetActivity.this,FindpasswordActivity.class));
//                finish();
//            }
//        });
    }
    private void toast(String content) {
        Toast.makeText(ResetActivity.this, content, Toast.LENGTH_LONG).show();
    }
}
