package com.example.zhangdabiji;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class Yonghudenglu extends AppCompatActivity {
    AutoCompleteTextView zuming,zhiwu;
    RadioButton  nan,nu,zhidongdenglu;
    EditText   xuehao,mima;
    private   ProgressDialog progressDialog;
    Button zhuce;
    private  static final String []  data1={"PHP组","UI组","运营组","前端组","运维组","安卓组"};
    private  static final String []  data2={"站长","副站长","办公室主任","技术站长","组长","组员"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yonghudenglu);
        zhiwu=findViewById(R.id.yonghudenglu_zhiwu);
        zuming=findViewById(R.id.yonghudenglu_zuming);
        nan=findViewById(R.id.yonghudenglu_nan);
        nu=findViewById(R.id.yonghudenglu_nu);
        xuehao=findViewById(R.id.yonghuxuehao_edit);
       mima=findViewById(R.id.yonghu_mima_edit);
       zhuce=findViewById(R.id.zuce);
        zhidongdenglu=findViewById(R.id.yonghudenglu_zhidong);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Yonghudenglu.
                this, android.R.layout.simple_dropdown_item_1line, data1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Yonghudenglu.
                this, android.R.layout.simple_dropdown_item_1line, data2);
        zhiwu.setAdapter(adapter2);
        zuming.setAdapter(adapter1);
       zhuce.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                   showProgressDialog();
                  new  Thread(new Runnable() {
                      @Override
                      public void run() {
                         try {
                             Thread.sleep(3500);
                         }catch (InterruptedException e){
                             e.printStackTrace();
                         }
                      }
                  }).start();
               closeProgressDialog();
           }
       });
    }
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getApplication());
            progressDialog.setMessage("load....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            progressDialog.setCancelable(true);// 设置是否可以通过点击Back键取消
            progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            progressDialog.setIcon(R.drawable.jiazai);//
            progressDialog.setMax(18);

        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

}
