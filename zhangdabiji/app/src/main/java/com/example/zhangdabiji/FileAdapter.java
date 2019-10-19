package com.example.zhangdabiji;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
    private List<FileBean> mfileList;
    SharedPreferences.Editor  editor;
    SharedPreferences preferences;
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fileImage;
        TextView fileName;
        public ViewHolder(View view) {
            super(view);
            fileImage = view.findViewById(R.id.file_item_img);
            fileName = view.findViewById(R.id.file_item_name);
        }
    }
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        //做出逻辑
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fileitemlayout, parent, false);
      final ViewHolder holder = new ViewHolder(view);
        LinearLayout item=view.findViewById(R.id.item_layout);
        editor=  PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit();
        preferences=PreferenceManager.getDefaultSharedPreferences(view.getContext());

      /*  item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
             /*   try{
                    int position = holder.getAdapterPosition();
                     FileBean file = mfileList.get(position);
                    //  sdcard/test2.docx为本地doc文件的路径

                    Intent intent =  OpenFiles.getWordFileIntent(file.getrealPath());
                   view.getContext(). startActivity(intent);
                }catch (Exception e){
                    //没有安装第三方的软件会提示
                    Toast toast = Toast.makeText(view.getContext(), "没有找到打开该文件的应用程序", Toast.LENGTH_SHORT);
                    toast.show();
                }


                return false;
            }
        });*/
        item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String s=preferences.getString("bukandewendang","");
                int position = holder.getAdapterPosition();
                    s=s+position+",";
             //   Toast.makeText(view.getContext(), s, Toast.LENGTH_SHORT).show();
                    editor.putString("bukandewendang",s);
                    editor.apply();
                    mfileList.remove(position);
                    notifyDataSetChanged();
                return false;
            }
        });
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                FileBean file = mfileList.get(position);
                Intent intent=new Intent(view.getContext(),WendangJiazaiyemian.class);
                intent.putExtra("fileurl",file.getrealPath());
                view.getContext(). startActivity(intent);
            }
        });
       /* holder.fileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
               FileBean file = mfileList.get(position);
                Toast.makeText(view.getContext(), "你点击了View"+ file.getPath(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.fileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
              FileBean file = mfileList.get(position);
                Toast.makeText(view.getContext(), "你点击了图片"+ file.getPath(), Toast.LENGTH_SHORT).show();
            }
        });*/
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //绑定控件
        FileBean file = mfileList.get(position);
        holder.fileImage.setImageResource(file.getIconId());
        holder.fileName.setText(file.getPath());
    }

    @Override
    public int getItemCount() {
        return mfileList.size();
    }

    public FileAdapter(List<FileBean> fileList) {
        mfileList = fileList;
    }


    }
