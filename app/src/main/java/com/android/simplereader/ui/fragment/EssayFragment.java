package com.android.simplereader.ui.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.simplereader.R;
import com.android.simplereader.config.Constants;
import com.android.simplereader.model.bean.Essay;
import com.android.simplereader.model.callback.HttpCallbackListener;
import com.android.simplereader.ui.activity.EssayActivity;
import com.android.simplereader.ui.activity.SearchActivity;
import com.android.simplereader.ui.adapter.EssayAdapter;
import com.android.simplereader.util.DialogUtils;
import com.android.simplereader.util.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragon丶Lz on 2016/1/22.
 */
public class EssayFragment extends Fragment implements AbsListView.OnScrollListener,AdapterView.OnItemClickListener {

    private ListView essay_listview;
    private FloatingActionButton search;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AppCompatActivity mAppCompatActivity;
    private Activity mActivity;
    private Toolbar toolbar;

    private List<Essay.Res_Body.PageBean.ContentList> essayList = new ArrayList<>();
    private static List<Essay.Res_Body.PageBean.ContentList> list = new ArrayList<>();
    private EssayAdapter adapter;
    private int page =1;
    private boolean scrollFlag = false;
    private final int SUCCESS = 1;
    private final int SHOW_RESPONSE = 2;
    private int numbersInOnePage = 0;
    private Object messageHttp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_essay,container,false);
        initView(view);
        httpRequest(page);
        adapter = new EssayAdapter(getActivity(),R.layout.fragment_essay_item,essayList);
        essay_listview.setAdapter(adapter);
        essay_listview.setOnScrollListener(this);
        essay_listview.setOnItemClickListener(this);
        initRefresh();
        return view;
    }

    private void initView(View v){
        AppCompatActivity mAppCompatActivity = (AppCompatActivity) mActivity;
        toolbar = (Toolbar) v.findViewById(R.id.essay_layout);
        mAppCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("优质文章~");
        toolbar.setLogo(R.mipmap.ic_logo);
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
        search = (FloatingActionButton) v.findViewById(R.id.essay_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ToSearch = new Intent(getActivity(), SearchActivity.class);
                startActivity(ToSearch);
            }
        });
        essay_listview = (ListView) v.findViewById(R.id.esssay_lv);

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.essay_refresh);
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
                        essayList.clear();
                        list.clear();
                        numbersInOnePage =0;
                        page =1;
                        Log.d("RefreshProBlem-->>","开始执行http....");
                        httpRequest(page);
                Log.d("RefreshProBlem-->>", "执行完成http....");

                essay_listview.setSelection(0);

                        //刷新成功
                        mHandler.sendEmptyMessage(SUCCESS);

            }
        });

    }

    private void httpRequest(int page){
        DialogUtils.onProcess(getActivity(), "正在为您准备文章中...", "请稍后");

        OkHttpUtil.sendOKHttpRequestByHeader(Constants.essayAPI + "?typeId=&page=" + page + "&key=", new HttpCallbackListener() {
            @Override
            public void onSuccess(String response) {
                Message message = new Message();
                message.what = SHOW_RESPONSE;
                message.obj = response;
                messageHttp = response;
                mHandler.sendMessage(message);
                DialogUtils.dissmissProcess();
            }

            @Override
            public void onFailure(Exception e) {
                DialogUtils.dissmissProcess();
            }
        });
    }

    private void response(Object msg,int startPosition) {

        Gson gson = new Gson();
        Type type = new TypeToken<Essay>() {
        }.getType();
        Essay essay = gson.fromJson((String) msg, type);
        list = essay.getShowapi_res_body().getPagebean().getContentlist();
        numbersInOnePage = list.size();
        Log.d("DataLoadProblem————>","第一个numbersInOnePage="+numbersInOnePage);
        for (int i = startPosition; i < list.size() && i < startPosition + 10; i++) {
            essayList.add(list.get(i));
        }

        adapter.notifyDataSetChanged();
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
                    adapter.notifyDataSetChanged();
                    //swipeRefreshLayout.setEnabled(false);
                    break;
                case SHOW_RESPONSE:
                    response(msg.obj, 0);
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EssayActivity.actionStart(getActivity(),essayList.get(position).getEssayUrl(),essayList.get(position).getEssayUserName(),essayList.get(position).getEssayTitle(),essayList.get(position).getEssayDate(),essayList.get(position).getContentImg());
    }

    @Override
   public void onScrollStateChanged(AbsListView view, int scrollState) {
    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
        scrollFlag = true;
    } else {
        scrollFlag = false;
    }
}

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount) {
            if (messageHttp != null) {
                if (totalItemCount != (page - 1) * numbersInOnePage) {
                    if (totalItemCount < numbersInOnePage * page) {
                        response(messageHttp, totalItemCount - (page - 1) * numbersInOnePage);
                    } else {
                        httpRequest(++page);
                    }
                }
            }
        }
    }
}
