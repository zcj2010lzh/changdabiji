package com.example.zhangdabiji;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
    protected        static List<FileBean> mfileList;
  private   static SharedPreferences.Editor  editor;
 private    static SharedPreferences preferences;
  private  static String tag="MainActivity111111";
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
            fileCheck=view.findViewById(R.id.file_item_check);
            fileDate=view.findViewById(R.id.file_item_date);

        }
    }
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        //做出逻辑
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fileitemlayout, parent, false);
   final ViewHolder     holder= new ViewHolder(view);
        editor=  PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit();
        preferences=PreferenceManager.getDefaultSharedPreferences(view.getContext());
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
            @Override
            public boolean onLongClick(View view) {
                MainActivity.danxuam=false;
                positionSet.add(holder.getAdapterPosition());
                holder.fileCheck.setChecked(true);
                holder.fileCheck.setVisibility(View.VISIBLE);
               MainActivity.l1.setVisibility(View.VISIBLE);
               MainActivity.l2.setVisibility(View.GONE);
                return false;
            }
        });
        holder.fileCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isChecked()){
                    compoundButton.setChecked(false);
                    compoundButton.setVisibility(View.GONE);
                }
            }
        });
item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (MainActivity. danxuam){
                   int position = holder.getAdapterPosition();
                   FileBean file = mfileList.get(position);
                   Intent intent=new Intent(view.getContext(),WendangJiazaiyemian.class);
                   intent.putExtra("fileurl",file.getrealPath());
                   view.getContext(). startActivity(intent);
               }
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
        holder.fileDate.setText(file.getFiledate());
    }

    @Override
    public int getItemCount() {
        return mfileList.size();
    }

    public FileAdapter(List<FileBean> fileList) {
        mfileList = fileList;
    }


    public  static  void  delete(){
        String s;
        s=preferences.getString("bukandewendang","");
        Log.d(tag,""+mfileList.size());
        for(int i=0;i<positionSet.size();i++)
            Log.d(tag, ""+String.valueOf(positionSet.size()-1-i));
        for(int i=0;i<positionSet.size();i++){
            s=s+positionSet.get(positionSet.size()-1-i)+",";
            mfileList.remove(positionSet.size()-1-i);
        }

        editor.putString("bukandewendang",s);
        editor.apply();
    }
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