package com.example.zhangdabiji;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
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
public class ZhuCe extends AppCompatActivity {

    TextView   zuce,findpasssword;
 EditText xuehaoedit,mimaedit;
    Button denlu;
    protected  static Activity  zuceActivity;
    Boolean isexit=false;
    String response1;
    private boolean flag = true;
    private static final String TAG = "ZhuCe";
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    ImageView passwordisvisable;
    Dialog dialog;
    ProgressDialog progressDialog;
    String token,vertification_code;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
        zuce = findViewById(R.id.yonghuzhuce_zhuce);
        denlu = findViewById(R.id.denglu);
       mimaedit = findViewById(R.id.yonghu_mima_edit);
       xuehaoedit = findViewById(R.id.yonghuxuehao_edit);
//        findpasssword=findViewById(R.id.yonghuzhuce_wangjimima);
        zuceActivity=this;
        FrameLayout layout=findViewById(R.id.zuce_back);
        Drawable drawable=layout.getBackground();
        drawable.setAlpha(100);
        layout.setBackground(drawable);//alpha透明度
//        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),R.drawable.board);
//        int[] argb = new int[bitmap.getWidth() * bitmap.getHeight()];
//
//       bitmap.getPixels(argb, 0, bitmap.getWidth(), 0, 0, bitmap
//
//                .getWidth(), bitmap.getHeight());// 获得图片的ARGB值
//        int number=5;
//        number = number * 255 / 100;
//
//        for (int i = 0; i < argb.length; i++) {
//
//            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);
//
//        }
//
//        bitmap = Bitmap.createBitmap(argb, bitmap.getWidth(), bitmap
//
//                .getHeight(), Bitmap.Config.ARGB_8888);
//        Drawable drawable = new BitmapDrawable(bitmap);
//        layout.setBackground(drawable);
        passwordisvisable=findViewById(R.id.mimaisvisable);

        passwordisvisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    mimaedit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordisvisable.setImageResource(R.drawable.visable);
                    flag = false;
                }else {
                    mimaedit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flag=true;
                    passwordisvisable.setImageResource(R.drawable.invisable);
                }
            }
        });
       editor=  PreferenceManager.getDefaultSharedPreferences(ZhuCe.this).edit();

        zuce.setBackgroundColor(Color.parseColor("#00000000"));
        preferences=PreferenceManager.getDefaultSharedPreferences(this);
        mimaedit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        denlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (xuehaoedit.getText().toString().equals(preferences.getString("yonghuxuehao", "-v2utb2nytvn82yvt82y"))
                               && mimaedit.getText().toString().equals(preferences.getString("yonghumima", "jwrqcbrbqhbcrhnq9c"))){
                            Intent intent=new Intent(ZhuCe.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                       }
                     else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (progressDialog == null)
                                        progressDialog=new ProgressDialog(ZhuCe.this);
                                    ProgressShow.showProgressDialog(progressDialog);
                                    if (!isFinishing())
                                        progressDialog.show();
                                }
                            });
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
                                    x = response.body().string();
                                    Log.d(TAG, ""+x);
                                    try {
                                        JSONObject object=new JSONObject(x);
                                        response1=object.getString("code");
                                        token=object.getString("token");

                                        if (response1.equals("1"))
                                            isexit=true;


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ProgressShow.closeProgressDialog(progressDialog);
                                            if (isexit) {
                                                editor.putString("yonghuxuehao",xuehaoedit.getText().toString());
                                                editor.putString("yonghumima",mimaedit.getText().toString());
                                                editor.putString("token",token);
                                                editor.apply();
                                                Intent intent = new Intent(ZhuCe.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else
                                                Toast.makeText(ZhuCe.this, "用户不存在密码错误", Toast.LENGTH_SHORT).show();
                                            ;}

                                    });
                                }
                            });//得到Response 对象
                        }
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
        Intent intent=getIntent();
     //
      //  Toast.makeText(zuceActivity, ""+preferences.getBoolean("is",false), Toast.LENGTH_SHORT).show();
        if (intent.getBooleanExtra("is",false)){
            xuehaoedit.setText(preferences.getString("yonghuxuehao",""));
            mimaedit.setText(preferences.getString("yonghumima",""));
        }
        mimaedit.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

}
