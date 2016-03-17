package com.android.simplereader.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.simplereader.R;
import com.android.simplereader.model.bean.Collection;
import com.android.simplereader.ui.activity.CollectionActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Set;

/**
 * Created by Dragon丶Lz on 2016/2/15.
 */
public class CollectionAdapter extends ArrayAdapter<Collection> {

    private int resourceId;
    private Context mContext;

    public CollectionAdapter(Context context, int resource, List<Collection> objects) {
        super(context, resource, objects);
        mContext = context;
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Collection collection =getItem(position);
        String title = collection.getCollectionTitle();
        String date  = collection.getCollectionTime();
        String name = collection.getCollectionUserName();
        String img = collection.getCollectionPic();
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.title_tv = (TextView) convertView.findViewById(R.id.essay_item_title);
            viewHolder.date_tv = (TextView) convertView.findViewById(R.id.essay_item_date);
            viewHolder.userName_tv = (TextView) convertView.findViewById(R.id.essay_item_user);
            viewHolder.content_img = (ImageView) convertView.findViewById(R.id.essay_item_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Set<Integer> positionSet = CollectionActivity.collectionActivirt_instance.positionSet;
        if (positionSet.contains(position)) {
            convertView.setBackground(getContext().getResources().getDrawable(R.drawable.bg_selected));
        } else {
            convertView.setBackground(getContext().getResources().getDrawable(R.drawable.btn_common));
        }

        viewHolder.title_tv.setText(title);
        viewHolder.date_tv.setText(date);
        viewHolder.userName_tv.setText(name);
        Picasso.with(mContext).load(img).into(viewHolder.content_img);

        return convertView;

    }

    private class ViewHolder {
        TextView title_tv;
        TextView date_tv;
        TextView userName_tv;
        ImageView content_img;
    }
}
