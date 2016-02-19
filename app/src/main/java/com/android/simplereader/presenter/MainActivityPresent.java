package com.android.simplereader.presenter;

import com.android.simplereader.ui.view.IMainActivityView;

/**
 * Created by Dragon丶Lz on 2016/1/22.
 */
public class MainActivityPresent {

    private IMainActivityView iMainActivityView;

    public MainActivityPresent(IMainActivityView v){
        this.iMainActivityView = v;
    }

    public  void showFragment(int index){
        iMainActivityView.setTabSelection(index);
    }
}
