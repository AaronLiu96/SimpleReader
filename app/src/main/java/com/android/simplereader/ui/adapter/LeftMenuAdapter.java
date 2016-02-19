package com.android.simplereader.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.simplereader.R;
import com.android.simplereader.model.bean.LeftMenu;

import java.util.List;

/**
 * Created by Dragon丶Lz on 2016/1/29.
 */
public class LeftMenuAdapter extends ArrayAdapter<LeftMenu> {

    private int resourceId;

    public LeftMenuAdapter(Context context, int resource, List<LeftMenu> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LeftMenu leftMenu = getItem(position);

        ViewHolder viewHolder;
        if (convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.ListItem = (TextView) convertView.findViewById(R.id.LeftMenu_List_item);
            convertView.setTag(viewHolder);
        }else {

            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ListItem.setText(leftMenu.getName());
        return convertView;
    }

    private class ViewHolder{
        TextView ListItem;
    }
}
