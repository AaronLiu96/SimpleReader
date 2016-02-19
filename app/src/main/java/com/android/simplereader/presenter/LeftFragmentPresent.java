package com.android.simplereader.presenter;

import android.content.Context;
import android.util.Log;

import com.android.simplereader.model.ILeftMenu;
import com.android.simplereader.model.bean.LeftMenu;
import com.android.simplereader.model.impl.LeftMenuModel;
import com.android.simplereader.ui.view.ILeftMenuFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragon丶Lz on 2016/1/29.
 */
public class LeftFragmentPresent {

    private ILeftMenu iLeftMenu;
    private ILeftMenuFragment iLeftMenuFragment;
    private Context context;
    private List<LeftMenu> dataList = new ArrayList<>();
    public LeftFragmentPresent(ILeftMenuFragment view,Context mContext){
        this.context = mContext;
        iLeftMenuFragment =view;
        iLeftMenu = new LeftMenuModel();
    }

    public void showData(){

        iLeftMenu.loadListData(dataList);
        Log.d("LeftListProblem-->>",dataList.get(0).getName());
        iLeftMenuFragment.refreshData(dataList);

    }
}
