package com.android.simplereader.model.bean;

import android.support.v7.widget.AppCompatEditText;

import cn.bmob.v3.BmobObject;

/**
 * Created by Dragon丶Lz on 2016/2/10.
 */
public class FeedBack extends BmobObject{
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
