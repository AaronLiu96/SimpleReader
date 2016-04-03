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
import com.android.simplereader.presenter.StoriesFragmentPresent;
import com.android.simplereader.ui.adapter.StoriesListAdapter;
import com.android.simplereader.ui.view.IStoriesFragmentView;
import com.android.simplereader.util.DialogUtils;
import com.android.simplereader.util.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dragon丶Lz on 2016/1/22.
 */
public class StoriesFragment extends Fragment implements AbsListView.OnScrollListener,View.OnClickListener,IStoriesFragmentView{

    private int page = 1;
    private boolean scrollFlag = false;
    private int lastVisibleItemPosition;
    private Object messageHttp;
    private int numbersInOnePage = 0;
    private List<Stories> items;
    private StoriesListAdapter adapter;
    private AppCompatActivity mAppCompatActivity;
    private Activity mActivity;
    private StoriesFragmentPresent storiesFragmentPresent;

    @Bind(R.id.btn_float)
    ImageButton refresh;
    @Bind(R.id.btn_float_shadow)
    ImageView shadow;
    @Bind(R.id.lv_stories)
    ListView mListView;
    @Bind(R.id.storise_layout)
    Toolbar toolbar;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_storise,container,false);
        ButterKnife.bind(this, view);
        storiesFragmentPresent = new StoriesFragmentPresent(this,getActivity());
       storiesFragmentPresent.getData(page);
        initToolbar();
        return  view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_float:
                storiesFragmentPresent.refreshData();
                break;
        }
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
                            storiesFragmentPresent.praseData(messageHttp, totalItemCount - (page - 1) * numbersInOnePage);
                        } else {
                            storiesFragmentPresent.getData(++page);
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

    @Override
    public void refresh() {
        RotateAnimation animation = new RotateAnimation(0f, 1800f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        refresh.startAnimation(animation);

        items.clear();
        numbersInOnePage = 0;
        page = 1;
        storiesFragmentPresent.getData(page);
        mListView.setSelection(0);
    }

    @Override
    public void fabAnimate() {

    }

    @Override
    public void showData(List<Stories> data) {
        adapter = new StoriesListAdapter(getActivity(), R.layout.fragment_storise_item, data);
        mListView.setAdapter(adapter);
        mListView.setOnScrollListener(this);
        refresh.setOnClickListener(this);
        adapter.notifyDataSetChanged();
    }

    private void initToolbar(){
        AppCompatActivity mAppCompatActivity = (AppCompatActivity) mActivity;
        mAppCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("幽默段子~");
        toolbar.setLogo(R.mipmap.ic_logo);
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.white));
    }
}
