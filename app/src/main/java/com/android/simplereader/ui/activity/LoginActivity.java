package com.android.simplereader.ui.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.simplereader.R;
import com.android.simplereader.app.BaseActivity;
import com.android.simplereader.util.ActivityCollectorUtils;
import com.android.simplereader.util.DialogUtils;
import com.android.simplereader.util.SPUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.login_account_et)
    AppCompatEditText login_account_et;
    @Bind(R.id.login_password_et)
    AppCompatEditText login_password_et;
    @Bind(R.id.login_button)
    Button login_button;
    @Bind(R.id.login_ToRegister)
    TextView login_ToRegister;
    @Bind(R.id.login_ToTourist)
    TextView login_ToTourist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ActivityCollectorUtils.addActivity(this);
        initOnClick();
    }




    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.login_button:
                final String account = login_account_et.getText().toString();
                String password = login_password_et.getText().toString();
                DialogUtils.onProcess(LoginActivity.this,"正在登陆","请稍后..");
                BmobUser user = new BmobUser();
                user.setUsername(account);
                user.setPassword(password);
                user.login(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                          //在这里处理登录后的情况
                        DialogUtils.dissmissProcess();
                        SPUtils.put(LoginActivity.this, "is_login", true);
                        SPUtils.put(LoginActivity.this, "name", account);
                        Intent ToMainActivity = new Intent(LoginActivity.this,MainActivity.class);
                        ToMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(ToMainActivity);
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        DialogUtils.dissmissProcess();
                        SPUtils.put(LoginActivity.this, "is_login", false);
                        Snackbar.make(v, "账号或密码错误", Snackbar.LENGTH_SHORT)
                                .setAction("再试一次~", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                    }
                });


                break;
            case R.id.login_ToRegister:
                Intent ToRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                ToRegister.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(ToRegister);
                break;
            case R.id.login_ToTourist:
                SPUtils.put(LoginActivity.this, "is_login", false);
                Intent ToMainActivity = new Intent(LoginActivity.this,MainActivity.class);
                ToMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(ToMainActivity);
                this.finish();
                break;
        }
    }

    private void initOnClick(){
        login_button.setOnClickListener(this);
        login_ToRegister.setOnClickListener(this);
        login_ToTourist.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtils.removeActivity(this);
    }



}
