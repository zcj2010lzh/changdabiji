package com.example.zhangdabiji;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static LinearLayout item;
    //UI Objects
    Button  wendang,wode,gaishu;
    private ViewPager vpager;
    Boolean isexit=false;
    private MyFragmentPagerAdapter mAdapter;
  public   static View view;
    //几个代表页面的常量
  public static   TextView gejiantextView,sanchu;
    public List<View> views;
    CheckBox quanxuan;
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE= 2;
    SharedPreferences preferences;
    static Boolean  danxuam=true;
  public static   LinearLayout l1,l2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       view= LayoutInflater.from(MainActivity.this).inflate(R.layout.item_popip, null, false);
       wode=findViewById(R.id.main_wode);
       gaishu=findViewById(R.id.main_paiming);
       gejiantextView=findViewById(R.id.kejian);
      wendang=findViewById(R.id.main_wendang);
      l1=findViewById(R.id.main_l1);
      l2=findViewById(R.id.main_l2);
     sanchu=findViewById(R.id.main_sanchu);
     quanxuan=findViewById(R.id.main_quanxuan);
     quanxuan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
             FileAdapter.positionSet.clear();
             Toast.makeText(MainActivity.this, ""+FileAdapter.mfileList.size(), Toast.LENGTH_SHORT).show();
           for(int i=0;i<FileAdapter.mfileList.size();i++){
                 FileAdapter.ViewHolder holder= (FileAdapter.ViewHolder) WendangYemian.recyclerView.findViewHolderForAdapterPosition(i);
           if (holder==null){
               Toast.makeText(MainActivity.this, ""+i, Toast.LENGTH_SHORT).show();
               continue;}
               FileAdapter.positionSet.add(i);
               holder.fileCheck.setChecked(true);
               holder.fileCheck.setVisibility(View.VISIBLE);
           }
         }
     });
        SharedPreferences.Editor  editor= PreferenceManager.getDefaultSharedPreferences( MainActivity.this).edit();
      preferences=PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if (ZhuCe.zuceActivity!=null)
       ZhuCe.zuceActivity.finish();

      wode.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              vpager.setCurrentItem(2);
          }
      });
        gaishu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpager.setCurrentItem(1);
            }
        });
      wendang.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              vpager.setCurrentItem(0);
          }
      });
      editor.putBoolean("yonghuiszhidongdenglu",true);
      editor.apply();
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

        sanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           FileAdapter.delete();
            FileAdapter.positionSet.clear();
            WendangYemian.adapter.notifyDataSetChanged();

            }
        });
           bindViews();
    }

    private void bindViews() {
        vpager = (ViewPager) findViewById(R.id.vpager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.setOffscreenPageLimit(2);
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
                    gaishu.setBackgroundColor(Color.parseColor("#00000000"));
                    wendang.setBackgroundColor(Color.parseColor("#23000000"));
                   if (WendangYemian.swipe){
                       if (MainActivity.gejiantextView.getVisibility()==View.INVISIBLE)
                           MainActivity.gejiantextView.setVisibility(View.VISIBLE);
                   }
                    break;
                case PAGE_TWO:
                    gaishu.setBackgroundColor(Color.parseColor("#23000000"));
                    wode.setBackgroundColor(Color.parseColor("#00000000"));
                    wendang.setBackgroundColor(Color.parseColor("#00000000"));
                    if (MainActivity.gejiantextView.getVisibility()==View.VISIBLE)
                        MainActivity.gejiantextView.setVisibility(View.INVISIBLE);
                    break;
                case PAGE_THREE:
                    wode.setBackgroundColor(Color.parseColor("#23000000"));
                    gaishu.setBackgroundColor(Color.parseColor("#00000000"));
                    wendang.setBackgroundColor(Color.parseColor("#00000000"));
                    if (MainActivity.gejiantextView.getVisibility()==View.VISIBLE)
                        MainActivity.gejiantextView.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            //调用exit()方法
            if (l1.getVisibility()==View.VISIBLE){
                danxuam=true;
                for (int i :FileAdapter.positionSet){
                    FileAdapter.ViewHolder  holder= (FileAdapter.ViewHolder) WendangYemian.recyclerView.findViewHolderForAdapterPosition(i);
                    if (holder==null)
                        continue;
                    holder.fileCheck.setVisibility(View.GONE);
                    holder.fileCheck.setChecked(false);
                }
                l1.setVisibility(View.INVISIBLE);
                l2.setVisibility(View.VISIBLE);
            }
            else
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



        public void onDestory()
        {
            super.onDestroy();
            this.finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    protected   static Boolean initPopWindow(View v) {
        Button btn_xixi = (Button) view.findViewById(R.id.btn_xixi);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效


        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(v, 50, 0);

        //设置popupWindow里的按钮的事件
        btn_xixi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        return  true;
    }
}


