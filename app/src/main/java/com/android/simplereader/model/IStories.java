package com.android.simplereader.model;

import android.content.Context;

import com.android.simplereader.model.bean.Stories;
import com.android.simplereader.model.callback.StoriesCallback;

import java.util.List;
import java.util.Objects;

/**
 * Created by Dragon丶Lz on 2016/3/25.
 */
public interface IStories {

    void  loadStoriesData(int page,Context context,StoriesCallback storiesCallback);

    List<Stories> praseData(Object response,int startPosition);

    String getResponse();


}
