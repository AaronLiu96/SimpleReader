package com.android.simplereader.model.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.simplereader.config.Constants;
import com.android.simplereader.model.IStories;
import com.android.simplereader.model.bean.Stories;
import com.android.simplereader.model.callback.HttpCallbackListener;
import com.android.simplereader.model.callback.StoriesCallback;
import com.android.simplereader.util.DialogUtils;
import com.android.simplereader.util.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dragon丶Lz on 2016/3/25.
 */
public class StoriesModel implements IStories {

    private static  final String TAG = "StoriesProblem-->";
    private int numbersInOnePage = 0;
    private String json_data = null;
    private List<Stories> lists = new ArrayList<>();



    public StoriesModel(){

    }

    @Override
    public void  loadStoriesData(int page,Context context, final StoriesCallback storiesCallback) {
        DialogUtils.onProcess(context, "正在为您准备幽默的段子中", "请稍后");
        OkHttpUtil.sendOKHttpRequest(Constants.storiesAPI + page, new HttpCallbackListener() {
            @Override
            public void onSuccess(String response) {
                DialogUtils.dissmissProcess();
                json_data =response;
                Log.d(TAG, "Model里面的数据response：" + response);
                storiesCallback.success(response);
            }


            @Override
            public void onFailure(Exception e) {
                DialogUtils.dissmissProcess();
                storiesCallback.failure("未知错误");
            }
        });

    }

    @Override
    public List<Stories> praseData(Object response, int startPosition) {
        try {
            JSONObject jsonObject = new JSONObject((String)response);
            JSONArray comments = jsonObject.getJSONArray("comments");
            numbersInOnePage = comments.length();
            for (int i = startPosition; i < comments.length() && i < startPosition + 10; i++) {
                JSONObject json = (JSONObject) comments.opt(i);
                String title = json.getString("comment_author");
                String date = json.getString("comment_date");
                String content = json.getString("text_content");
                String email = json.getString("comment_author_email");
                int oo = json.getInt("vote_positive");
                int xx = json.getInt("vote_negative");
                int reply = json.getInt("comment_reply_ID");
                int id = json.getInt("comment_ID");
                lists.add(new Stories(title, date, content, oo, xx, reply, id, email));
            }
      //      adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lists;
    }

    @Override
    public String getResponse() {
        return json_data;
    }


}
