package com.android.simplereader.model.bean;

/**
 * Created by Dragon丶Lz on 2016/2/5.
 */
public class MineList {

    private String MineTitle;
    private int MineImg;

    public MineList(String mineTitle,int Mineimg){
        this.MineImg = Mineimg;
        this.MineTitle = mineTitle;
    }

    public String getMineTitle() {
        return MineTitle;
    }

    public void setMineTitle(String mineTitle) {
        MineTitle = mineTitle;
    }

    public int getMineImg() {
        return MineImg;
    }

    public void setMineImg(int mineImg) {
        MineImg = mineImg;
    }
}
