package com.android.simplereader.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.simplereader.model.IStories;
import com.android.simplereader.model.bean.Stories;
import com.android.simplereader.model.callback.StoriesCallback;
import com.android.simplereader.model.impl.StoriesModel;
import com.android.simplereader.ui.view.IStoriesFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragon丶Lz on 2016/3/25.
 */
public class StoriesFragmentPresent {
    private Context context;
    private IStories iStories;
    private IStoriesFragmentView iStoriesFragmentView;
    private static  final String TAG = "StoriesProblem--->";

    private final static int PRASE_RESPONSE = 0;
    private String jsonData;
    private List<Stories> dataList = new ArrayList<>();

    private List<Stories> praseData;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case PRASE_RESPONSE:

                    dataList = iStories.praseData(msg.obj,0);
                    iStoriesFragmentView.showData(dataList);
                    break;
                default:
                    break;
            }
        }
    };

    public StoriesFragmentPresent(IStoriesFragmentView view ,Context mContext){
        this.iStoriesFragmentView = view;
        this.context = mContext;
        iStories = new StoriesModel();
    }

    public void getData(int page){
        iStories.loadStoriesData(page, context, new StoriesCallback() {
            @Override
            public void success(String response) {
                Message message = new Message();
                message.what = PRASE_RESPONSE;
                message.obj = response;
                // messageHttp = response;
                handler.sendMessage(message);
            }

            @Override
            public void failure(String result) {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }
        });



    }

    public List<Stories> praseData(Object msg,int startPosition){

        praseData =iStories.praseData(msg,startPosition);

        return praseData;
    }

    public void refreshData(){
        iStoriesFragmentView.refresh();
    }

    public String getJson(int page){
        iStories.loadStoriesData(page, context, new StoriesCallback() {
            @Override
            public void success(String response) {

            }

            @Override
            public void failure(String result) {

            }
        });

        return null;
    }


}
