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
import com.android.simplereader.model.bean.Essay;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Dragon丶Lz on 2016/1/31.
 */
public class EssayAdapter extends ArrayAdapter<Essay.Res_Body.PageBean.ContentList> {

    private int resourceId;
    private Context mContext;

    public EssayAdapter(Context context, int resource, List<Essay.Res_Body.PageBean.ContentList> objects) {
        super(context, resource, objects);
        mContext = context;
        resourceId = resource;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Essay.Res_Body.PageBean.ContentList essay =getItem(position);
        String title = essay.getEssayTitle();
        String date = essay.getEssayDate();
      //  String url = essay.getShowapi_res_body().getPagebean().getContentlist().get(position).getEssayUrl();
        String pic = essay.getContentImg();
        String userName = essay.getEssayUserName();
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.essay_item_title);
            viewHolder.date = (TextView) convertView.findViewById(R.id.essay_item_date);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.essay_item_user);
            viewHolder.img_content = (ImageView) convertView.findViewById(R.id.essay_item_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(title);
        viewHolder.date.setText(date);
        viewHolder.userName.setText(userName);
        Picasso.with(mContext).load(pic).into(viewHolder.img_content);
        Log.d("EssayProblem--->>","我执行加载了图片————地址是————"+pic);
        return convertView;

    }


    private class ViewHolder {
        TextView title;
        TextView date;
        TextView userName;
        ImageView img_content;
    }
}
