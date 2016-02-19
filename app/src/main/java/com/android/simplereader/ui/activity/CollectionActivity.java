package com.android.simplereader.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.simplereader.R;
import com.android.simplereader.app.BaseActivity;
import com.android.simplereader.model.bean.Collection;
import com.android.simplereader.ui.adapter.CollectionAdapter;
import com.android.simplereader.util.ActivityCollectorUtils;
import com.android.simplereader.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class CollectionActivity extends BaseActivity {

    @Bind(R.id.collection_lv)
     ListView collection_lv;
    @Bind(R.id.collection_refresh)
     SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.toolbar)
     Toolbar toolbar;

    private final int SUCCESS = 1;
    private List<Collection>  collectionList = new ArrayList<>();
    private CollectionAdapter collectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        ActivityCollectorUtils.addActivity(this);
        toolbar.setTitle("我的收藏");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setLogo(R.mipmap.ic_logo);
        getDataFromBmob();
        collectionAdapter = new CollectionAdapter(CollectionActivity.this,R.layout.fragment_essay_item,collectionList);
        collection_lv.setAdapter(collectionAdapter);
        initRefresh();
    }
    private void initRefresh(){
        swipeRefreshLayout.setColorSchemeResources(R.color.color_1,
                R.color.color_2,
                R.color.color_3,
                R.color.color_4);
        //进度圈大小，只有两个值，DEFAULT、LARGE
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        //进度圈的背景颜色
        swipeRefreshLayout.setProgressBackgroundColor(R.color.swipe_background_color);
        /*进度圈位置
        swipeRefreshLayout.setPadding(20, 20, 20, 20);
        swipeRefreshLayout.setProgressViewOffset(true, 100, 200);
        swipeRefreshLayout.setDistanceToTriggerSync(50);*/
        //实现下拉滚动效果，100是下拉的位置
        swipeRefreshLayout.setProgressViewEndTarget(true, 100);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                collectionList.clear();

                getDataFromBmob();
                collection_lv.setSelection(0);

                //刷新成功
                mHandler.sendEmptyMessage(SUCCESS);

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtils.removeActivity(this);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //返回成功
                case 1:
                    swipeRefreshLayout.setRefreshing(false);        //关闭刷新
                    //将刷新得到的数据添加到适配器里
                    collectionAdapter.notifyDataSetChanged();
                    //swipeRefreshLayout.setEnabled(false);
                    break;

                default:
                    break;
            }
        }

    };

    private void getDataFromBmob(){

        BmobQuery<Collection> query = new BmobQuery<Collection>();
        query.addWhereEqualTo("UserName", (String) SPUtils.get(CollectionActivity.this, "name", ""));
        Log.d("CollectionProblem---->>>","name = "+(String) SPUtils.get(CollectionActivity.this, "name", ""));
        query.setLimit(50);
        query.findObjects(this, new FindListener<Collection>() {
            @Override
            public void onSuccess(List<Collection> list) {
                // TODO Auto-generated method stub
                for (int i = 0; i < list.size(); i++) {
                    collectionList.add(list.get(i));
                    collectionAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int i, String s) {
                // TODO Auto-generated method stub


               Toast.makeText(CollectionActivity.this,"出错了："+s,Toast.LENGTH_LONG).show();

            }
        });


    }
}
