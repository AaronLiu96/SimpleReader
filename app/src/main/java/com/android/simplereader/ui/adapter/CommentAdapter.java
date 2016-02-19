package com.android.simplereader.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.simplereader.R;
import com.android.simplereader.model.bean.Comment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Dragon丶Lz on 2016/2/2.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {

    private int resourceId;
    private Context mCOntext;
    public CommentAdapter(Context context, int resource, List<Comment> objects) {
        super(context, resource, objects);
        resourceId = resource;
        mCOntext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = getItem(position);

        String comment_data = comment.getComment_data();
        String comment_name = comment.getComment_name();
        String comment_date = comment.getComment_date();
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_comment_data = (TextView) convertView.findViewById(R.id.comment_item_data);
            viewHolder.tv_comment_date = (TextView) convertView.findViewById(R.id.comment_item_date);
            viewHolder.tv_comment_name = (TextView) convertView.findViewById(R.id.comment_item_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_comment_data.setText(comment_data);
        viewHolder.tv_comment_date.setText(comment_date);
        viewHolder.tv_comment_name.setText(comment_name);
        return convertView;
    }



    private class ViewHolder{
        TextView tv_comment_data;
        TextView tv_comment_date;
        TextView tv_comment_name;
    }
}
