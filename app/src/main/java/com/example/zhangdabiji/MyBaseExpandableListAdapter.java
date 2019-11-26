package com.example.zhangdabiji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<Group> gData;
    private ArrayList<ArrayList<Item>> iData;
    private Context mContext;

    public MyBaseExpandableListAdapter(ArrayList<Group> gData, ArrayList<ArrayList<Item>> iData, Context mContext) {
        this.gData = gData;
        this.iData = iData;
        this.mContext = mContext;
    }

    public int getGroupCount() {
        return gData.size();
    }


    public int getChildrenCount(int groupPosition) {
        return iData.get(groupPosition).size();
    }


    public Group getGroup(int groupPosition) {
        return gData.get(groupPosition);
    }

    public Item getChild(int groupPosition, int childPosition) {
        return iData.get(groupPosition).get(childPosition);
    }


    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public boolean hasStableIds() {
        return false;
    }

    //取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ViewHolderGroup groupHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_group, parent, false);
            groupHolder = new ViewHolderGroup();
            groupHolder.tv_group_name =  convertView.findViewById(R.id.tv_group_name);
            groupHolder.tv_group_zu=convertView.findViewById(R.id.tv_group_zuming);
            convertView.setTag(groupHolder);
        }else{
            groupHolder = (ViewHolderGroup) convertView.getTag();
        }
        groupHolder.tv_group_name.setText(gData.get(groupPosition).getgName());
        groupHolder.tv_group_zu.setText(gData.get(groupPosition).getZu());
        return convertView;
    }

    //取得显示给定分组给定子位置的数据用的视图

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderItem itemHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_item, parent, false);
            itemHolder = new ViewHolderItem();
          //  itemHolder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
            itemHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            itemHolder.tv_date=convertView.findViewById(R.id.tv_item_date);
            convertView.setTag(itemHolder);
        }else{
            itemHolder = (ViewHolderItem) convertView.getTag();
        }
       // itemHolder.img_icon.setImageResource(iData.get(groupPosition).get(childPosition).getiId());
        itemHolder.tv_name.setText(iData.get(groupPosition).get(childPosition).getiName());
        itemHolder.tv_date.setText(iData.get(groupPosition).get(childPosition).getDate());
        return convertView;
    }

    //设置子列表是否可选中
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    private static class ViewHolderGroup{
        private TextView tv_group_name;
        private  TextView tv_group_zu;
    }

    private static class ViewHolderItem{
      //  private ImageView img_icon;
        private TextView tv_name;
        private TextView tv_date;
    }

}

