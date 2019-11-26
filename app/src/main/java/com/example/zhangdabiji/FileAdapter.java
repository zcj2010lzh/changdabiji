package com.example.zhangdabiji;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
    static List<FileBean> mfileList;
    List<String> fileName = new ArrayList<>();
    private static SharedPreferences.Editor editor;
    private static SharedPreferences preferences;

    protected static List<Integer> positionSet = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fileImage;
        TextView fileName;
        CheckBox fileCheck;
        TextView fileDate;

        public ViewHolder(View view) {
            super(view);
            fileImage = view.findViewById(R.id.file_item_img);
            fileName = view.findViewById(R.id.file_item_name);
            fileCheck = view.findViewById(R.id.file_item_check);
            fileDate = view.findViewById(R.id.file_item_date);

        }
    }

    @SuppressLint("CommitPrefEdits")
    @NotNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //做出逻辑
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fileitemlayout, parent, false);

        final ViewHolder holder = new ViewHolder(view);

        editor = PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit();
        preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        holder.setIsRecyclable(true);
        LinearLayout item = view.findViewById(R.id.item_layout);
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
            @SuppressLint("RestrictedApi")
            @Override
            public boolean onLongClick(View view) {
                if (WendangYemian.floatingActionButton.getVisibility()==View.GONE)
                    WendangYemian.floatingActionButton.setVisibility(View.VISIBLE);
             WendangYemian.danxuam = false;
                mfileList.get(holder.getAdapterPosition()).setCheched(true);
                fileName.add(mfileList.get(holder.getAdapterPosition()).getPath());
                holder.fileCheck.setChecked(true);
                holder.fileCheck.setVisibility(View.VISIBLE);
                positionSet.add(holder.getAdapterPosition());
                //MainActivity.sanchu.setVisibility(View.VISIBLE);
                return false;
            }
        });
        holder.fileCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isChecked()) {
                    compoundButton.setChecked(false);
                    compoundButton.setVisibility(View.GONE);
                }
            }
        });
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (WendangYemian.danxuam) {
                    int position = holder.getAdapterPosition();
                    FileBean file = mfileList.get(position);
                    Intent intent = new Intent(view.getContext(), WendangJiazaiyemian.class);
                    intent.putExtra("fileurl", file.getrealPath());
                    view.getContext().startActivity(intent);
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //绑定控件
        FileBean file = mfileList.get(position);
        holder.fileImage.setImageResource(file.getIconId());
        holder.fileName.setText(file.getPath());
        holder.fileDate.setText(file.getFiledate());
        if (mfileList.get(position).getCheched()) {
            holder.fileCheck.setVisibility(View.VISIBLE);
            holder.fileCheck.setChecked(true);
        }
        else {
            holder.fileCheck.setVisibility(View.GONE);
            holder.fileCheck.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return mfileList.size();
    }

    FileAdapter(List<FileBean> fileList) {
        mfileList = fileList;
    }


    static void delete() {
        String s;
        s = preferences.getString("bukandewendang", "");
        for (int j = 0; j < positionSet.size() - 1; j++) {
            for (int h = 0; h < positionSet.size() - 1; h++)
                if (positionSet.get(h) > positionSet.get(h + 1)) {
                    int z;
                    z = positionSet.get(h + 1);
                    positionSet.set(h + 1, positionSet.get(h));
                    positionSet.set(h, z);
                }
        }
        for (int i = 0; i < positionSet.size(); i++) {
            s = s + mfileList.get(positionSet.get(positionSet.size() - 1 - i)).getPath() + ",";

            mfileList.get(positionSet.size()-1-i).setCheched(false);
            mfileList.remove((int) positionSet.get(positionSet.size() - 1 - i));

            editor.putString("bukandewendang", s);
            editor.apply();
        }
//        for (int z=0;z<mfileList.size();z++){
//            ViewHolder holder;
//            holder= (ViewHolder) WendangYemian.recyclerView.findViewHolderForAdapterPosition(z);
//            if (!mfileList.get(z).getCheched()){
//
//                assert holder != null;
//                holder.fileCheck.setVisibility(View.GONE);
//                holder.fileCheck.setChecked(false);
//            }
//            assert holder != null;
//            if (holder.fileCheck.getVisibility()==View.VISIBLE)
//                holder.fileCheck.setVisibility(View.GONE);
//        }
      /*  public  static  void  quanxuan(Boolean x){
       if (x){
           positionSet.clear();
           for(int i=0;i<mfileList.size();i++){
               positionSet.add(i);
               View view1=group.getChildAt(i);
               ViewHolder holder=new ViewHolder(view1);
               holder.fileCheck.setVisibility(View.VISIBLE);
               holder.fileCheck.setChecked(true);
           }
       }
       else {
           for(int i=0;i<mfileList.size();i++){
               View view=group.getChildAt(i);
               ViewHolder holder=new ViewHolder(view);
               holder.fileCheck.setVisibility(View.INVISIBLE);
               holder.fileCheck.setChecked(false);
           }
       }
    }*/

    }
}