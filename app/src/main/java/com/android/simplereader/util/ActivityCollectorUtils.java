package com.android.simplereader.util;

import com.android.simplereader.app.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragon丶Lz on 2016/2/3.
 */
public class ActivityCollectorUtils {

    public static List<BaseActivity> activities = new ArrayList<BaseActivity>();

        public static void addActivity(BaseActivity activity){
            activities.add(activity);
        }

        public static void removeActivity(BaseActivity activity){
            activities.remove(activity);
        }

        public static void finishAll(){
            for (BaseActivity activity : activities){
                if (!activity.isFinishing()) {
                    activity.finish();
                }
        }
    }
}
