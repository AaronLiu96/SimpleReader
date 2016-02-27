package com.android.simplereader.model.callback;

import android.view.MotionEvent;
import android.view.View;

import com.android.simplereader.R;

/**
 * Created by Dragon丶Lz on 2016/2/26.
 */
public class MyPopupOnTouchListner implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            v.setBackgroundColor(v.getResources().getColor(R.color.secondary_text));
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            v.setBackgroundColor(v.getResources().getColor(R.color.background_material_light));
        }
        return false;
    }
}

