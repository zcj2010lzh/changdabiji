package com.example.zhangdabiji;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.tencent.smtt.sdk.QbSdk;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Zhedieshitu extends AppCompatActivity {

    private ArrayList<Group> gData = null;
    private ArrayList<ArrayList<Item>> iData = null;
    private ArrayList<Item> lData = null;
    private  int groupsize=0;
    private static final String TAG = "Zhedieshitu";
    private  ArrayList<Item>  myWeekly=new ArrayList<>();
     SharedPreferences preferences;
    String docurl;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhedieshitu);
        Context mContext = Zhedieshitu.this;
        preferences = PreferenceManager.getDefaultSharedPreferences(Zhedieshitu.this);
        //数据准备
        gData = new ArrayList<Group>();
        iData = new ArrayList<ArrayList<Item>>();
        lData = new ArrayList<>();

        QbSdk.initX5Environment(Zhedieshitu.this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。
            }

            @Override
            public void onViewInitFinished(boolean b) {
            }
        });
        //TANK组
        ExpandableListView exlist_lol = findViewById(R.id.exlist_lol1);
        //为列表设置点击事件

        final MyBaseExpandableListAdapter myAdapter = new MyBaseExpandableListAdapter(gData, iData, Zhedieshitu.this);
        exlist_lol.setAdapter(myAdapter);
        if (netChange.detectInternerState(Zhedieshitu.this)){
        }
        else {
            RelativeLayout layout=findViewById(R.id.internetwrong);
            layout.setVisibility(View.VISIBLE);
            exlist_lol.setVisibility(View.GONE);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String token = preferences.getString("token", "");
                OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                Request request = new Request.Builder()
                        .url(" http://47.103.205.169/api/all_summary/?token="+ token)//请求接口。如果需要传参拼接到接口后面。
                        .build();//创建Request 对象
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                        final String responsedata = response.body().string();
//                        Log.d(TAG, ""+responsedata);
                        try {
                            final JSONArray array = new JSONArray(responsedata);
                            for (int i = 0; i < array.length(); i++) {
                                ArrayList<Item>  items=new ArrayList<>();
                                iData.add(items);
                                JSONObject object = array.getJSONObject(i);
                                String title=object.getString("title");
                                String file_type=object.getString("file_type");
                                String ispublic=object.getString("is_public");
                                String url="http://47.103.205.169"+object.getString("file");
                                String created_time=object.getString("created_time");
                                String [] username=object.getString("author").split("-");
                                groupsize=0;
                                for (int j=0;j<gData.size()||gData.size()==0;j++) {
                                    if (gData.size()==0) {
                                        gData.add(new Group(username[username.length - 1], username[0]));
                                        items.add(new Item(title,created_time,url,file_type));
                                        iData.add(j,items);
                                        continue;
                                    }
                                    if (!username[username.length - 1].equals(gData.get(j).name)) {
                                        groupsize++;
                                        if(groupsize==gData.size()){
                                            gData.add(new Group(username[username.length - 1], username[0]));
                                        items.add(new Item(title,created_time,url,file_type));
                                        iData.add(gData.size()-1,items);
                                        break;}
                                    }
                                    if (username[username.length - 1].equals(gData.get(j).name)) {
                                       iData.get(j).add(new Item(title,created_time,url,file_type));
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gData.add(new Group("自己的文档"));
                                iData.add(gData.size()-1,myWeekly);
                                myAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
                Request request1=new Request.Builder()
                        .url("http://47.103.205.169/api/summary/?token="+token)
                        .build();

                       client.newCall(request1).enqueue(new Callback() {
                           @Override
                           public void onFailure(@NotNull Call call, @NotNull IOException e) {

                           }

                           @Override
                           public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                               final String responsedata = response.body().string();
                               try {
                                   final JSONArray array = new JSONArray(responsedata);
                                   for (int i = 0; i < array.length(); i++) {
                                       JSONObject object = array.getJSONObject(i);
                                       String title=object.getString("title");
                                       String file_type=object.getString("file_type");
                                       String url="http://47.103.205.169"+object.getString("file");
                                       String created_time=object.getString("created_time");
                                        myWeekly.add(new Item(title,created_time,url,file_type));
                                       }

                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                       });
            }
        }).start();
        exlist_lol.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        exlist_lol.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                 docurl=   downloadFile(iData.get(groupPosition).get(childPosition).getUrl(),iData.get(groupPosition).get(childPosition).getiName()+"."+iData.get(groupPosition).get(childPosition).getFile_type());
                 //  File file1=new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/tencent/QQfile_recv","test") ;
                //写字符串进word

                return false;
            }
        });
    }
    private String  downloadFile(final String address, final String filename){
        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/tencent/QQfile_recv", filename);
          if (file.exists())
              file.delete();
        Log.d(TAG, ""+file.getAbsolutePath());
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!Zhedieshitu.this.isFinishing()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (progressDialog==null)
                                progressDialog=new ProgressDialog(Zhedieshitu.this);
                            ProgressShow.showProgressDialog(progressDialog);
                            progressDialog.show();
                        }
                    });
                }
                OkGo.<File>get(address)
                        .execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath() +"/tencent/QQfile_recv",filename) {
                            @Override
                            public void onSuccess(Response<File> response) {
                                if (docurl!="") {
                                    ProgressShow.closeProgressDialog(progressDialog);
                                    Intent intent = new Intent(Zhedieshitu.this, WendangJiazaiyemian.class);
                                    intent.putExtra("isDeleting",true);
                                    intent.putExtra("fileurl", docurl);
                                    startActivity(intent);
                                }
                        }
                            @Override
                            public void downloadProgress(Progress progress) {
                                super.downloadProgress(progress);
                                //如何拿到当前的下载进度

                            }
                        });
            }
        }).start();
     return  file.getAbsolutePath();
    }
}