package com.android.simplereader.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Dragon丶Lz on 2016/2/15.
 */
public class Collection extends BmobObject {

    private String CollectionUserName;
    private String CollectionTitle;
    private String CollectionContent;
    private String CollectionPic;
    private String CollectionTime;
    private String UserName;

    public String getCollectionUserName() {
        return CollectionUserName;
    }

    public void setCollectionUserName(String collectionUserName) {
        CollectionUserName = collectionUserName;
    }

    public String getCollectionTitle() {
        return CollectionTitle;
    }

    public void setCollectionTitle(String collectionTitle) {
        CollectionTitle = collectionTitle;
    }

    public String getCollectionContent() {
        return CollectionContent;
    }

    public void setCollectionContent(String collectionContent) {
        CollectionContent = collectionContent;
    }

    public String getCollectionPic() {
        return CollectionPic;
    }

    public void setCollectionPic(String collectionPic) {
        CollectionPic = collectionPic;
    }

    public String getCollectionTime() {
        return CollectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        CollectionTime = collectionTime;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
