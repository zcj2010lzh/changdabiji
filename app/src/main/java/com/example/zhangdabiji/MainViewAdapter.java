package com.example.zhangdabiji;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.MainHolder> {
    ArrayList<MainviewBean> mainviewBeans;
   MainViewAdapter(ArrayList<MainviewBean> mainviewBeans){
       this.mainviewBeans=mainviewBeans;
    }
    private OnItemClickListener onItemClickListener = null;

    //setter方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //回调接口
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mainview_item,parent,false);
        return new MainHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, final int position) {
        MainviewBean bean=mainviewBeans.get(position);
        holder.groupName.setText(bean.getGroupName());
        holder.biLi.setText(bean.getBili());
        holder.bar.setProgress(bean.getProgress());
        holder.bar.setMax(bean.getMax());
        holder.bar .setClickable(false);
        holder.bar .setSelected(false);
        holder.bar .setFocusable(false);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainviewBeans.size();
    }

    static class MainHolder extends RecyclerView.ViewHolder{
        TextView groupName,biLi;
        SeekBar bar;
        MainHolder(@NonNull View itemView) {
            super(itemView);
           groupName=itemView.findViewById(R.id.mainview_groupname);
           biLi=itemView.findViewById(R.id.main_bili);
           bar=itemView.findViewById(R.id.mainview_bar);
        }
    }
}
