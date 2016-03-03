package com.android.simplereader.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.simplereader.R;
import com.android.simplereader.model.bean.MineList;

import java.util.List;

/**
 * Created by Dragon丶Lz on 2016/2/5.
 */
public class MineAdapter extends ArrayAdapter<MineList> {

    private int resourceId;

    public MineAdapter(Context context, int resource, List<MineList> objects) {
        super(context, resource, objects);
        resourceId =resource;
    }
/**
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MineList mineList =getItem(position);
        String title = mineList.getMineTitle();
        int img = mineList.getMineImg();
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_mine_title = (TextView) convertView.findViewById(R.id.mine_item_title);
            viewHolder.img_mine_img = (ImageView) convertView.findViewById(R.id.mine_item_img_left);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_mine_title.setText(title);
        viewHolder.img_mine_img.setImageResource(img);
        return convertView;

    }
    **/

    private class ViewHolder{
        TextView tv_mine_title;
        ImageView img_mine_img;
    }
}
