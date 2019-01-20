package com.edityj.trafficcontrol.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edityj.trafficcontrol.R;
import com.edityj.trafficcontrol.pojo.ITEMDATA;

import java.util.List;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/1/19
 * description:
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<ITEMDATA> data;
    private int temp=0;
//    private List<String> mdata;
    public OnItemClickListener mOnItemClickListener;

    public MyAdapter (List<ITEMDATA> data){
        this.data = data;
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
        Log.e("资源配置次数:", String.valueOf(i));
//        Log.e("读取数据d:", data.get(i).getDanger());
//        Log.e("读取数据r:", data.get(i).getRemind());
//        Log.e("读取数据s:", data.get(i).getSpeed());
        //RecyclerView禁止复用
        viewHolder.setIsRecyclable(false);
        if(data.get(i).getDanger()!=null){
            viewHolder.textSpeed.setVisibility(View.GONE);
            viewHolder.textRemind.setVisibility(View.GONE);

            Log.e("配置危险:", String.valueOf(i)+","+data.get(i).getDanger());
            viewHolder.textDanger.setText(data.get(i).getDanger());
        }

        if(data.get(i).getRemind()!=null){
            viewHolder.textSpeed.setVisibility(View.GONE);
            viewHolder.textDanger.setVisibility(View.GONE);
            Log.e("配置提醒:", String.valueOf(i));
            viewHolder.textRemind.setText(data.get(i).getRemind());
        }

        if(data.get(i).getSpeed()!=null){
            viewHolder.textRemind.setVisibility(View.GONE);
            viewHolder.textDanger.setVisibility(View.GONE);
            Log.e("配置速度:", String.valueOf(i));
            viewHolder.textSpeed.setText(data.get(i).getSpeed());
        }

        //绑定图片数据
        if(data.get(i).getIcon()!=0){
            viewHolder.textRemind.setVisibility(View.GONE);
            viewHolder.textDanger.setVisibility(View.GONE);
            viewHolder.textSpeed.setVisibility(View.GONE);
            viewHolder.imTrafficIcon.setVisibility(View.VISIBLE);

            viewHolder.imTrafficIcon.setImageResource(data.get(i).getIcon());
        }
        if(data.get(i).getPlay()!=0){
            viewHolder.imIsTrue.setVisibility(View.VISIBLE);
            viewHolder.imIsTrue.setImageResource(data.get(i).getPlay());
        }else{
            viewHolder.imIsTrue.setVisibility(View.GONE);
        }
        if(data.get(i).getDelete()!=0){
            viewHolder.imIsDelete.setVisibility(View.VISIBLE);
            viewHolder.imIsDelete.setImageResource(data.get(i).getDelete());
        }else{
            viewHolder.imIsDelete.setVisibility(View.GONE);
        }
//        if(i<data.getWarning().size()){
//            viewHolder.textTrafficCode.setText(data.getWarning().get(i));
//            viewHolder.imTrafficIcon.setVisibility(View.GONE);
//        }else if(i<data.getRemind().size()+data.getWsize()){
//            Log.e("Remind资源配置索引:", String.valueOf(i-data.getWsize()));
//            viewHolder.textTrafficCode.setText(data.getRemind().get(i-data.getWsize()));
//            viewHolder.imTrafficIcon.setVisibility(View.GONE);
//        }else if(i<data.getSpeed().size()+data.getWRsize()){
//            viewHolder.textTrafficCode.setText(data.getSpeed().get(i-data.getWRsize()));
//            viewHolder.imTrafficIcon.setVisibility(View.GONE);
//        }else if(i<data.getIcon().size()+data.getWRSsize()){
//            viewHolder.imTrafficIcon.setVisibility(View.VISIBLE);
//            viewHolder.textTrafficCode.setVisibility(View.GONE);
//            viewHolder.imTrafficIcon.setImageResource(data.getIcon().get(i-data.getWRSsize()));
//        }
//        viewHolder.textTrafficCode.setText(mdata.get(i));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imTrafficIcon;
        public ImageView imIsTrue;
        public ImageView imIsDelete;

        public TextView textDanger;
        public TextView textRemind;
        public TextView textSpeed;

        public RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //绑定图片控件
            imTrafficIcon = itemView.findViewById(R.id.im_traffic_icon);
            imIsTrue = itemView.findViewById(R.id.im_is_true);
            imIsDelete = itemView.findViewById(R.id.im_delete);
            //绑定文字控件
            textDanger = itemView.findViewById(R.id.text_danger);
            textRemind = itemView.findViewById(R.id.text_remind);
            textSpeed = itemView.findViewById(R.id.text_speed);

            relativeLayout = (RelativeLayout)itemView;
            relativeLayout.setOnClickListener(this);
            imIsDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener!=null) {
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

}
