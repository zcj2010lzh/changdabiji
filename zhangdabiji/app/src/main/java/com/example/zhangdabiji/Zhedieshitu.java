package com.example.zhangdabiji;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Zhedieshitu extends AppCompatActivity {

    private ArrayList<Group> gData = null;
    private ArrayList<ArrayList<Item>> iData = null;
    private ArrayList<Item> lData = null;
    private Context mContext;
    private ExpandableListView exlist_lol;
    private MyBaseExpandableListAdapter myAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhedieshitu);
        mContext = Zhedieshitu.this;


        //数据准备
        gData = new ArrayList<Group>();
        iData = new ArrayList<ArrayList<Item>>();
        gData.add(new Group("AD"));
         gData.add(new Group("AP"));
        gData.add(new Group("zx"));

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
        exlist_lol = findViewById(R.id.exlist_lol1);
        //为列表设置点击事件
        myAdapter=new MyBaseExpandableListAdapter(gData,iData, Zhedieshitu.this);
        exlist_lol.setAdapter(myAdapter);
        exlist_lol. setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(mContext, "你点击了：" + iData.get(groupPosition).get(childPosition).getiName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });


    }
    /*
    *     <ImageView
        android:id="@+id/img_icon"
        android:layout_width="48dp"
        android:layout_height="48dp"
        android:focusable="false"/>*/
}