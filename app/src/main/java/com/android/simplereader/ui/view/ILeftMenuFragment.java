package com.android.simplereader.ui.view;

import com.android.simplereader.model.bean.LeftMenu;

import java.util.List;

/**
 * Created by Dragon丶Lz on 2016/1/29.
 */
public interface ILeftMenuFragment {

    void setName(String name);

    void refreshData(List<LeftMenu> dataList);
}
