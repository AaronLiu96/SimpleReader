package com.android.simplereader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.simplereader.R;
import com.android.simplereader.app.BaseActivity;
import com.android.simplereader.util.ActivityCollectorUtils;
import com.android.simplereader.util.BmobUtil;
import com.android.simplereader.util.SPUtils;

import butterknife.Bind;

public class EssayActivity extends BaseActivity {


    private String Essay_url;
    private String Essay_UserName;
    private String Essay_title;
    private String Essay_date;
    private String Essay_contentImg;


    private WebView essay_web_view;



    public static void actionStart(Context context,String url,String UserName,String title,String date,String contentImg){
        Intent intent = new Intent(context,EssayActivity.class);
        intent.putExtra("essay_url",url);
        intent.putExtra("essay_UserName",UserName);
        intent.putExtra("essay_title",title);
        intent.putExtra("essay_contentImage",contentImg);
        intent.putExtra("essay_date",date);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);
        ActivityCollectorUtils.addActivity(this);
        Essay_url = getIntent().getStringExtra("essay_url");
        Essay_UserName = getIntent().getStringExtra("essay_UserName");
        Essay_title = getIntent().getStringExtra("essay_title");
        Essay_date = getIntent().getStringExtra("essay_date");
        Essay_contentImg = getIntent().getStringExtra("essay_contentImage");
        FloatingActionButton collection = (FloatingActionButton) findViewById(R.id.essay_collection);
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里做完成收藏的任务
                BmobUtil.PostCollection(EssayActivity.this,(String) SPUtils.get(EssayActivity.this, "name", ""),Essay_UserName,Essay_title,Essay_date,Essay_url,Essay_contentImg);

            }
        });

        essay_web_view = (WebView) findViewById(R.id.essay_web_view);
        essay_web_view.getSettings().setJavaScriptEnabled(true);
        essay_web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        essay_web_view.loadUrl(Essay_url);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtils.removeActivity(this);
    }
}
