package com.edityj.trafficcontrol.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edityj.trafficcontrol.R;

import java.util.List;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/1/19
 * description:
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<String> mdata;
    public OnItemClickListener mOnItemClickListener;

    public MyAdapter (List<String> data){
        this.mdata = data;
    }

    //监听接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textTrafficCode.setText(mdata.get(i));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imTrafficIcon;
        public ImageView imIsTrue;
        public TextView textTrafficCode;
        public RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imTrafficIcon = itemView.findViewById(R.id.im_traffic_icon);
            imIsTrue = itemView.findViewById(R.id.im_is_true);
            textTrafficCode = itemView.findViewById(R.id.text_traffic_code);
            relativeLayout = (RelativeLayout)itemView;
            relativeLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener!=null) {
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

}
