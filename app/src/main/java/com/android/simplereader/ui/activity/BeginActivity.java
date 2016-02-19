package com.android.simplereader.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.android.simplereader.R;
import com.android.simplereader.app.BaseActivity;
import com.android.simplereader.util.ActivityCollectorUtils;
import com.android.simplereader.util.SPUtils;

import java.util.Timer;
import java.util.TimerTask;

public class BeginActivity extends BaseActivity {

    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        ActivityCollectorUtils.addActivity(this);
        logo= (ImageView) findViewById(R.id.begin_logo);
        TranslateAnimation down = new TranslateAnimation(0, 0, -300, 0);//位移动画，从button的上方300像素位置开始
        down.setFillAfter(true);
        down.setInterpolator(new BounceInterpolator());//弹跳动画,要其它效果的当然也可以设置为其它的值
        down.setDuration(2000);//持续时间
        logo.startAnimation(down);//设置按钮运行该动画效果
        jump();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtils.removeActivity(this);
    }

    private void jump(){
        Timer timer=new Timer();
        TimerTask task= new TimerTask() {
            @Override
            public void run() {

                if ((boolean) SPUtils.get(BeginActivity.this, "is_login", false)) {
                    Intent intent = new Intent(BeginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(BeginActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        };
        timer.schedule(task, 2500);
    }
}
