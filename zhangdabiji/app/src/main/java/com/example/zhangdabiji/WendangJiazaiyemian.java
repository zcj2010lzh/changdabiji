package com.example.zhangdabiji;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsReaderView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import com.lzy.okgo.OkGo;
//import com.lzy.okgo.callback.FileCallback;

public class  WendangJiazaiyemian  extends AppCompatActivity implements TbsReaderView.ReaderCallback, View.OnClickListener {
        Dialog dialog;
    RelativeLayout mRelativeLayout;
  private   View inflate;
    private TbsReaderView mTbsReaderView;
    private String docUrl;
    SharedPreferences preferences;
  private   String filename;
    TextView shangchuan,bianji;
    RadioButton gongkai;
    private static final String[][] MIME_MapTable={{".doc",    "application/msword"}};
    Context mcontext=WendangJiazaiyemian.this;
    String text;
    private String download = Environment.getExternalStorageDirectory() + "/download/test/document/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wendang_jiazaiyemian);
        Toolbar toolbar=findViewById(R.id.wendangjiazaiyemian_toolbar);
        setSupportActionBar(toolbar);
        preferences= PreferenceManager.getDefaultSharedPreferences(WendangJiazaiyemian.this);
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。

            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                initDoc();
            }
        });
        mTbsReaderView = new TbsReaderView(this, this);
        mRelativeLayout = findViewById(R.id.tbsView);
        mRelativeLayout.addView(mTbsReaderView,new RelativeLayout.LayoutParams(-1,-1));
        initDoc();
    }

    private void initDoc() {
        Intent intent = getIntent();
        docUrl = intent.getStringExtra("fileurl");

        final String[] name = docUrl.split("/");
        filename = name[name.length - 1];
        //判断是否在本地/[下载/直接打开]
        File docFile = new File(docUrl);
        if (docFile.exists()) {
            //存在本地;
            displayFile(docUrl, filename);


        } else {
       new Thread(new Runnable() {
           @Override
           public void run() {
            final String token=   preferences.getString("token","");
               OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
               Request request = new Request.Builder()
                       .url("http://47.103.205.169/api/summary/?token="+token)//请求接口。如果需要传参拼接到接口后面。
                       .build();//创建Request 对象
              client.newCall(request).enqueue(new Callback() {
                  @Override
                  public void onFailure(@NotNull Call call, @NotNull IOException e) {

                  }

                  @Override
                  public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                      final String responsedata=response.body().string();
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                           //Toast.makeText(mcontext, responsedata+"", Toast.LENGTH_SHORT).show();
                         }
                     });
                 try {
                          JSONArray array=new JSONArray(responsedata);
                          for(int i=0;i<array.length()-1;i++) {
                              JSONObject object = array.getJSONObject(i);
                              text = object.getString("text");
                                  ss(text);

                          }
                          //displayFile(file.getAbsolutePath(),file.getName());
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
              });
           }
       }).start();
        }
    }
    public void ss(final String x)
    {
       // int myProcessID = Process.myPid();
        final File yygypath = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/tencent/QQfile_recv");//this.getCacheDir();
    final File    file = new File(yygypath,"test.txt");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("hh",yygypath.toString());
                Log.d("hh",file.getAbsolutePath());
            }
        });
        try {
         final Boolean   x2=  file.createNewFile();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mcontext, ""+x2, Toast.LENGTH_SHORT).show();
                }
            });

        } catch (final IOException e) {
            e.printStackTrace();
        }

        try(FileWriter writer=new FileWriter(file)) {
         writer.write(x);

    } catch (IOException e) {
        e.printStackTrace();
    }

                }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wendanfjiazaiyemian,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sangedian:
                dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
                //填充对话框的布局
                inflate = LayoutInflater.from(this).inflate(R.layout.wendangjiazaiyemian_dialog, null);
                //初始化控件
              bianji =  inflate.findViewById(R.id.wendangjiazaiyemian_bianji);
               shangchuan = (TextView) inflate.findViewById(R.id.wendangjiazaiyemian_shangchuan);
               bianji.setOnClickListener((View.OnClickListener) mcontext);
           shangchuan.setOnClickListener((View.OnClickListener) mcontext);
                //将布局设置给Dialog
                dialog.setContentView(inflate);
                //获取当前Activity所在的窗体
                Window dialogWindow = dialog.getWindow();
                //设置Dialog从窗体底部弹出
                dialogWindow.setGravity( Gravity.BOTTOM);
                //获得窗体的属性
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.y = 20;//设置Dialog距离底部的距离
//    将属性设置给窗体
                dialogWindow.setAttributes(lp);
                dialog.show();//显示对话框
                break;
        }
        return  true;
    }

    private String tbsReaderTemp = Environment.getExternalStorageDirectory() + "/TbsReaderTemp";
    private void displayFile(String filePath, String fileName) {

        //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
        String bsReaderTemp = tbsReaderTemp;
        File bsReaderTempFile =new File(bsReaderTemp);
        if (!bsReaderTempFile.exists()) {
            Log.d("print","准备创建/TbsReaderTemp！！");
            boolean mkdir = bsReaderTempFile.mkdir();
            if(!mkdir){
                Log.d("print","创建/TbsReaderTemp失败！！！！！");
            }
        }
        Bundle bundle = new Bundle();
   /*   1.TbsReader: Set reader view exception:Cannot add a null child view to a ViewGroup
        TbsReaderView: OpenFile failed! [可能是文件的路径错误]*/
   /*   2.插件加载失败
        so文件不支持;*/
   /*
   ndk {
            //设置支持的SO库架构  'arm64-v8a',
            abiFilters 'armeabi', "armeabi-v7a",  'x86'
        } */
   /*
        3.自适应大小
    */
        bundle.putString("filePath", filePath);
        bundle.putString("tempPath", tbsReaderTemp);
        boolean result = mTbsReaderView.preOpen(getFileType(fileName), false);
        Log.d("print","查看文档---"+result);
        if (result) {
            mTbsReaderView.openFile(bundle);
        }else{

        }
    }

    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            Log.d("print", "paramString---->null");
            return str;
        }
        Log.d("print", "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.d("print", "i <= -1");
            return str;
        }

        str = paramString.substring(i + 1);
        Log.d("print", "paramString.substring(i + 1)------>" + str);
        return str;
    }
    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTbsReaderView.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wendangjiazaiyemian_bianji:
                Intent intent =  OpenFiles.getWordFileIntent(docUrl,mcontext);
                startActivity(intent);
                break;
        }
    }
    public  void openFile(Context context,File file) {
        //Uri uri = Uri.parse("file://"+file.getAbsolutePath());
      /*  Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        String type="application/msword";
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        if (docUrl.endsWith(".doc")||docUrl.endsWith(".docx"))
        type ="application/msword";
        //设置intent的data和Type属性。
        intent.setDataAndType(/*uri*///Uri.fromFile(file), type);
        //跳转
        //context.startActivity(intent);*/
       // if (docUrl.endsWith(".doc")||docUrl.endsWith(".docx"))
            //type ="application/msword";

    }


}