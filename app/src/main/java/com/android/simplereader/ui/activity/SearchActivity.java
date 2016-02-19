package com.android.simplereader.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.simplereader.R;
import com.android.simplereader.app.BaseActivity;
import com.android.simplereader.config.Constants;
import com.android.simplereader.model.bean.Essay;
import com.android.simplereader.model.callback.HttpCallbackListener;
import com.android.simplereader.ui.adapter.EssayAdapter;
import com.android.simplereader.util.ActivityCollectorUtils;
import com.android.simplereader.util.OkHttpUtil;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements AbsListView.OnScrollListener,AdapterView.OnItemClickListener {

    private List<Essay.Res_Body.PageBean.ContentList> essayList = new ArrayList<>();
    private static List<Essay.Res_Body.PageBean.ContentList> list = new ArrayList<>();
    private EssayAdapter adapter;
    private int page =1;
    private boolean scrollFlag = false;
    private final int SUCCESS = 1;
    private final int SHOW_RESPONSE = 2;
    private int numbersInOnePage = 0;
    private Object messageHttp;
    private String searchText;

    private MaterialEditText editText;
    private ListView listView;
    private ImageView search_btn;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActivityCollectorUtils.addActivity(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.essay_activity_refresh);
        listView = (ListView) findViewById(R.id.essay_search_activity__lv);
        editText = (MaterialEditText) findViewById(R.id.essay_activity_edt);
        search_btn = (ImageView) findViewById(R.id.essay_activity_img_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                essayList.clear();
                list.clear();
                numbersInOnePage =0;
                page =1;
                searchText = editText.getText().toString();
                httpRequest(page,searchText);
                listView.setSelection(0);
            }
        });
        adapter = new EssayAdapter(SearchActivity.this,R.layout.fragment_essay_item,essayList);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(this);
        initRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtils.removeActivity(this);
    }


    private void httpRequest(int page,String searchData){
        OkHttpUtil.sendOKHttpRequestByHeader(Constants.essayAPI + "?typeId=&page=" + page + "&key="+searchData, new HttpCallbackListener() {
            @Override
            public void onSuccess(String response) {
                Message message = new Message();
                message.what = SHOW_RESPONSE;
                message.obj = response;
                messageHttp = response;
                mHandler.sendMessage(message);
            }

            @Override
            public void onFailure(Exception e) {

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
        Log.d("DataLoadProblem————>", "第一个numbersInOnePage=" + numbersInOnePage);
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
                //new一个县城实现刷新，下面内容改为实现的功能。例如网页获取数据的重新获取一遍网页你内容
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        essayList.clear();
                        list.clear();
                        numbersInOnePage =0;
                        page =1;


                        try {
                            httpRequest(page,searchText);
                            listView.setSelection(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //刷新成功
                        mHandler.sendEmptyMessage(SUCCESS);
                    }
                }).start();
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EssayActivity.actionStart(SearchActivity.this,essayList.get(position).getEssayUrl(),essayList.get(position).getEssayUserName(),essayList.get(position).getEssayTitle(),essayList.get(position).getEssayDate(),essayList.get(position).getContentImg());
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
                        httpRequest(++page,searchText);
                    }
                }
            }
        }
    }
}
