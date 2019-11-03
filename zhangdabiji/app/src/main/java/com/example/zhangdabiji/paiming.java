package com.example.zhangdabiji;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class paiming extends Fragment {
    private ArrayList<Group> gData = null;
    private ArrayList<ArrayList<Item>> iData = null;
    private ArrayList<Item> lData = null;
    private ExpandableListView exlist_lol;
    private MyBaseExpandableListAdapter myAdapter ;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_paiming,container,false);

        //数据准备
        gData = new ArrayList<Group>();
        iData = new ArrayList<ArrayList<Item>>();
        gData.add(new Group("长大在线(18级)"));
        gData.add(new Group("安卓组"));
        gData.add(new Group("PHP组"));
        gData.add(new Group("UI组"));
        gData.add(new Group("运维组"));
        gData.add(new Group("运营组"));
        gData.add(new Group("前端组"));
        lData = new ArrayList<Item>();

        lData.add(new Item("剑圣"));
        lData.add(new Item("德莱文"));
        lData.add(new Item("男枪"));
        lData.add(new Item("韦鲁斯"));
        iData.add(lData);
        //AP组
        lData = new ArrayList<Item>();
        lData.add(new Item( "提莫"));
        lData.add(new Item( "安妮"));
        lData.add(new Item( "天使"));
        lData.add(new Item("泽拉斯"));
        lData.add(new Item( "狐狸"));
        iData.add(lData);
        //TANK组
        lData = new ArrayList<Item>();
        lData.add(new Item( "诺手"));
        lData.add(new Item("德邦"));
        lData.add(new Item( "奥拉夫"));
        lData.add(new Item("龙女"));
        lData.add(new Item("狗熊"));
        iData.add(lData);
        exlist_lol = view. findViewById(R.id.paiming_exlist_lol1);
        //为列表设置点击事件
        myAdapter=new MyBaseExpandableListAdapter(gData,iData, getContext());
        exlist_lol.setAdapter(myAdapter);
        exlist_lol. setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getContext(), "你点击了：" + iData.get(groupPosition).get(childPosition).getiName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    return view;}
    }

