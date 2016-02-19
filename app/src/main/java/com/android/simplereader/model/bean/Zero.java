package com.android.simplereader.model.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Dragon丶Lz on 2016/2/1.
 */
public class Zero extends BmobObject{

    private String ZeroName;
    private String ZeroContent;
    private String ZeroDate;
    private Integer ZeroGood;



    public Zero(){}

    public String getZeroName() {
        return ZeroName;
    }

    public void setZeroName(String zeroName) {
        ZeroName = zeroName;
    }

    public String getZeroContent() {
        return ZeroContent;
    }

    public void setZeroContent(String zeroContent) {
        ZeroContent = zeroContent;
    }

    public String getZeroDate() {
        return ZeroDate;
    }

    public void setZeroDate(String zeroDate) {
        ZeroDate = zeroDate;
    }

    public Integer getZeroGood() {
        return ZeroGood;
    }

    public void setZeroGood(Integer zeroGood) {
        ZeroGood = zeroGood;
    }


}
