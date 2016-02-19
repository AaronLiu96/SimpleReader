package com.android.simplereader.ui.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.simplereader.R;
import com.android.simplereader.app.BaseActivity;
import com.android.simplereader.model.bean.Zero;
import com.android.simplereader.ui.adapter.MineMoodAdapter;
import com.android.simplereader.util.ActivityCollectorUtils;
import com.android.simplereader.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class MineActivity extends BaseActivity {

    @Bind(R.id.toolbar_mine)
    Toolbar toolbar;
    @Bind(R.id.activity_mine_rv)
    RecyclerView activity_mine_rv;
    @Bind(R.id.rootLayout)
    CoordinatorLayout rootLayout;
    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private MineMoodAdapter mineMoodAdapter;
    private List<Zero> moodList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ButterKnife.bind(this);
        ActivityCollectorUtils.addActivity(this);
        initView();
        getDataFromBmob(MineActivity.this);



    }


    private void initView(){
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        collapsingToolbarLayout.setTitle("我的心情");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtils.removeActivity(this);
    }


        public void getDataFromBmob(Context context) {
            BmobQuery<Zero> query = new BmobQuery<Zero>();

            query.addWhereEqualTo("ZeroName", (String)SPUtils.get(context,"name",""));
            query.setLimit(50);
            query.findObjects(context, new FindListener<Zero>() {
                @Override
                public void onSuccess(List<Zero> list) {
                    for (int i = 0; i < list.size(); i++) {
                        moodList.add(list.get(i));
                        Log.d("MineMoodActivity-->>", moodList.get(i).getZeroDate());

                    }
                    mineMoodAdapter = new MineMoodAdapter(moodList,MineActivity.this);
                    activity_mine_rv.setLayoutManager(new LinearLayoutManager(MineActivity.this));
                    activity_mine_rv.setAdapter(mineMoodAdapter);
                    activity_mine_rv.setItemAnimator(new DefaultItemAnimator());
                }

                @Override
                public void onError(int i, String s) {

                }
            });
        }



}
