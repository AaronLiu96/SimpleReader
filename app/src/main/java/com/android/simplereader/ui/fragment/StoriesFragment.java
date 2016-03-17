package com.android.simplereader.ui.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.simplereader.R;
import com.android.simplereader.config.Constants;
import com.android.simplereader.model.bean.Stories;
import com.android.simplereader.model.callback.HttpCallbackListener;
import com.android.simplereader.ui.adapter.StoriesListAdapter;
import com.android.simplereader.util.DialogUtils;
import com.android.simplereader.util.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragon丶Lz on 2016/1/22.
 */
public class StoriesFragment extends Fragment implements AbsListView.OnScrollListener,AdapterView.OnItemClickListener,View.OnClickListener{

    private int page = 1;
    private boolean scrollFlag = false;
    private int lastVisibleItemPosition;
    private Object messageHttp;
    private int numbersInOnePage = 0;
    private List<Stories> items = new ArrayList<Stories>();
    private StoriesListAdapter adapter;
    private AppCompatActivity mAppCompatActivity;
    private Activity mActivity;

    private ImageButton refresh;
    private ImageView shadow;
    private ListView mListView;
    private Toolbar toolbar;


    public static final int SHOW_RESPONSE = 0;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    response(msg.obj, 0);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_storise,container,false);

        httpRequset(page);

        init(view);
        return  view;
    }
    private void init(View v) {
        AppCompatActivity mAppCompatActivity = (AppCompatActivity) mActivity;
        toolbar = (Toolbar) v.findViewById(R.id.storise_layout);
        mAppCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("幽默段子~");
        toolbar.setLogo(R.mipmap.ic_logo);
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
        mListView = (ListView) v.findViewById(R.id.lv_stories);
        adapter = new StoriesListAdapter(getActivity(), R.layout.fragment_storise_item, items);
        mListView.setAdapter(adapter);
        mListView.setOnScrollListener(this);
        mListView.setOnItemClickListener(this);
        refresh = (ImageButton) v.findViewById(R.id.btn_float);
        refresh.setOnClickListener(this);
        shadow = (ImageView) v.findViewById(R.id.btn_float_shadow);
}

    private void httpRequset(int page) {
        DialogUtils.onProcess(getActivity(), "正在为您准备幽默的段子中", "请稍后");
        OkHttpUtil.sendOKHttpRequest(Constants.storiesAPI + page, new HttpCallbackListener() {
            @Override
            public void onSuccess(String response) {
                DialogUtils.dissmissProcess();
                Message message = new Message();
                message.what = SHOW_RESPONSE;
                message.obj = response;
                messageHttp = response;
                handler.sendMessage(message);
            }


            @Override
            public void onFailure(Exception e) {
                DialogUtils.dissmissProcess();

            }
        });
    }

    private void response(Object msg, int startPosition) {
        try {
            JSONObject jsonObject = new JSONObject((String) msg);
            JSONArray comments = jsonObject.getJSONArray("comments");
            numbersInOnePage = comments.length();
            for (int i = startPosition; i < comments.length() && i < startPosition + 10; i++) {
                JSONObject json = (JSONObject) comments.opt(i);
                String title = json.getString("comment_author");
                String date = json.getString("comment_date");
                String content = json.getString("text_content");
                String email = json.getString("comment_author_email");
                int oo = json.getInt("vote_positive");
                int xx = json.getInt("vote_negative");
                int reply = json.getInt("comment_reply_ID");
                int id = json.getInt("comment_ID");
                items.add(new Stories(title, date, content, oo, xx, reply, id, email));
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_float:
                RotateAnimation animation = new RotateAnimation(0f, 1800f, Animation.RELATIVE_TO_SELF,
                        0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(1000);
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
                refresh.startAnimation(animation);

                items.clear();
                numbersInOnePage = 0;
                page = 1;
                httpRequset(page);
                mListView.setSelection(0);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //如果要对每一个做一个跳转的话就在这里做
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
                            httpRequset(++page);
                        }
                    }
                }
        }
        if (scrollFlag) {
            if (firstVisibleItem > lastVisibleItemPosition) {
                //上滑
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) refresh.getLayoutParams();
                int fabBottomMargin = lp.bottomMargin;
                refresh.animate().translationY(refresh.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
                shadow.animate().translationY(refresh.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
            }
            if (firstVisibleItem < lastVisibleItemPosition) {
                //下滑
                refresh.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                shadow.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }
            if (firstVisibleItem == lastVisibleItemPosition) {
                return;
            }
            lastVisibleItemPosition = firstVisibleItem;
        }
    }
}
