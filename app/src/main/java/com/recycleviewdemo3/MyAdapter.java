package com.recycleviewdemo3;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 类描述：
 * 创建人：xuyaxi
 * 创建时间：2017/8/14 19:13
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<Data.美女Bean> list;
    Context mContext;

    public MyAdapter(List<Data.美女Bean> list, Context context) {
        this.list = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Data.美女Bean d = list.get(position);
        Glide.with(mContext).load(d.getImg()).into(holder.image_item);

        ObjectAnimator anim = ObjectAnimator
                .ofFloat(holder.itemView, "alpha", 0.0F, 1.0F)
                .setDuration(1000);
        anim.start();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image_item;

        public ViewHolder(final View itemView) {
            super(itemView);
            image_item = (ImageView) itemView.findViewById(R.id.image_item);

            image_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "点击当前" + itemView, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
