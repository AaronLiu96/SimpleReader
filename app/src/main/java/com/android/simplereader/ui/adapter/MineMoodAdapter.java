package com.android.simplereader.ui.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.simplereader.R;
import com.android.simplereader.model.bean.Collection;
import com.android.simplereader.model.bean.Zero;
import com.android.simplereader.ui.activity.CommentActivity;
import com.android.simplereader.util.BmobUtil;
import com.android.simplereader.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Dragon丶Lz on 2016/2/17.
 */
public class MineMoodAdapter extends RecyclerView.Adapter<MineMoodAdapter.MyViewHold>{


    private List<Zero> mineData;
    private Context mContext;

    public MineMoodAdapter(List<Zero> datas,Context context){
     //  Log.d("MineMoodApapter--->>",datas.get(0).getZeroDate());
        mineData = datas;
        mContext =context;
      //  Log.d("MineMoodApapter--->>",mineData.get(0).getZeroDate());
    }



    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("MineMoodApapter--->>","我开始执行了onCreateViewHolder");
        MyViewHold hold = new MyViewHold(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mine_item,parent,false));
        return hold;
    }

    @Override
    public void onBindViewHolder(MyViewHold holder, final int position) {
        Log.d("MineMoodApapter--->>","我开始执行了onBindViewHolder");
        holder.time_tv.setText(mineData.get(position).getZeroDate());

        holder.data_tv.setText("I say:"+mineData.get(position).getZeroContent());
        Log.d("MineMoodApapter--->>", mineData.get(position).getZeroContent());
        holder.mine_tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.actionStart(mContext, mineData.get(position).getObjectId());
            }
        });
        holder.mine_tv_good_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUtil.UpdateGoodZero(mContext);

            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("MineMoodApapter--->>",mineData.size()+"");
        return mineData.size();
    }


    class MyViewHold extends RecyclerView.ViewHolder{

        TextView time_tv;
        TextView data_tv;
        ImageView mine_tv_good_btn;
        TextView mine_tv_good;
        ImageView mine_tv_comment;
        public MyViewHold(View itemView) {
            super(itemView);
            time_tv = (TextView) itemView.findViewById(R.id.activity_mine_item_time);
            data_tv = (TextView) itemView.findViewById(R.id.activity_mine_item_data);
            mine_tv_good = (TextView) itemView.findViewById(R.id.mine_tv_good);
            mine_tv_comment = (ImageView) itemView.findViewById(R.id.mine_tv_comment);
            mine_tv_good_btn = (ImageView) itemView.findViewById(R.id.mine_tv_good_btn);

        }
    }
}
