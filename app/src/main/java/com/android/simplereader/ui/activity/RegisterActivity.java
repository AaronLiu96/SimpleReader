package com.android.simplereader.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.simplereader.R;
import com.android.simplereader.app.BaseActivity;
import com.android.simplereader.util.ActivityCollectorUtils;
import com.android.simplereader.util.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.register_account_et)
    AppCompatEditText register_account_et;
    @Bind(R.id.register_password_et)
    AppCompatEditText register_password_et;
    @Bind(R.id.register_email_et)
    AppCompatEditText register_email_et;
    @Bind(R.id.register_button)
    Button register_button;
    @Bind(R.id.register_ToLogin)
    TextView register_ToLogin;
    @Bind(R.id.register_ToTourist)
    TextView register_ToTourist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActivityCollectorUtils.addActivity(this);
        ButterKnife.bind(this);
        initOnClick();
    }
    private void initOnClick(){
        register_button.setOnClickListener(this);
        register_ToLogin.setOnClickListener(this);
        register_ToTourist.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.register_button:
                String account = register_account_et.getText().toString();
                String password = register_password_et.getText().toString();
                String email = register_email_et.getText().toString();
                BmobUser user = new BmobUser();
                user.setUsername(account);
                user.setEmail(email);
                user.setPassword(password);
                user.signUp(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Snackbar.make(v, "注册陈成功了！", Snackbar.LENGTH_SHORT)
                                .setAction("" +
                                        "快去登陆吧", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent ToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(ToLogin);

                                    }
                                }).show();

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Snackbar.make(v, "抱歉出错了：" + s, Snackbar.LENGTH_SHORT)
                                .setAction("再试一次~", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                    }
                });

                break;
            case R.id.register_ToTourist:
                SPUtils.put(RegisterActivity.this, "is_login", false);
                Intent ToMainActivity = new Intent(RegisterActivity.this,MainActivity.class);
                ToMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(ToMainActivity);
                this.finish();
                break;
            case R.id.register_ToLogin:
                Intent ToLogin = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(ToLogin);
                this.finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtils.removeActivity(this);
    }
}
