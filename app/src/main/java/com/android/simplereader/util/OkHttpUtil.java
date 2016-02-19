package com.android.simplereader.util;

import com.android.simplereader.config.Constants;
import com.android.simplereader.model.callback.HttpCallbackListener;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by Dragon丶Lz on 2016/1/30.
 */
public class OkHttpUtil {

    /***
     * 这是不需要请求头的网络请求方式
     * @param address 请求地址
     * @param listener 一个回调监听
     */
    public static void sendOKHttpRequest(final String address,final HttpCallbackListener listener){
        final OkHttpClient client  = new OkHttpClient();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().url(address).build();

                    Response response = null;

                    response = client.newCall(request).execute();

                    if (listener != null) {

                        listener.onSuccess(response.body().string());
                    }
                }catch (Exception e) {
                    if (listener !=null) {
                        listener.onFailure(e);
                        }
                    }
                }


        }).start();
    }

    public static void sendOKHttpRequestByHeader(final String address,final HttpCallbackListener listener){
        final OkHttpClient client  = new OkHttpClient();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().url(address).addHeader("apikey", Constants.apikey).build();

                    Response response = null;

                    response = client.newCall(request).execute();

                    if (listener != null) {

                        listener.onSuccess(response.body().string());
                    }
                }catch (Exception e) {
                    if (listener !=null) {
                        listener.onFailure(e);
                    }
                }
            }


        }).start();
    }


}
