package com.example.zhangdabiji;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
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

public class HistoryHandind extends AppCompatActivity {
   ProgressDialog progressDialog;
   String docurl;
    String file_type;
    Boolean frommainview=false;
    HostoryHanding_adapter adapter;
    SmartRefreshLayout refreshLayout;
    RelativeLayout relativeLayout;
    int position;
    private static final String TAG = "HistoryHandind";
   ArrayList<Weekly_sketle> weekly_sketles=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_handind);
        QbSdk.initX5Environment(HistoryHandind.this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。
            }

            @Override
            public void onViewInitFinished(boolean b) {
            }
        });
        refreshLayout=findViewById(R.id.historyHanding_smartrefresh);
        final RecyclerView recyclerView=findViewById(R.id.historyHanding_recycler);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
       adapter=new HostoryHanding_adapter(weekly_sketles);
        recyclerView.setAdapter(adapter);
        relativeLayout=findViewById(R.id.internetwrong);
         adapter.setOnItemClickListener(new HostoryHanding_adapter.OnItemClickListener() {
             @Override
             public void onItemClick(View v, int position) {
                 Boolean nengkan=false;
                 Weekly_sketle sketle=weekly_sketles.get(position);
                 if ((mainView.preferences.getString("username","").equals("flyingdigital"))||(mainView.preferences.getString("username","").equals("zcjlovelzh")))
                     nengkan=true;
                 if (mainView.preferences.getString("usergroup","").equals("运营组"))
                     nengkan=true;
                 if (sketle.getIspublic())
                     nengkan=true;
                 if (nengkan)
                 docurl=downloadFile(sketle.getUrl(),sketle.getWeekly_name()+"."+sketle.getFile_type());
                // Toast.makeText(HistoryHandind.this, ""+nengkan, Toast.LENGTH_SHORT).show();
             }
         });


        Intent intent=getIntent();
        if (intent.getBooleanExtra("frommainview",false)) {
            frommainview = true;
            position=intent.getIntExtra("position",0);
        }
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                if (netChange.detectInternerState(HistoryHandind.this)){
//                    RelativeLayout relativeLayout=findViewById(R.id.internetwrong);
//                    relativeLayout.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.GONE);
//                }
//                else {
//                    RelativeLayout relativeLayout=findViewById(R.id.internetwrong);
//                    relativeLayout.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                }
//                if (refreshlayout.isRefreshing())
//                    refreshlayout.finishRefresh(200);
//            }
//        });
      if (!frommainview){
          new Thread(new Runnable() {
              @Override
              public void run() {
                  OkHttpClient client = new OkHttpClient();
                  Request request1 = new Request.Builder()
                          .url("http://47.103.205.169/api/summary/?token=" + MainActivity.token)
                          .build();

                  client.newCall(request1).enqueue(new Callback() {
                      @Override
                      public void onFailure(@NotNull Call call, @NotNull IOException e) {
                              runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      relativeLayout.setVisibility(View.VISIBLE);
                                      recyclerView.setVisibility(View.GONE);
                                  }
                              });

                      }

                      @Override
                      public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                          final String responsedata = response.body().string();
                          Log.d(TAG, ""+responsedata);
                          try {
                              final JSONArray array = new JSONArray(responsedata);
                              for (int i = 0; i < array.length(); i++) {
                                  JSONObject object = array.getJSONObject(i);
                                  String title = object.getString("title");
                                  file_type= object.getString("file_type");
                                  String url = "http://47.103.205.169" + object.getString("file");
                                  String week=object.getString("week");
                                  weekly_sketles.add(new Weekly_sketle("第"+week+"周",title,url,file_type,true));
                              }
                          } catch (JSONException e) {
                              e.printStackTrace();
                          }
                          runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  adapter.notifyDataSetChanged();
//                                  if (netChange.detectInternerState(HistoryHandind.this)){
//                                      // T//oast.makeText(this, "true", Toast.LENGTH_SHORT).show();
//                                  }
//                                  else {
//
//                                      relativeLayout.setVisibility(View.VISIBLE);
//                                      recyclerView.setVisibility(View.GONE);
//                                  }
//
                                  if (relativeLayout.getVisibility()==View.VISIBLE&&weekly_sketles.size()>0){
                                      relativeLayout.setVisibility(View.GONE);
                                      recyclerView.setVisibility(View.VISIBLE);
                                  }
                              }
                          });
                      }
                  });

              }
          }).start();
      }else {
          for (int i=0;i<mainView.weekLyList.get(position).size();i++){
              Weekly_sketle sketle=mainView.weekLyList.get(position).get(i);
              weekly_sketles.add(new Weekly_sketle(sketle.getWeek(),sketle.getWeekly_name(),sketle.getUrl(),sketle.getFile_type(),sketle.getIspublic()));
          }
      }
//        if (weekly_sketles.size()<=0){
//            RelativeLayout relativeLayout=findViewById(R.id.internetwrong);
//            relativeLayout.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//        }

    }
    private String  downloadFile(final String address, final String filename){
        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/tencent/QQfile_recv", filename);
        if (file.exists())
            file.delete();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!HistoryHandind.this.isFinishing()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (progressDialog==null)
                                progressDialog=new ProgressDialog(HistoryHandind.this);
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
                                    Intent intent = new Intent(HistoryHandind.this, WendangJiazaiyemian.class);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
