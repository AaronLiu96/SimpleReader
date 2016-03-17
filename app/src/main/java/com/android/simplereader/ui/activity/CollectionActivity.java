package com.android.simplereader.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.simplereader.R;
import com.android.simplereader.app.BaseActivity;
import com.android.simplereader.model.bean.Collection;
import com.android.simplereader.ui.adapter.CollectionAdapter;
import com.android.simplereader.util.ActivityCollectorUtils;
import com.android.simplereader.util.BmobUtil;
import com.android.simplereader.util.SPUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class CollectionActivity extends BaseActivity implements ActionMode.Callback {

    @Bind(R.id.collection_lv)
     ListView collection_lv;
    @Bind(R.id.collection_refresh)
     SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.toolbar)
     Toolbar toolbar;

    private final int SUCCESS = 1;
    private List<Collection>  collectionList = new ArrayList<>();
    private CollectionAdapter collectionAdapter;
    public Set<Integer> positionSet = new HashSet<>();
    private ActionMode mActionMode;
    public static CollectionActivity collectionActivirt_instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        ActivityCollectorUtils.addActivity(this);
        collectionActivirt_instance =this;
        setSupportActionBar(toolbar);
        toolbar.setTitle("我的收藏");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setLogo(R.mipmap.ic_logo);
        getDataFromBmob();
        collectionAdapter = new CollectionAdapter(CollectionActivity.this,R.layout.fragment_essay_item,collectionList);
        collection_lv.setAdapter(collectionAdapter);
        collection_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null) { // 如果当前处于多选状态，则进入多选状态的逻辑
                    // 维护当前已选的position
                    addOrRemove(position);
                } else { // 如果不是多选状态，则进入点击事件的业务逻辑
                    EssayActivity.actionStart(CollectionActivity.this, collectionList.get(position).getCollectionContent(), collectionList.get(position).getUserName(),
                            collectionList.get(position).getCollectionTitle(), collectionList.get(position).getCollectionTime(), collectionList.get(position).getCollectionPic());
                }


            }
        });
        collection_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode == null) {
                    mActionMode = startActionMode(CollectionActivity.this);
                }
                addOrRemove(position);
                return true;
            }
        });
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

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        toolbar.setVisibility(View.GONE);
        if (mActionMode == null) {
            mActionMode = mode;
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_collection, menu);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete: // 删除已选
                //删除操作
                for (int i=0;i<positionSet.size();i++){
                    BmobUtil.DeleteCollectionItem(CollectionActivity.this,collectionList.get(i).getObjectId());
                }
                collectionList.clear();
                getDataFromBmob();
                collection_lv.setSelection(0);
                collectionAdapter.notifyDataSetChanged();
                mode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

        mActionMode = null;
        toolbar.setVisibility(View.VISIBLE);
        positionSet.clear();
        collectionAdapter.notifyDataSetChanged();

    }

    private void addOrRemove(int position) {
        if (positionSet.contains(position)) { // 如果包含，则撤销选择
            positionSet.remove(position);
        } else { // 如果不包含，则添加
            positionSet.add(position);
        }
        if (positionSet.size() == 0) { // 如果没有选中任何的item，则退出多选模式
            mActionMode.finish();
        } else {
            // 设置ActionMode标题
            mActionMode.setTitle("已选择"+positionSet.size() +"项");
            // 更新列表界面，否则无法显示已选的item
            collectionAdapter.notifyDataSetChanged();
        }
    }
}
