package com.android.simplereader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.android.simplereader.R;
import com.android.simplereader.app.BaseActivity;
import com.android.simplereader.util.ActivityCollectorUtils;
import com.android.simplereader.util.BmobUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.Bind;

public class EditActivity extends BaseActivity {



    private FloatingActionButton zero_edit_complete;

    private MaterialEditText activity_edit_zero;

    private Toolbar toolbar;

    private String name;

    public static void actionStart(Context context,String UserName){
        Intent intent = new Intent(context,EditActivity.class);

        intent.putExtra("Zero_UserName",UserName);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ActivityCollectorUtils.addActivity(this);
        name = getIntent().getStringExtra("Zero_UserName");
        initView();
        toolbar.setTitle("写心情~");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        zero_edit_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String content = activity_edit_zero.getText().toString();
                if (content != null) {
                    new MaterialDialog.Builder(EditActivity.this)
                            .title("温馨提示")
                            .content("是否发表此心情呢?")
                            .positiveText("是的，我不犹豫了")
                            .negativeText("不要，我还要想一下")
                            .titleColor(EditActivity.this.getResources().getColor(R.color.main))
                            .backgroundColor(EditActivity.this.getResources().getColor(R.color.white))
                            .positiveColor(EditActivity.this.getResources().getColor(R.color.main))
                            .negativeColor(EditActivity.this.getResources().getColor(R.color.dialog_nav))
                            .cancelable(false)
                            .theme(Theme.LIGHT)
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    BmobUtil.PostZeroContent(v, EditActivity.this, content, name);
                                    finish();
                                }

                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }
        });
    }

    private void initView(){
        zero_edit_complete = (FloatingActionButton) findViewById(R.id.zero_edit_complete);
        activity_edit_zero = (MaterialEditText) findViewById(R.id.activity_edit_zero);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtils.removeActivity(this);
    }
}
