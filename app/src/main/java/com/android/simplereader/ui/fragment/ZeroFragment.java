package com.android.simplereader.ui.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.simplereader.R;
import com.android.simplereader.model.bean.Zero;
import com.android.simplereader.ui.activity.EditActivity;
import com.android.simplereader.ui.adapter.ZeroAdapter;
import com.android.simplereader.util.BmobUtil;
import com.android.simplereader.util.DialogUtils;
import com.android.simplereader.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by Dragon丶Lz on 2016/1/22.
 */
public class ZeroFragment extends Fragment {

    private ListView zero_listview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton zero_edit;
    private AppCompatActivity mAppCompatActivity;
    private Activity mActivity;
    private Toolbar toolbar;

    private List<Zero> zeroList = new ArrayList<>();
    private int numberOfLoad =50;
    private ZeroAdapter zeroAdapter;
    private final int SUCCESS = 1;
    private final int SHOW_RESPONSE = 2;

    private String TAG = "ZeroNoDataProblem--->>";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_zero,container,false);
        initView(view);
        getDataFromBmob(view);
        zeroAdapter = new ZeroAdapter(getActivity(),R.layout.fragment_zero_item,zeroList);
        zero_listview.setAdapter(zeroAdapter);
        initRefresh(view);
        return view;
    }

    private void initView(View v){
        AppCompatActivity mAppCompatActivity = (AppCompatActivity) mActivity;
        toolbar = (Toolbar) v.findViewById(R.id.zero_layout);
        mAppCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("易友空间~");
        toolbar.setLogo(R.mipmap.ic_logo);
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
        swipeRefreshLayout= (SwipeRefreshLayout) v.findViewById(R.id.zero_refresh);
        zero_listview = (ListView) v.findViewById(R.id.zero_listview);
        zero_edit = (FloatingActionButton) v.findViewById(R.id.zero_edit);
        zero_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditActivity.actionStart(getActivity(),(String) SPUtils.get(getActivity(), "name", ""));
            }
        });

    }
    private void getDataFromBmob(final View view){
        DialogUtils.onProcess(getActivity(), "易友们正在努力赶来中...", "请稍后");

        Log.d(TAG, "执行科getDataFromBmob方法");
        BmobQuery<Zero> query = new BmobQuery<Zero>();

        query.setLimit(numberOfLoad);
        query.findObjects(getActivity(), new FindListener<Zero>() {
            @Override
            public void onSuccess(List<Zero> list) {
                DialogUtils.dissmissProcess();

                for (int i = 0; i < list.size(); i++) {
                    zeroList.add(list.get(i));
                    Log.d(TAG, "zeroList得到了数据——>" + zeroList.get(i).getZeroContent());
                    zeroAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int i, String s) {
                DialogUtils.dissmissProcess();

                Snackbar.make(view, "数据出错了，请稍后再试~"+s, Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                Log.d("ZeroProblem————>",":"+s);
            }
        });




    }

    private void initRefresh(final View view){
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

                        zeroList.clear();

                        getDataFromBmob(view);



                        zero_listview.setSelection(0);

                        //刷新成功
                        mHandler.sendEmptyMessage(SUCCESS);

            }
        });

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
                    zeroAdapter.notifyDataSetChanged();
                    //swipeRefreshLayout.setEnabled(false);
                    break;

                default:
                    break;
            }
        }

    };
}
