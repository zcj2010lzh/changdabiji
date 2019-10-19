package com.example.zhangdabiji;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    //UI Objects
    Button  wendang;
    Button  wode;
    private ViewPager vpager;
    Boolean isexit=false;
    private MyFragmentPagerAdapter mAdapter;

    //几个代表页面的常量
    public List<View> views;
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       wode=findViewById(R.id.main_wode);
      wendang=findViewById(R.id.main_wendang);
      wode.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              vpager.setCurrentItem(PAGE_TWO);
          }
      });
      wendang.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              vpager.setCurrentItem(0);
          }
      });
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        bindViews();
    }

    private void bindViews() {
        vpager = (ViewPager) findViewById(R.id.vpager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
    }
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }


    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                wode.setBackgroundColor(Color.parseColor("#00000000"));
                    wendang.setBackgroundColor(Color.parseColor("#23000000"));
                    break;
                case PAGE_TWO:
                   wode.setBackgroundColor(Color.parseColor("#23000000"));
                    wendang.setBackgroundColor(Color.parseColor("#00000000"));
                    break;

            }
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            //调用exit()方法
            exit();
        }
        return false;
    }

    //被调用的exit()方法
    private void exit() {
        Timer timer;//声明一个定时器
        if (!isexit) {  //如果isExit为false,执行下面代码
            isexit = true;  //改变值为true
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();  //弹出提示
            timer = new Timer();  //得到定时器对象
            //执行定时任务,两秒内如果没有再次按下,把isExit值恢复为false,再次按下返回键时依然会进入if这段代码
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isexit = false;
                }
            }, 2000);
        } else {//如果两秒内再次按下了返回键,这时isExit的值已经在第一次按下时赋值为true了,因此不会进入if后的代码,直接执行下面的代码
            finish();
        }
    }
}