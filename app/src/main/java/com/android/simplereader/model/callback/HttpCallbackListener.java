package com.android.simplereader.model.callback;

/**
 * Created by Dragon丶Lz on 2016/1/30.
 */
public interface HttpCallbackListener {
    void onSuccess(String response);
    void onFailure(Exception e);
}
