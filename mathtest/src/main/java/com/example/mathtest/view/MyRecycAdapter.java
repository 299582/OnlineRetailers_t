package com.example.mathtest.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mathtest.R;
import com.example.mathtest.model.NewsBean;

import java.util.List;

public class MyRecycAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<NewsBean.DataBean> list;
    private final int TYPE_ONE = 0;
    private final int TYPE_TWO = 1;
    private OnItemClickListener mItemClickListener;

    public MyRecycAdapter(Context context, List<NewsBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    //item的回调接口
    public interface OnItemClickListener{
        void onItemClick(View view,int Position);
    }

    //对外暴露一个设置点击监听器的方法，其中传入需要OnItemClickListener接口
    //定义一个设置点击监听器的方法
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if(position%3==0){
            return TYPE_ONE;
        }else{
            return TYPE_TWO;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_ONE){
            View view = View.inflate(context, R.layout.item_recyc_1,null);
            ViewHolder1 holder1 = new ViewHolder1(view);
            return holder1;
        }else{
            View view = View.inflate(context,R.layout.item_recyc_2,null);
            ViewHolder2 holder2 = new ViewHolder2(view);
            return holder2;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickListener != null){
                    mItemClickListener.onItemClick(v,position);
                }
            }
        });
        int itemViewType = getItemViewType(position);
        if(itemViewType == TYPE_ONE){
            ViewHolder1 holder1 = (ViewHolder1) holder;
            holder1.title.setText(list.get(position).getBrief());
            holder1.time.setText(list.get(position).getDate());
            holder1.from.setText(list.get(position).getSource());
            Glide.with(context).load(list.get(position).getLbimg().get(0).getSrc()).into(holder1.img_item_1);
//            holder1.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(mItemClickListener != null){
//                        mItemClickListener.onItemClick(v,position);
//                    }
//                }
//            });
        }else{
            ViewHolder2 holder2 = (ViewHolder2) holder;
            holder2.title2.setText(list.get(position).getBrief());
            holder2.from2.setText(list.get(position).getSource());
            holder2.time2.setText(list.get(position).getDate());
            Glide.with(context).load(list.get(position).getLbimg().get(0).getSrc()).into(holder2.img1_item_2);
            Glide.with(context).load(list.get(position).getMiniimg().get(0).getSrc()).into(holder2.img2_item_2);
            Glide.with(context).load(list.get(position).getMiniimg02().get(0).getSrc()).into(holder2.img3_item_2);
//            holder2.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(mItemClickListener != null){
//                        mItemClickListener.onItemClick(v,position);
//                    }
//                }
//            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        private ImageView img_item_1;
        private TextView title,time,from;
        public ViewHolder1(View itemView) {
            super(itemView);
            img_item_1 = itemView.findViewById(R.id.imageView_item_1);
            title = itemView.findViewById(R.id.textView_item_1_title);
            time = itemView.findViewById(R.id.textView_item_1_time);
            from = itemView.findViewById(R.id.textView_item_1_from);
        }
    }
    public class ViewHolder2 extends RecyclerView.ViewHolder {
        private ImageView img1_item_2,img2_item_2,img3_item_2;
        private TextView title2,time2,from2;
        public ViewHolder2(View itemView) {
            super(itemView);

            img1_item_2 = itemView.findViewById(R.id.imageView1_item_2);
            img2_item_2 = itemView.findViewById(R.id.imageView2_item_2);
            img3_item_2 = itemView.findViewById(R.id.imageView3_item_2);
            time2 = itemView.findViewById(R.id.textView_item_2_time);
            title2 = itemView.findViewById(R.id.textView_item_2_title);
            from2 = itemView.findViewById(R.id.textView_item_2_from);
        }
    }
}
