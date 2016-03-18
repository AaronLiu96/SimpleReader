package com.android.simplereader.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.simplereader.R;
import com.android.simplereader.model.bean.Zero;
import com.android.simplereader.model.callback.OnItemClickListener;
import com.android.simplereader.ui.activity.CommentActivity;
import com.android.simplereader.util.BmobUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragon丶Lz on 2016/2/1.
 */
public class ZeroAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;
    private List<Zero> dataLists;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }



    public ZeroAdapter(Context context, List<Zero> objects) {

        mContext =context;

        dataLists = objects;
    }


    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getHeaderView(){
        return mHeaderView;
    }

    public void  addData(List<Zero> datas){
        dataLists.addAll(datas);
        notifyDataSetChanged();

    }
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null){
            return TYPE_NORMAL;
        }else if (position ==0){
            return TYPE_HEADER;
        }else {
            return TYPE_NORMAL;
        }
    }
    /**
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

     **/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ZeroHolder(mHeaderView);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_zero_item, parent, false);
        return new ZeroHolder(layout);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);
        final Zero zero = dataLists.get(pos);
        if(holder instanceof ZeroHolder) {
            String ZeroName = zero.getZeroName();
            String ZeroContent = zero.getZeroContent();
            String ZeroDate = zero.getZeroDate();
            Integer ZeroGood = zero.getZeroGood();

            final String ZeroObjectId = zero.getObjectId();
            ((ZeroHolder) holder).ZeroGood_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommentActivity.actionStart(mContext,ZeroObjectId);
                }
            });
            ((ZeroHolder) holder).ZeroGood_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BmobUtil.UpdateGoodZero(mContext, ZeroObjectId);
                    ((ZeroHolder) holder).ZeroGood_tv.setText(((ZeroHolder) holder).ZeroGood_tv.getText() + "+1");

                }
            });
            ((ZeroHolder) holder).ZeroName_tv.setText(ZeroName);
            ((ZeroHolder) holder).ZeroGood_tv.setText(ZeroGood.toString());
            ((ZeroHolder) holder).ZeroContent_tv.setText(ZeroContent);
            ((ZeroHolder) holder).ZeroDate_tv.setText(ZeroDate);
            if(mListener == null) return;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(pos, zero);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataLists == null ? 0 : dataLists.size();
    }


    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }



    private class ZeroHolder extends RecyclerView.ViewHolder{
        TextView ZeroName_tv;
        TextView ZeroContent_tv;
        TextView ZeroDate_tv;
        TextView ZeroGood_tv;
        ImageView ZeroGood_btn;
        ImageView ZeroGood_comment;

        public ZeroHolder(View itemView) {
            super(itemView);
            if (itemView== mHeaderView) return;
            ZeroContent_tv = (TextView) itemView.findViewById(R.id.zero_tv_content);
            ZeroDate_tv = (TextView) itemView.findViewById(R.id.zero_tv_date);
            ZeroGood_btn = (ImageView) itemView.findViewById(R.id.zero_tv_good_btn);
            ZeroGood_tv = (TextView) itemView.findViewById(R.id.zero_tv_good);
            ZeroName_tv = (TextView) itemView.findViewById(R.id.zero_tv_name);
            ZeroGood_comment = (ImageView) itemView.findViewById(R.id.zero_tv_comment);

        }
    }
}
