package com.example.zhangdabiji;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ZhuCe extends AppCompatActivity {

    TextView   zuce;
 EditText xuehaoedit,mimaedit;
    CheckBox zhidongdenglu;
    Button denlu;
    String xuehaozhi;
    String mimazhi;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
        zuce=findViewById(R.id.yonghuzhuce_zhuce);
         denlu=findViewById(R.id.denglu);
         mimaedit=findViewById(R.id.yonghu_mima_edit);
         xuehaoedit=findViewById(R.id.yonghuxuehao_edit);
        zhidongdenglu=findViewById(R.id.yonghudenglu_zhidong);
      //  xuehaozhi=xuehaoedit.getText().toString();
     //mimazhi=mimaedit.getText().toString();
     final    SharedPreferences.Editor  editor= PreferenceManager.getDefaultSharedPreferences( ZhuCe.this).edit();
        denlu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ZhuCe.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        zuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<JsonData> xuehaomimaarray=new ArrayList<>();
                JsonData xuehaodata=new JsonData("xuehao",xuehaoedit.getText().toString());
                JsonData mimadata=new JsonData("mima",mimaedit.getText().toString());
                xuehaomimaarray.add(xuehaodata);
                xuehaomimaarray.add(mimadata);
                Boolean isexit=false;
                isexit=HttpUtil.generateJsondata(xuehaomimaarray,"");
                if (isexit){
                Intent intent=new Intent(ZhuCe.this,Yonghudenglu.class);
                zuce.setBackgroundColor(Color.parseColor("#00000000"));
                startActivity(intent);}
                else
                    Toast.makeText(ZhuCe.this, "用户名错或密码错误", Toast.LENGTH_SHORT).show();
            }
        });
      zhidongdenglu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              if (zhidongdenglu.isChecked()) {
                  editor.putBoolean("yonghuiszhidongdenglu",true);
                  //Toast.makeText(ZhuCe. this, "iiii", Toast.LENGTH_SHORT).show();
                  editor.apply();
              } else {
                  editor.putBoolean("yonghuiszhidongdenglu",false);
                  //Toast.makeText(Yonghudenglu. this, "iiii", Toast.LENGTH_SHORT).show();
                  editor.apply();
              }
          }
      });
    }
}
