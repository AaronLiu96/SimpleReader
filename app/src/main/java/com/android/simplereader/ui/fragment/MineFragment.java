package com.android.simplereader.ui.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.simplereader.R;
import com.android.simplereader.model.bean.MineList;
import com.android.simplereader.ui.activity.CollectionActivity;
import com.android.simplereader.ui.activity.MineActivity;
import com.android.simplereader.ui.adapter.MineAdapter;
import com.android.simplereader.util.DialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragon丶Lz on 2016/1/22.
 */
public class MineFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mine_lv;
    private List<MineList> dataList = new ArrayList<>();
    private MineAdapter mineAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fragment_mine,container,false);
        mine_lv = (ListView) view.findViewById(R.id.mine_listview);
        mineAdapter = new MineAdapter(getActivity(),R.layout.fragment_mine_item,dataList);
        mine_lv.setAdapter(mineAdapter);
        mine_lv.setOnItemClickListener(this);
        return view;
    }

    private void initData(){
        dataList.add(new MineList("我的收藏",R.mipmap.ic_essay_collection));
        dataList.add(new MineList("修改密码",R.mipmap.ic_mine_change));
        dataList.add(new MineList("我发表的",R.mipmap.ic_mine_zero));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                //这里跳转到收藏界面
                Intent ToCollectionActivity =new Intent(getActivity(), CollectionActivity.class);
                ToCollectionActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(ToCollectionActivity);
                break;
            case 1:
                DialogUtils.UpdatePasswordDialog(getActivity(),view);

                break;
            case 2:
                Intent ToMineActivity =new Intent(getActivity(), MineActivity.class);
                ToMineActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(ToMineActivity);
                break;
            default:
                break;
        }
    }
}
