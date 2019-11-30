package com.example.zhangdabiji;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class ChangePassword extends AppCompatActivity {
  String vertification_code;
    EditText newPassword;
   EditText findpassword_email;
    private static final String TAG = "ChangePassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Button ok=findViewById(R.id.verification_code_findpassword_ok_button);
        final Button findpassword = findViewById(R.id.verification_code_findpassword_button);
        final EditText findpassword_edit =  findViewById(R.id.verification_code_findpassword_edit);
       findpassword_email=findViewById(R.id.yonghunyouxiang_findpassword_edit);
       newPassword=findViewById(R.id.verification_code_findpassword_mima_edit);
        findpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!findpassword_email.getText().toString().contains("@")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangePassword.this, "邮箱错误", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                        } else {
                            RequestBody body1 = new FormBody.Builder()
                                    .add("email", findpassword_email.getText().toString())
                                    .build();
                            final Request request1 = new Request.Builder()
                                    .post(body1)
                                    .url("http://47.103.205.169/api/recover_password_email/")
                                    .build();
//                        Log.d(TAG, "run: "+youxiang.getText().toString());
                            OkHttpClient client = new OkHttpClient();
                            client.newCall(request1).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ChangePassword.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                   String x=response.body().string();
                                    try {
                                        JSONObject object=new JSONObject(x);
                                        vertification_code=object.getString("verification_code");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ChangePassword.this, "获取验证码成功" , Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    }
                }).start();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findpassword_edit.getText().toString().equals(vertification_code))
                sendFindpasswordRequest(findpassword_email.getText().toString());
                else Toast.makeText(ChangePassword.this, "证码错误", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: "+findpassword_edit.getText().toString()+""+vertification_code);
            }
        });
    }
    void sendFindpasswordRequest(String email){
        OkHttpClient client=new OkHttpClient();
        RequestBody body=new FormBody.Builder()
                .add("email",email)
                .add("password",newPassword.getText().toString())
                .build();

        Request request=new Request.Builder()
                .post(body)
                .url("http://47.103.205.169/api/recover_password/")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChangePassword.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                }
                });
            }
        });
    }
}
//    <?xml version="1.0" encoding="utf-8"?>
//<LinearLayout
//    xmlns:android="http://schemas.android.com/apk/res/android"
//            android:orientation="vertical" android:layout_width="match_parent"
//            android:layout_height="match_parent">
//<TableLayout
//        android:layout_width="match_parent"
//                android:layout_height="wrap_content">
//<TableRow
//            android:layout_width="match_parent"
//                    android:layout_height="wrap_content"
//                    android:id="@+id/yonghu_yonghunianji">
//<TextView
//                android:layout_weight="3"
//                        android:text="邮箱"
//                        android:textSize="20sp"
//                        android:gravity="center"
//                        android:textColor="#000000"
//                        android:layout_width="0dp"
//                        android:layout_height="wrap_content"
//                        android:id="@+id/yonghunianji_txt"/>
//
//<EditText
//                android:hint="xxx@xx.com"
//                        android:id="@+id/yonghunyouxiang_findpassword_edit"
//                        android:layout_width="0dp"
//                        android:layout_height="wrap_content"
//                        android:layout_marginEnd="36dp"
//                        android:layout_weight="10"
//                        android:inputType="textEmailAddress" />
//</TableRow>
//<TableRow
//            android:layout_marginTop="10dp"
//                    android:layout_width="match_parent"
//                    android:layout_height="wrap_content"
//                    >
//<TextView
//                android:layout_weight="3"
//                        android:text="验证码"
//                        android:textSize="20sp"
//                        android:gravity="center"
//                        android:textColor="#000000"
//                        android:layout_width="0dp"
//                        android:layout_height="wrap_content"
//                        android:id="@+id/verification_code_txt"/>
//<EditText
//                android:layout_marginRight="40dp"
//                        android:layout_weight="4"
//                        android:layout_width="0dp"
//                        android:layout_height="wrap_content"
//                        android:id="@+id/verification_code_findpassword_edit" />
//<LinearLayout
//                android:layout_weight="5"
//                        android:layout_width="0dp"
//                        android:layout_height="match_parent"
//                        >
//<Button
//                    android:text="获取验证码"
//                            android:background="#199CE0"
//                            android:textColor="@color/white"
//                            android:layout_gravity="center_vertical"
//                            android:layout_width="wrap_content"
//                            android:id="@+id/verification_code_findpassword_button"
//                            android:layout_height="wrap_content">
//
//</Button>
//</LinearLayout>
//</TableRow>
//<TableRow
//            android:layout_marginTop="10dp"
//                    android:layout_width="match_parent"
//                    android:layout_height="wrap_content"
//                    >
//<TextView
//                android:layout_weight="3"
//                        android:text="密码"
//                        android:textSize="20sp"
//                        android:gravity="center"
//                        android:textColor="#000000"
//                        android:layout_width="0dp"
//                        android:layout_height="wrap_content"
//                        />
//<TextView
//                android:layout_marginEnd="40dp"
//                        android:layout_weight="4"
//                        android:layout_width="0dp"
//                        android:autoLink="none"
//                        android:layout_height="wrap_content"
//                        android:id="@+id/verification_code_findpassword_mima_edit" />
//<LinearLayout
//                android:layout_weight="5"
//                        android:layout_width="0dp"
//                        android:layout_height="match_parent"
//                        >
//<Button
//                    android:text="确定"
//                            android:background="#00ACC1"
//                            android:textColor="@color/white"
//                            android:layout_gravity="center_vertical"
//                            android:layout_width="wrap_content"
//                            android:id="@+id/verification_code_findpassword_ok_button"
//                            android:layout_height="wrap_content">
//
//</Button>
//</LinearLayout>
//</TableRow>
//</TableLayout>
//</LinearLayout>