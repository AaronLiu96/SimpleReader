package com.android.simplereader.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.simplereader.R;


/**
 * Created by Dragon丶Lz on 2015/2/1.
 */
public class BackTitleLayout extends RelativeLayout {
    public BackTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.include_title, this);

        ImageView imageView = (ImageView) findViewById(R.id.iv_back);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }
}
