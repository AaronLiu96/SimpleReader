package com.android.simplereader.ui.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.simplereader.R;
import com.android.simplereader.model.bean.MineList;
import com.android.simplereader.ui.activity.CollectionActivity;
import com.android.simplereader.ui.activity.MineActivity;
import com.android.simplereader.ui.adapter.MineAdapter;
import com.android.simplereader.util.DialogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dragon丶Lz on 2016/1/22.
 */
public class MineFragment extends Fragment implements AdapterView.OnItemClickListener {

    //private ListView mine_lv;
   // private List<MineList> dataList = new ArrayList<>();
    //private MineAdapter mineAdapter;
    private GridView mine_gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter sim_adapter;
    private String[] iconName = { "邮箱", "我的收藏", "我的心情", "修改密码"};
    private int[] icon = {R.mipmap.ic_mine_email,R.mipmap.ic_fragmen_mine_collectiont,R.mipmap.ic_fabiao,R.mipmap.ic__mine_password};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mine,container,false);
        mine_gridView = (GridView) view.findViewById(R.id.mine_gridview);
        dataList = new ArrayList<Map<String, Object>>();
        initData();
      //  mineAdapter = new MineAdapter(getActivity(),R.layout.fragment_mine_item,dataList);
       // mine_lv.setAdapter(mineAdapter);
      //  mine_lv.setOnItemClickListener(this);
        String [] from ={"image","text"};
        int [] to = {R.id.mine_ItemImage,R.id.mine_ItemText};
        sim_adapter = new SimpleAdapter(getActivity(), dataList, R.layout.fragment_mine_item, from, to);
        //配置适配器
        mine_gridView.setAdapter(sim_adapter);
        mine_gridView.setOnItemClickListener(this);
        return view;
    }
/**
    private void initData(){
        dataList.add(new MineList("我的收藏",R.mipmap.ic_essay_collection));
        dataList.add(new MineList("修改密码",R.mipmap.ic_mine_change));
        dataList.add(new MineList("我发表的",R.mipmap.ic_mine_zero));

    }
**/

    public List<Map<String, Object>> initData(){
    //cion和iconName的长度是相同的，这里任选其一都可以
    for(int i=0;i<icon.length;i++){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("image", icon[i]);
        map.put("text", iconName[i]);
        dataList.add(map);
    }

    return dataList;
}
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                //这里先不处理

                break;
            case 1:
                //这里跳转到收藏界面
                Intent ToCollectionActivity =new Intent(getActivity(), CollectionActivity.class);
                ToCollectionActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(ToCollectionActivity);
                break;
            case 3:
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
