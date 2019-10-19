package com.example.zhangdabiji;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Welcome extends AppCompatActivity {
    public static List<FileBean> fileList = new ArrayList<>();
    SharedPreferences preferences;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final Boolean iszhidong;
         preferences=PreferenceManager.getDefaultSharedPreferences(this);
        iszhidong=preferences.getBoolean("yonghuiszhidongdenglu",false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (iszhidong){
                    Intent intent=new Intent(Welcome.this,MainActivity.class);
                    startActivity(intent);
                    finish();}
                else {
                    Intent intent1=new Intent(Welcome.this,ZhuCe.class);
                    startActivity(intent1);
                    finish();
                }
            }
        },3000);
    }



    }
