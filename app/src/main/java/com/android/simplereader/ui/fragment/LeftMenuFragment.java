package com.android.simplereader.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.simplereader.R;
import com.android.simplereader.model.bean.LeftMenu;
import com.android.simplereader.presenter.LeftFragmentPresent;
import com.android.simplereader.ui.activity.AboutActivity;
import com.android.simplereader.ui.adapter.LeftMenuAdapter;
import com.android.simplereader.ui.view.ILeftMenuFragment;
import com.android.simplereader.util.ActivityCollectorUtils;
import com.android.simplereader.util.DialogUtils;
import com.android.simplereader.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Dragon丶Lz on 2016/1/28.
 */
public class LeftMenuFragment extends Fragment implements ILeftMenuFragment,AdapterView.OnItemClickListener{

    private TextView LeftMenu_Name;
    private ListView LeftMenu_List;
    private TextView LeftMenu_finish;
    private LeftMenuAdapter leftMenuAdapter;
    private LeftFragmentPresent leftFragmentPresent;
    private List<LeftMenu> dataList = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fragment_left,container,false);
        initView(view);
        leftMenuAdapter = new LeftMenuAdapter(getActivity(),R.layout.fragment_leftmenu_item,dataList);

        if ((boolean) SPUtils.get(getActivity(), "is_login", false)) {
            LeftMenu_Name.setText((String) SPUtils.get(getActivity(), "name", ""));
        }else {
            LeftMenu_Name.setText("未登录");
        }
        LeftMenu_List.setAdapter(leftMenuAdapter);
        LeftMenu_List.setOnItemClickListener(this);


        return view;
    }

    private void initView(View v){


        LeftMenu_Name = (TextView) v.findViewById(R.id.LeftMenu_Name);
        LeftMenu_List = (ListView) v.findViewById(R.id.LeftMenu_List);
        LeftMenu_finish = (TextView) v.findViewById(R.id.LeftMenu_finish);
        LeftMenu_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollectorUtils.finishAll();
            }
        });



    }


    @Override
    public void setName(String name) {
        LeftMenu_Name.setText(name);
    }

    @Override
    public void refreshData(List<LeftMenu> dataList) {
        Log.d("LeftListProblem-->>","我已经执行了refreshData");
        leftMenuAdapter = new LeftMenuAdapter(getActivity(),R.layout.fragment_leftmenu_item,dataList);
        Log.d("LeftListProblem-->>",dataList.get(0).getName());
        LeftMenu_List.setAdapter(leftMenuAdapter);
        Log.d("LeftListProblem-->>", "已经执行了setAdapter");
    }


    private void initData(){
        dataList.add(new LeftMenu("意见反馈"));
        dataList.add(new LeftMenu("关于我们"));
        dataList.add(new LeftMenu("切换账号"));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        switch (parent.getId()){
            case R.id.LeftMenu_List:
                switch (position){
                    case 0:
                        DialogUtils.PostFeedBack(getActivity());
                        break;
                    case 1:
                        Intent ToAbout = new Intent(getActivity(), AboutActivity.class);
                        ToAbout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(ToAbout);
                        break;
                    case 2:
                        break;
                    default:
                        break;
                }
                break;

        }
    }
}
