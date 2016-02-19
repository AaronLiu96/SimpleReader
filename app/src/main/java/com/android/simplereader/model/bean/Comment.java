package com.android.simplereader.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Dragon丶Lz on 2016/2/2.
 */
public class Comment extends BmobObject {

    private String ZeroItemId;
    private String comment_data;
    private String comment_name;
    private String comment_date;

    public String getZeroItemId() {
        return ZeroItemId;
    }

    public void setZeroItemId(String zeroItemId) {
        ZeroItemId = zeroItemId;
    }

    public String getComment_data() {
        return comment_data;
    }

    public void setComment_data(String comment_data) {
        this.comment_data = comment_data;
    }

    public String getComment_name() {
        return comment_name;
    }

    public void setComment_name(String comment_name) {
        this.comment_name = comment_name;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }
}
