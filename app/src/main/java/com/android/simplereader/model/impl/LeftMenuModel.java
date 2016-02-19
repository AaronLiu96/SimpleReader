package com.android.simplereader.model.impl;

import com.android.simplereader.R;
import com.android.simplereader.model.ILeftMenu;
import com.android.simplereader.model.bean.LeftMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragon丶Lz on 2016/1/29.
 */
public class LeftMenuModel implements ILeftMenu {



    @Override
    public void loadListData(List<LeftMenu> leftMenuList) {

        leftMenuList.add(new LeftMenu("我的朋友"));
        leftMenuList.add(new LeftMenu("关于我们"));
        leftMenuList.add(new LeftMenu("意见反馈"));
    }
    }
