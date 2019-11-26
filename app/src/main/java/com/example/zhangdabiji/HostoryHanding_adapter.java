package com.example.zhangdabiji;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HostoryHanding_adapter extends RecyclerView.Adapter<HostoryHanding_adapter.Weekly_holder> {
    private ArrayList<Weekly_sketle> weekly_sketles_list;
    HostoryHanding_adapter(ArrayList<Weekly_sketle> weekly_sketles_list){
       this.weekly_sketles_list=weekly_sketles_list;
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
    public Weekly_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.historyhanding_item,parent,false);
       Weekly_holder holder=new Weekly_holder(view);
       return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Weekly_holder holder, final int position) {
        Weekly_sketle sketle=weekly_sketles_list.get(position);
        holder.week.setText(sketle.getWeek());
        holder.Weeklyname.setText(sketle.getWeekly_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v,position);
                }
            }
        });

            if (!sketle.getIspublic())
                holder.lock.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return weekly_sketles_list.size();
    }

    static class Weekly_holder extends RecyclerView.ViewHolder{
         TextView week,Weeklyname;
         ImageView lock;
        public Weekly_holder(@NonNull View itemView) {
            super(itemView);
            week=itemView.findViewById(R.id.historyHanding_week);
            lock=itemView.findViewById(R.id.lock);
            Weeklyname=itemView.findViewById(R.id.historyHanding_Weekly_name);
        }
    }
}
