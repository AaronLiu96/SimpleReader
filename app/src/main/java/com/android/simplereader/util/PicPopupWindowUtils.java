package com.android.simplereader.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.android.simplereader.R;

/**
 * Created by Dragon丶Lz on 2016/2/26.
 */
public class PicPopupWindowUtils {

    public static PopupWindow getPopouWiondow(Activity activity) {
        PopupWindow mPopupWindow;
        View popupView = activity.getLayoutInflater().inflate(R.layout.view_popouwindow, null);

        mPopupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), (Bitmap) null));
        return mPopupWindow;
    }
}
