package com.example.zhangdabiji;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lzy.okgo.OkGo;
import com.tencent.smtt.sdk.TbsReaderView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class  WendangJiazaiyemian  extends AppCompatActivity implements TbsReaderView.ReaderCallback, View.OnClickListener {
    Dialog dialog;
  String[] name;
    String filename ;
    RelativeLayout mRelativeLayout;
    private TbsReaderView mTbsReaderView;
    private String docUrl;
    Intent intent ;
    String x;
    SharedPreferences preferences;
    String token;
    TextView shangchuan, bianji;
    private static final String TAG = "WendangJiazaiyemian";
  CheckBox  gongkai;
    private static final String[][] MIME_MapTable = {{".doc", "application/msword"}};
    Context mcontext = WendangJiazaiyemian.this;
    ProgressDialog progressDialog;
    private String filetype;
    TextView  textView;
    private String download =Environment.getExternalStorageDirectory().getAbsolutePath() + "/tencent/QQfile_recv";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wendang_jiazaiyemian);
        Toolbar toolbar = findViewById(R.id.wendangjiazaiyemian_toolbar);
        setSupportActionBar(toolbar);
        OkGo.getInstance().init(getApplication());
        preferences = PreferenceManager.getDefaultSharedPreferences(WendangJiazaiyemian.this);
       token=preferences.getString("token","");
        mTbsReaderView = new TbsReaderView(this, this);
        mRelativeLayout = findViewById(R.id.tbsView);
        ImageView imageView=findViewById(R.id.sangedian);
        textView=findViewById(R.id.fileitle);
        intent= getIntent();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose();
            }
        });
        if(intent.getBooleanExtra("isDeleting",false))
            toolbar.setVisibility(View.GONE);
        mRelativeLayout.addView(mTbsReaderView, new RelativeLayout.LayoutParams(-1, -1));
        initDoc();
    }

    private void initDoc() {
       intent= getIntent();
        docUrl = intent.getStringExtra("fileurl");
        filetype=intent.getStringExtra("filetype");
         name= docUrl.split("/");
         filename= name[name.length - 1];
        for(int i=0;i<filename.length();i++){
            if (filename.toCharArray()[i]=='.'){
                textView.setText(filename.substring(0,i));
                break;
            }
        }
       // Toast.makeText(mcontext, docUrl, Toast.LENGTH_SHORT).show();
        //判断是否在本地/[下载/直接打开]
        File docFile = new File(docUrl);
        if (docFile.exists()){
            //存在本地;

            displayFile(docUrl, filename);

    } else {
          displayFile(docUrl,"test.doc");
        }
}


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wendanfjiazaiyemian,menu);
        return  true;
    }

    public void  choose() {
            dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
            //填充对话框的布局
            View inflate = LayoutInflater.from(this).inflate(R.layout.wendangjiazaiyemian_dialog, null);
            //初始化控件
            bianji = inflate.findViewById(R.id.wendangjiazaiyemian_bianji);
            shangchuan = (TextView) inflate.findViewById(R.id.wendangjiazaiyemian_shangchuan);
            bianji.setOnClickListener((View.OnClickListener) mcontext);
            gongkai = inflate.findViewById(R.id.wendangjiazaiyemian_gongkai);
            shangchuan.setOnClickListener((View.OnClickListener) mcontext);
            //将布局设置给Dialog
            dialog.setContentView(inflate);
            //获取当前Activity所在的窗体
            Window dialogWindow = dialog.getWindow();
            //设置Dialog从窗体底部弹出
            assert dialogWindow != null;
            dialogWindow.setGravity(Gravity.BOTTOM);
            //获得窗体的属性
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width=getResources().getDisplayMetrics().widthPixels;
            lp.y = 20;//设置Dialog距离底部的距离
//    将属性设置给窗体
            dialogWindow.setAttributes(lp);
            dialog.show();//显示对话框

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
        Boolean delete=intent.getBooleanExtra("isDeleting",false);
        if (delete){
            File file=new File(docUrl);
            if (file.exists())
                file.delete();
        }
        mTbsReaderView.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wendangjiazaiyemian_bianji:
                dialog.dismiss();
                Intent intent =  OpenFiles.getWordFileIntent(docUrl,mcontext);
                startActivity(intent);
                break;
            case R.id.wendangjiazaiyemian_shangchuan:
              //   HttpUtil.upLoadeFile(filename,docUrl,""+gongkai.isChecked(),WendangJiazaiyemian.this,token);
            if (!mainView.preferences.getBoolean("thisweekisload",false)){
                Log.d(TAG, "onClick: "+MainActivity.preferences.getBoolean("thisweekisload",false));
                for(int i=0;i<filename.length();i++){
                    if (filename.toCharArray()[i]=='.'){
                        x=filename.substring(0,i);
                        break;
                    }
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean ispublic;
                        ispublic=gongkai.isChecked();
                        if (!WendangJiazaiyemian. this.isFinishing()){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (progressDialog==null)
                                        progressDialog=new ProgressDialog(WendangJiazaiyemian.this);
                                    ProgressShow.showProgressDialog(progressDialog);
                                    progressDialog.show();
                                }
                            });
                        }
                        if (mainView.preferences.getBoolean("openPrivacy",false)){
                           ispublic=false;
                        }

                        String ispub=String.valueOf(ispublic);
                        char a[]=ispub.toCharArray();
                        a[0]-=32;
                        ispub=String.copyValueOf(a);
                        OkHttpClient client=new OkHttpClient();
                        dialog.dismiss();
                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)

                                .addFormDataPart("file", filename,
                                        RequestBody.create(MediaType.parse("multipart/form-data"), new File(docUrl)))
                                .addFormDataPart("is_public", String.valueOf(ispub))
                                .addFormDataPart("file_type",filetype)
                                .addFormDataPart("title",x)
                                .build();//传文件

                        Request request = new Request.Builder()
                                .header("Authorization", "Client-ID " + UUID.randomUUID())
                                .url("http://47.103.205.169/api/summary/?token="+token)
                                .post(requestBody)
                                .build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       Toast.makeText(WendangJiazaiyemian.this, "上传失败", Toast.LENGTH_SHORT).show();
                                   }
                               });
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        Toast.makeText(WendangJiazaiyemian.this, "上传成功", Toast.LENGTH_SHORT).show();
                                        mainView.editor.putBoolean("thisweekisload",true);
                                        int count=Integer.parseInt(mainView.preferences.getString("userloadcount","0"));
                                        count++;
                                        mainView.editor.putString("userloadcount",String.valueOf(count));
                                        mainView.editor.apply();

                                    }
                                });
                            }
                        });
                    }
                }).start();
            }
            else Toast.makeText(this, "本周已上传", Toast.LENGTH_SHORT).show();

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