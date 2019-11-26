package com.example.zhangdabiji;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Welcome extends AppCompatActivity {
    public static List<FileBean> fileList = new ArrayList<>();
    SharedPreferences preferences;
    VideoView videoView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final Boolean iszhidong;
         preferences=PreferenceManager.getDefaultSharedPreferences(this);
        iszhidong=preferences.getBoolean("yonghuiszhidongdenglu",false);
        videoView=findViewById(R.id.welcome_video);
        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.welcome);
        videoView.setVideoURI(uri);
        videoView.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (iszhidong){
                    Intent intent=new Intent(Welcome.this,MainActivity.class);
                    startActivity(intent);
                    videoView.pause();
                    finish();}
                else {
                    Intent intent1=new Intent(Welcome.this,ZhuCe.class);
                    startActivity(intent1);
                    finish();
                }
            }
        },3000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK)
            return  true;
        return super.onKeyDown(keyCode, event);
    }
}
