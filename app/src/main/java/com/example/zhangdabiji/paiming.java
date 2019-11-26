package com.example.zhangdabiji;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;


public class paiming extends Fragment {
    private ArrayList<Group> gData = null;
    private ArrayList<ArrayList<Item>> iData = null;
    private MyBaseExpandableListAdapter myAdapter ;
    private static final String TAG = "Zhedieshitu";
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

        TextView textView=view.findViewById(R.id.paiming_head);
        textView.setText("第"+"周概述");
        ExpandableListView exlist_lol = view.findViewById(R.id.paiming_exlist_lol1);
        //为列表设置点击事件
        myAdapter=new MyBaseExpandableListAdapter(gData,iData, getContext());
        exlist_lol.setAdapter(myAdapter);
        for (int i=0;i<gData.size();i++){
            iData.add(new ArrayList<Item>());
        }
        if (netChange.detectInternerState(view.getContext())){
        }
        else {
            LinearLayout layout=view.findViewById(R.id.paming_network);
          LinearLayout relativeLayout=view.findViewById(R.id.paming_relativelayout);
            layout.setVisibility(View.VISIBLE);
           relativeLayout.setVisibility(View.GONE);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url("http://47.103.205.169/api/user_list/?token="+MainActivity.token)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.d(TAG, "false ");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                             String responsedata=response.body().string();

                        try {
                            JSONArray array=new JSONArray(responsedata);
                            for (int i=0;i<array.length();i++){
                                JSONObject object=array.getJSONObject(i);
                                ArrayList<Item> arrayItemitem=new ArrayList<>();
                                String group=object.getString("group");
                                String username=object.getString("username");
                                for (int j=0;j<gData.size();j++){
                                    if (group.equals(myAdapter.getGroup(j).getgName())){
                                       arrayItemitem.add(new Item(username));
                                       iData.add(j,arrayItemitem);
                                        Log.d(TAG, ""+ myAdapter.getGroup(i).getgName());

                                    }
                                }

                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    myAdapter.notifyDataSetChanged();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
        exlist_lol. setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return true;
            }
        });
    return view;}
    }

