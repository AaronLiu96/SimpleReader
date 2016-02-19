package com.android.simplereader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.android.simplereader.R;
import com.android.simplereader.app.BaseActivity;
import com.android.simplereader.model.bean.Comment;
import com.android.simplereader.ui.adapter.CommentAdapter;
import com.android.simplereader.util.ActivityCollectorUtils;
import com.android.simplereader.util.BmobUtil;
import com.android.simplereader.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class CommentActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.comment_btn_send)
    Button comment_btn_send;
    @Bind(R.id.comment_edt)
    EditText comment_edt;
    @Bind(R.id.comment_lv)
    ListView comment_lv;

    private CommentAdapter commentAdapter;
    private String zero_id;
    private List<Comment> commentList = new ArrayList<>();

    public static void actionStart(Context context,String objectID){
        Intent intent = new Intent(context,CommentActivity.class);

        intent.putExtra("Zero_ObjectID",objectID);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ActivityCollectorUtils.addActivity(this);
        ButterKnife.bind(this);
        zero_id = getIntent().getStringExtra("Zero_ObjectID");
        toolbar.setTitle("看评论~");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        comment_btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = comment_edt.getText().toString();
                BmobUtil.PostZeroComment(CommentActivity.this,comment,(String) SPUtils.get(CommentActivity.this, "name", ""),zero_id);
                commentList.clear();
                getDataFromBmob();
            }
        });
        getDataFromBmob();
        commentAdapter = new CommentAdapter(CommentActivity.this,R.layout.activity_comment_item,commentList);
        comment_lv.setAdapter(commentAdapter);



    }


    private void getDataFromBmob(){
        zero_id = getIntent().getStringExtra("Zero_ObjectID");
        BmobQuery<Comment> query = new BmobQuery<Comment>();

        query.addWhereEqualTo("ZeroItemId", zero_id);

        query.setLimit(50);

        query.findObjects(this, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> object) {
                // TODO Auto-generated method stub

                for (int i = 0; i < object.size(); i++) {
                    commentList.add(object.get(i));

                    commentAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(CommentActivity.this,"数据出错啦"+msg,Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtils.removeActivity(this);
    }
}
