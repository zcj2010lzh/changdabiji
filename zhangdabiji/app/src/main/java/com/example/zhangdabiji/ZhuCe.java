package com.example.zhangdabiji;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ZhuCe extends AppCompatActivity {

    TextView   zuce;
 EditText xuehaoedit,mimaedit;
    Button denlu;
    String xuehaozhi;
    String mimazhi;
    protected  static Activity  zuceActivity;
    Boolean isexit;
    String response1;
    SharedPreferences.Editor editor;
    List<JsonData> xuehaomimaarray;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
        zuce = findViewById(R.id.yonghuzhuce_zhuce);
        denlu = findViewById(R.id.denglu);
        mimaedit = findViewById(R.id.yonghu_mima_edit);
        xuehaoedit = findViewById(R.id.yonghuxuehao_edit);
        zuceActivity=this;
       editor=  PreferenceManager.getDefaultSharedPreferences(ZhuCe.this).edit();
        xuehaomimaarray = new ArrayList<>();
        JsonData xuehaodata = new JsonData("xuehao", xuehaoedit.getText().toString());
        JsonData mimadata = new JsonData("mima", mimaedit.getText().toString());
        xuehaomimaarray.add(xuehaodata);
        xuehaomimaarray.add(mimadata);
        zuce.setBackgroundColor(Color.parseColor("#00000000"));
        isexit= false;
        //  xuehaozhi=xuehaoedit.getText().toString();
        //mimazhi=mimaedit.getText().toString();

        denlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                            OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                            RequestBody body=new FormBody.Builder()
                                    .add("account",xuehaoedit.getText().toString())
                                    .add("password",mimaedit.getText().toString())
                                    .build();
                            Request request = new Request.Builder()
                                    .post(body)
                                    .url(" http://47.103.205.169/api/login/")//请求接口。如果需要传参拼接到接口后面。
                                    .build();//创建Request 对象
                        client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                                final     String  x;
                                String token;
                                    x = response.body().string();
                                    try {
                                                JSONObject object=new JSONObject(x);
                                           response1=object.getString("code");
                                           token=object.getString("token");
                                             editor.putString("token",token);
                                             editor.apply();
                                        if (response1.equals("1"))
                                            isexit=true;


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                           if (isexit) {
                                                Intent intent = new Intent(ZhuCe.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else
                                                Toast.makeText(ZhuCe.this, "用户名不存在密码错误", Toast.LENGTH_SHORT).show();}

                                    });
                                }
                            });//得到Response 对象
                        }
                    }).start();

            }

        });
        zuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ZhuCe.this, Yonghudenglu.class);
                startActivity(intent);
            }
        });
    }
}
