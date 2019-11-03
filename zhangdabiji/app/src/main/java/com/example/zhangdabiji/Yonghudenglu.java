package com.example.zhangdabiji;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Yonghudenglu extends AppCompatActivity {
    AutoCompleteTextView zuming;
    EditText   xuehao,mima ,youxiang,nicheng;
    private   ProgressDialog progressDialog;
    Button zhuce;
    Boolean  isexit;
    private  static final String []  data1={"PHP组","UI组","运营组","前端组","运维组","安卓组"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yonghudenglu);
        zuming=findViewById(R.id.yonghudenglu_zuming);
        xuehao=findViewById(R.id.yonghudenglu_xuehao_edit);
       mima=findViewById(R.id.yonghudenglu_mima_edit);
       nicheng=findViewById(R.id.yonghunicheng_edit);
       youxiang=findViewById(R.id.yonghunyouxiang_edit);
       zhuce=findViewById(R.id.zuce);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Yonghudenglu.
                this, android.R.layout.simple_dropdown_item_1line, data1);
        zuming.setAdapter(adapter1);
       zhuce.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               new Thread(new Runnable() {
                   @Override
                   public void run() {


                       for(int i=0;i<data1.length;i++){
                           if (zuming.getText().toString().equals(data1[i])){
                               break;
                           }
                           if(i==data1.length-1){
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   zuming.setText("");
                                   Toast.makeText(Yonghudenglu.this, "你不能自己定义组名哟", Toast.LENGTH_SHORT).show();
                               }
                           });
                           return;}
                       }
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               showProgressDialog();
                               if (!isFinishing())
                                   progressDialog.show();
                           }
                       });
                       OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                       RequestBody body=new FormBody.Builder()
                               .add("account",xuehao.getText().toString())
                               .add("password",mima.getText().toString())
                               .add("username",nicheng.getText().toString())
                               .add("email",youxiang.getText().toString())
                               .add("group",zuming.getText().toString())
                               .build();
                       Request request = new Request.Builder()
                               .post(body)
                               .url(" http://47.103.205.169/api/register/")//请求接口。如果需要传参拼接到接口后面。
                               .build();//创建Request 对象
                       client.newCall(request).enqueue(new Callback() {
                           @Override
                           public void onFailure(@NotNull Call call, @NotNull IOException e) {
                           }

                           @Override
                           public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                               final     String  x;
                               x = response.body().string();
                               try {
                                   JSONObject object=new JSONObject(x);
                             String      response1=object.getString("code");
                                   if (response1.equals("1"))
                                     isexit=true;


                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       if (isexit) {
                                           runOnUiThread(new Runnable() {
                                               @Override
                                               public void run() {
                                                   try {
                                                       Toast.makeText(Yonghudenglu.this, "创建成功", Toast.LENGTH_SHORT).show();
                                                       Thread.sleep(1000);
                                                   } catch (InterruptedException e) {
                                                       e.printStackTrace();
                                                   }
                                               }
                                           });
                                           Intent intent = new Intent(Yonghudenglu.this, MainActivity.class);
                                           startActivity(intent);
                                           closeProgressDialog();
                                           finish();
                                       } else
                                       {runOnUiThread(new Runnable() {
                                           @Override
                                           public void run() {
                                               Toast.makeText(Yonghudenglu.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                                           }
                                       });}}

                               });
                           }
                       });//得到Response 对象
                   }
               }).start();
           }
       });
    }
    private  void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(Yonghudenglu.this);
            progressDialog.setMessage("load....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            progressDialog.setCancelable(true);// 设置是否可以通过点击Back键取消
            progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            progressDialog.setIcon(R.drawable.jiazai);//
            progressDialog.setMax(18);

        }
    }

    private void closeProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

}
