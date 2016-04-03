package com.android.simplereader.ui.view;

import com.android.simplereader.model.bean.Stories;

import java.util.List;

/**
 * Created by Dragon丶Lz on 2016/3/25.
 */
public interface IStoriesFragmentView {

    void refresh();

    void fabAnimate();

    void showData(List<Stories> data);
}
