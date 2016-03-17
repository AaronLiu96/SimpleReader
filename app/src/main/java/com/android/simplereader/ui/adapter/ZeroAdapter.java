package com.android.simplereader.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.simplereader.R;
import com.android.simplereader.model.bean.Zero;
import com.android.simplereader.ui.activity.CommentActivity;
import com.android.simplereader.util.BmobUtil;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Dragon丶Lz on 2016/2/1.
 */
public class ZeroAdapter  extends ArrayAdapter<Zero> {

    private int resourceId;
    private Context mContext;


    public ZeroAdapter(Context context, int resource, List<Zero> objects) {
        super(context, resource, objects);
        mContext =context;
        resourceId =resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zero zero = getItem(position);
        String ZeroName = zero.getZeroName();
        String ZeroContent = zero.getZeroContent();
        String ZeroDate = zero.getZeroDate();
        Integer ZeroGood = zero.getZeroGood();

        final String ZeroObjectId = zero.getObjectId();
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.ZeroContent_tv = (TextView) convertView.findViewById(R.id.zero_tv_content);
                    viewHolder.ZeroDate_tv = (TextView) convertView.findViewById(R.id.zero_tv_date);
                    viewHolder.ZeroGood_btn = (ImageView) convertView.findViewById(R.id.zero_tv_good_btn);
                    viewHolder.ZeroGood_tv = (TextView) convertView.findViewById(R.id.zero_tv_good);
                    viewHolder.ZeroName_tv = (TextView) convertView.findViewById(R.id.zero_tv_name);
                    viewHolder.ZeroGood_comment = (ImageView) convertView.findViewById(R.id.zero_tv_comment);
                    viewHolder.ZeroGood_comment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                    CommentActivity.actionStart(mContext,ZeroObjectId);
                }
            });
            viewHolder.ZeroGood_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BmobUtil.UpdateGoodZero(mContext,ZeroObjectId);
                    viewHolder.ZeroGood_tv.setText(viewHolder.ZeroGood_tv.getText()+"+1");

                }
            });
            convertView.setTag(viewHolder);


        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ZeroName_tv.setText(ZeroName);
        viewHolder.ZeroGood_tv.setText(ZeroGood.toString());
        viewHolder.ZeroContent_tv.setText(ZeroContent);
        viewHolder.ZeroDate_tv.setText(ZeroDate);


        return convertView;

    }


    private class ViewHolder{
        TextView ZeroName_tv;
        TextView ZeroContent_tv;
        TextView ZeroDate_tv;
        TextView ZeroGood_tv;
        ImageView ZeroGood_btn;
        ImageView ZeroGood_comment;
    }
}
