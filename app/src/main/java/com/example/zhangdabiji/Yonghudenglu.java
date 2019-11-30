package com.example.zhangdabiji;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Yonghudenglu extends AppCompatActivity {
    AutoCompleteTextView zuming;
    EditText   xuehao,mima ,youxiang,nicheng,vertification_edit;
    private   ProgressDialog progressDialog;
    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
    Button zhuce,vertification_button;
    private static final String TAG = "Yonghudenglu";
    Boolean  isexit;
    String      response1;
    private  String vertification_code;
    SharedPreferences.Editor editor;
    Boolean isfa=true;
    private  static final String []  data1={"PHP组","UI组","运营组","前端组","运维组","安卓组"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yonghudenglu);
        zuming=findViewById(R.id.yonghudenglu_zuming);
        xuehao=findViewById(R.id.yonghudenglu_xuehao_edit);
       mima=findViewById(R.id.yonghudenglu_mima_edit);
       nicheng=findViewById(R.id.yonghunicheng_edit);
       youxiang=findViewById(R.id.yonghunyouxiang_edit);
       vertification_button=findViewById(R.id.verification_code_button);
       zhuce=findViewById(R.id.zuce);
       vertification_edit=findViewById(R.id.verification_code_edit);
       editor= PreferenceManager.getDefaultSharedPreferences(Yonghudenglu.this).edit();
        ArrayList<String> list=new ArrayList<>();
        Collections.addAll(list, data1);
        //        Collections.addAll(list, data1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Yonghudenglu.this, android.R.layout.simple_dropdown_item_1line, data1);
//     AutoCompleteAdapter adapter=new AutoCompleteAdapter(list,Yonghudenglu.this);
        zuming.setAdapter(adapter);
        vertification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!youxiang.getText().toString().contains("@"))
                    Toast.makeText(Yonghudenglu.this, "请重新输入邮箱", Toast.LENGTH_SHORT).show();
                else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            RequestBody body1=new FormBody.Builder()
                                    .add("email",youxiang.getText().toString())
                                    .build();
                            final Request request1=new Request.Builder()
                                    .post(body1)
                                    .url("http://47.103.205.169/api/verification_code/")
                                    .build();
                            Log.d(TAG, "run: "+youxiang.getText().toString());
                            client.newCall(request1).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Yonghudenglu.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    String v=response.body().string();
                                    try {
                                        JSONObject object=new JSONObject(v);
                                        vertification_code=object.getString("verification_code");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Yonghudenglu.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    }).start();
                }
            }
        });
       zhuce.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       if (vertification_code != null) {
                           if (vertification_code.equals(vertification_edit.getText().toString())) {
                               for (int i = 0; i < data1.length; i++) {
                                   if (zuming.getText().toString().equals(data1[i])) {
                                       break;
                                   }
                                   if (i == data1.length - 1) {
                                       runOnUiThread(new Runnable() {
                                           @Override
                                           public void run() {
                                               zuming.setText("");
                                               Toast.makeText(Yonghudenglu.this, "你不能自己定义组名哟", Toast.LENGTH_SHORT).show();
                                           }
                                       });
                                       return;
                                   }
                               }
                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       if (progressDialog == null)
                                           progressDialog = new ProgressDialog(Yonghudenglu.this);
                                       ProgressShow.showProgressDialog(progressDialog);
                                       if (!isFinishing())
                                           progressDialog.show();
                                   }
                               });

                               if (xuehao.getText().toString().equals("") || mima.getText().toString().equals("") || nicheng.getText().toString().equals(""))
                                   isfa = false;
                               final RequestBody body = new FormBody.Builder()
                                       .add("account", xuehao.getText().toString())
                                       .add("password", mima.getText().toString())
                                       .add("username", nicheng.getText().toString())
                                       .add("email", youxiang.getText().toString())
                                       .add("group", zuming.getText().toString())
                                       .add("vertification_code", vertification_code)
                                       .build();
                               final Request request = new Request.Builder()
                                       .post(body)
                                       .url("http://47.103.205.169/api/register/")//请求接口。如果需要传参拼接到接口后面。
                                       .build();//创建Request 对象
                               if (isfa) {
                                   client.newCall(request).enqueue(new Callback() {
                                       @Override
                                       public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                       }

                                       @Override
                                       public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                                           final String x;

                                           Log.d(TAG, "onResponse: " + Objects.requireNonNull(response.body()).toString());
                                           x = Objects.requireNonNull(response.body()).string();
                                           Log.d(TAG, "onResponse: " + x);
                                           try {
                                               JSONObject object = new JSONObject(x);
                                               response1 = object.getString("code");
                                               if (response1.equals("1"))
                                                   isexit = true;


                                           } catch (JSONException e) {
                                               e.printStackTrace();
                                           }
                                           runOnUiThread(new Runnable() {
                                               @Override
                                               public void run() {
                                                   Log.d(TAG, "run: " + response1);
                                                   if (response1.equals("1")) {
                                                       runOnUiThread(new Runnable() {
                                                           @Override
                                                           public void run() {
                                                               try {
                                                                   editor.putString("usergroup", zuming.getText().toString());
                                                                   editor.putString("username", nicheng.getText().toString());
                                                                   editor.apply();
                                                                   Toast.makeText(Yonghudenglu.this, "创建成功,请登录", Toast.LENGTH_SHORT).show();
                                                                   Thread.sleep(1000);
                                                               } catch (InterruptedException e) {
                                                                   e.printStackTrace();
                                                               }
                                                           }
                                                       });
                                                       progressDialog.dismiss();
                                                       Intent intent = new Intent(Yonghudenglu.this, ZhuCe.class);
                                                       startActivity(intent);
                                                       ProgressShow.closeProgressDialog(progressDialog);
                                                       finish();
                                                   } else {
                                                       runOnUiThread(new Runnable() {
                                                           @Override
                                                           public void run() {
                                                               Toast.makeText(Yonghudenglu.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                                                           }
                                                       });
                                                   }
                                               }

                                           });
                                       }
                                   });//得到Response 对象
                               }else {
                                   runOnUiThread(new Runnable() {
                                       @Override
                                       public void run() {
                                           Toast.makeText(Yonghudenglu.this, "请输入完整的信息", Toast.LENGTH_SHORT).show();
                                           progressDialog.dismiss();
                                           isfa=true;
                                       }
                                   });
                               }
                               } else {
                                   runOnUiThread(new Runnable() {
                                       @Override
                                       public void run() {
                                           Toast.makeText(Yonghudenglu.this, "验证码错误", Toast.LENGTH_SHORT).show();
                                           Log.d(TAG, "run: " + vertification_code);
                                       }
                                   });
                               }
                           } else {
                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       Toast.makeText(Yonghudenglu.this, "验证码或密码错误错误", Toast.LENGTH_SHORT).show();
                                   }
                               });
                           }

                   }

               }).start();
           }

       });
    }
}
