package com.example.zhangdabiji;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.zhangdabiji.Yonghuyemian.userloadcount;
import static com.example.zhangdabiji.Yonghuyemian.userunloadcount;
import static org.litepal.LitePalApplication.getContext;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
   String maxweek="0";
    LinearLayout  wendang,gaishu;
    private ViewPager vpager;
    public  static TextView  genduoshujutextView;
     public  static   ProgressDialog progressDialog;
    private  ImageView gailun,user;
    public static Boolean viewpagerisskip=true;
    Boolean isexit=false;
    public static final int takePhone = 1;
    public CircleImageView yonghuyemian_circle,usericon_draw;
    String xx;
    public  static    String imagePath=null;
    protected static String token;
    public Bitmap bitmap;
    Dialog dialog;
    private  SharedPreferences.Editor editor;;
    private MyFragmentPagerAdapter mAdapter;
    private  LinearLayout historyholding,userinfoemation,userquitloading;
    public static   TextView kejiantextView,sanchu;
  //static   CheckBox quanxuan;
    int windowheight;
    private  TextView usergroup_draw,username_draw,userloadinfmode_draw;
    public static final int PAGE_ONE = 0;
    public static SharedPreferences preferences;
    public static final int PAGE_TWO = 1;
//    public static final int PAGE_THREE= 2;
    public  static  View view;
    FileAdapter.ViewHolder holder;
    public  static   Toolbar toolbar ;
    View layout;
 private    TextView textView;
     DrawerLayout  drawerLayout;
    private static final String TAG = "MainActivity";
    //DrawerLayout drawerLayou;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_popip, null, false);
       gaishu=findViewById(R.id.main_paiming);
       kejiantextView=findViewById(R.id.kejian);
       toolbar = findViewById(R.id.main_toobar);

        setSupportActionBar(toolbar);
        genduoshujutextView=findViewById(R.id.kejianshuju);
        yonghuyemian_circle=toolbar.findViewById(R.id.main_toobar_touxiang);
        preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
      wendang=findViewById(R.id.main_wendang);
      gailun=findViewById(R.id.gailan);
      usergroup_draw=findViewById(R.id.user_group_draw);
      username_draw=findViewById(R.id.user_name_draw);
    drawerLayout=findViewById(R.id.main_drawlayout_new);
      usericon_draw=findViewById(R.id.user_icon_draw);
        username_draw.setText(preferences.getString("username",""));
        usergroup_draw.setText(preferences.getString("usergroup",""));
        int checkColor =MainActivity.this.getResources().getColor(R.color.blue);
      user=findViewById(R.id.user);
        userloadinfmode_draw=findViewById(R.id.userloadmode_draw);

        historyholding=findViewById(R.id.historyHanding_draw);

        historyholding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyholding.setBackgroundColor(getResources().getColor(R.color.gray));
                Intent intent=new Intent(MainActivity.this,HistoryHandind.class);
                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                startActivity(intent);
            }
        });

        userquitloading=findViewById(R.id.main_drawlayout_quit);
        userquitloading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(MainActivity.this,ZhuCe.class);
                intent3.putExtra("is",true);
                startActivity(intent3);
                SharedPreferences.Editor e= PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit();
                e.putBoolean("yonghuiszhidongdenglu",false);
                e.apply();
                finish();
            }
        });
        userinfoemation=findViewById(R.id.change_user_ingormation_draw);
        Bitmap bitmap= BitmapFactory.decodeResource(MainActivity.this. getResources(),R.drawable.ic_account_circle_black_48dp);
//                int[] argb = new int[bitmap.getWidth() * bitmap.getHeight()];
//
//       bitmap.getPixels(argb, 0, bitmap.getWidth(), 0, 0, bitmap
//
//                .getWidth(), bitmap.getHeight());// 获得图片的ARGB值
//        int number=5;
//        number = number * 255 / 100;
//
//        for (int i = 0; i < argb.length; i++) {
//
//            argb[i] = (number << 24) | (argb[i] & 0xFF0000FF);
//
//        }
//
//
//        bitmap = Bitmap.createBitmap(argb, bitmap.getWidth(), bitmap
//
//                .getHeight(), Bitmap.Config.ARGB_8888);
//        user.setImageBitmap(bitmap);
        user.setColorFilter(checkColor);
        layout=drawerLayout.getChildAt(0);
      yonghuyemian_circle.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (!drawerLayout.isDrawerOpen(GravityCompat.START))
                  drawerLayout.openDrawer(GravityCompat.START);

          }
      });
//     sanchu=findViewById(R.id.main_sanchu);
       // if (yonghuyemian_circle.getBackground()==null)
         //   yonghuyemian_circle.setImageResource(R.drawable.touxiang);
     final ImageView imageView=findViewById(R.id.add);
        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(/*Objects.requireNonNull(WendangYemian.this). getApplication()*/getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
            //拥有权限，执行操作
        }

        else{
            //没有权限，向用户请求权限
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {


            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                if (userinfoemation!=null)
                    userinfoemation.setBackgroundColor(getResources().getColor(R.color.grey));
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
     imageView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             Drawable drawable= ContextCompat.getDrawable(MainActivity.this,R.drawable.add);

             dialog = new Dialog(MainActivity.this);
             if (!(imageView.getBackground()==drawable)){
                // Toast.makeText(MainActivity.this, "sfhush", Toast.LENGTH_SHORT).show();
                 View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.maindialog, null);
                 ImageView view1=inflate.findViewById(R.id.upload);
                 ImageView view2=inflate.findViewById(R.id.write);
                 view1.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                                 dialog.dismiss();
                         Intent intent=new Intent(MainActivity.this,WendangYemian.class);
                         if (progressDialog == null)
                             progressDialog=new ProgressDialog(MainActivity.this);
                         ProgressShow.showProgressDialog(progressDialog);

                             progressDialog.show();
                         startActivity(intent);
                     }
                 });
                 view2.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         dialog.dismiss();
                         Intent intent=new Intent(MainActivity.this,Markdownonline.class);
                         startActivity(intent);
                     }
                 });
                 //初始化控件
                 //将布局设置给Dialog
                 dialog.setContentView(inflate);
                 imageView.setImageResource(R.drawable.cha);
                 //获取当前Activity所在的窗体
                 Window dialogWindow = dialog.getWindow();
                 //设置Dialog从窗体底部弹出
                 assert dialogWindow != null;
                 dialogWindow.setGravity(Gravity.BOTTOM);
                 //获得窗体的属性
                 WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                 lp.width=getResources().getDisplayMetrics().widthPixels;
                 lp.height=getResources().getDisplayMetrics().heightPixels/4;
                 lp.y = 160;//设置Dialog距离底部的距离
//    将属性设置给窗体
                 dialogWindow.setAttributes(lp);
                 dialog.show();//显示对话框
                 dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                     @Override
                     public void onDismiss(DialogInterface dialog) {
                         imageView.setImageResource(R.drawable.add);
                     }
                 });
             }else{
                 dialog.dismiss();
                //Toast.makeText(MainActivity.this, "JIAJ", Toast.LENGTH_SHORT).show();
             }
         }
     });
        //几个代表页面的常量
        //drawerLayou = findViewById(R.id.main_drawlayout);
     editor= PreferenceManager.getDefaultSharedPreferences( MainActivity.this).edit();
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if (ZhuCe.zuceActivity!=null)
       ZhuCe.zuceActivity.finish();
        gaishu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpager.setCurrentItem(1);
                Bitmap oldBmp = BitmapFactory.decodeResource(MainActivity.this.getResources(),R.drawable.usernew);

                Drawable drawable = new BitmapDrawable(oldBmp);
                Bitmap oldBmp1 = BitmapFactory.decodeResource(MainActivity.this.getResources(),R.drawable.ic_date_range_black_48dp);

                Drawable drawable1 = new BitmapDrawable(oldBmp1);
               user.setBackground(drawable);

                gailun.setBackground(drawable1);
            }
        });
      wendang.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              vpager.setCurrentItem(0);
              Bitmap oldBmp = BitmapFactory.decodeResource(MainActivity.this.getResources(),R.drawable.calender);

              Drawable drawable = new BitmapDrawable(oldBmp);
              Bitmap oldBmp1 = BitmapFactory.decodeResource(MainActivity.this.getResources(),R.drawable.ic_account_circle_black_48dp);

              Drawable drawable1 = new BitmapDrawable(oldBmp1);
              gailun.setBackground(drawable);

              user.setBackground(drawable1);
          }
      });
      textView=toolbar.findViewById(R.id.mainactivity_week);

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                Request request = new Request.Builder()
                        .url(" http://47.103.205.169/api/all_summary/?token="+token)//请求接口。如果需要传参拼接到接口后面。
                        .build();//创建Request 对象

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            textView.setText("第"+preferences.getString("week","0")+"周各组周总结概览");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final String responsedata = response.body().string();
                        Log.d(TAG, "onResponse: "+responsedata);
                        try {
                            final JSONArray array = new JSONArray(responsedata);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String week = object.getString("week");
                                if (Integer.parseInt(week) > Integer.parseInt(maxweek))
                                    maxweek = week;
                                Log.d(TAG, "onResponse: "+maxweek);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (Integer.parseInt(maxweek)>0)
                                textView.setText("第"+maxweek+"周各组周总结概览");
                                else {
                                    if (mainView.preferences!=null)
                                    textView.setText("第"+mainView.preferences.getString("week","0")+"周各组周总结概览");
                                }
                            }
                        });
                    }

                });

            }

        }).start();
      editor.putBoolean("yonghuiszhidongdenglu",true);
      editor.apply();
      mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

//        sanchu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    FileAdapter.delete();
//                    positionSet.clear();
//                    WendangYemian.adapter.notifyDataSetChanged();
//
//            }
//        });
           bindViews();
        userinfoemation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userinfoemation.setBackgroundColor(getResources().getColor(R.color.gray));
                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                      vpager.setCurrentItem(1);
            }
        });
        //xuehao=head.findViewById(R.id.yonghuyemian_xuehao);
        token= preferences.getString("token","");
       // xuehao.setText(preferences.getString("yonghuxuehao","201803310"));

        //menu.se
        //yonghuyemian_circle = head.findViewById(R.id.yonghuyemian_circleView);
//        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
//        editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
//        imagePath = preferences.getString("imgsource", "");
//        if (yonghuyemian_circle.getBackground() == null)
//            yonghuyemian_circle.setImageResource(R.drawable.touxiang);
//        if (!imagePath.equals(""))
//            displayImage(imagePath);
//     //   genghuantouxiang = head.findViewById(R.id.yonghuyemian_genhuantouxiang);
//        genghuantouxiang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                } else {
//                    openAlbum();
//                }
//            }
//        });
//        circleImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: ");
//                if (!drawerLayou.isDrawerOpen(GravityCompat.START))
//                    drawerLayou.openDrawer(GravityCompat.START);
//            }
//        });
        ViewGroup.LayoutParams lp=drawerLayout.getChildAt(1).getLayoutParams();
        lp.width=getResources().getDisplayMetrics().widthPixels;
        drawerLayout.getChildAt(1) .setLayoutParams(lp);//重写drawlayout的宽
        if (mainView.weekLyList.size()<=0)
            kejiantextView.setVisibility(View.VISIBLE);
    }



    private void bindViews() {
        vpager = (ViewPager) findViewById(R.id.vpager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.setOffscreenPageLimit(1);//一次加载几个碎片
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
                   if (WendangYemian.swipe){
                       if (MainActivity.kejiantextView.getVisibility()==View.VISIBLE)
                           MainActivity.kejiantextView.setVisibility(View.INVISIBLE);

                   }
                   if (toolbar!=null)
                       toolbar.setVisibility(View.VISIBLE);
                    if (userinfoemation!=null)
                        userinfoemation.setBackgroundColor(getResources().getColor(R.color.grey));
                    Bitmap oldBmp = BitmapFactory.decodeResource(MainActivity.this.getResources(),R.drawable.calender);

                    Drawable drawable = new BitmapDrawable(oldBmp);
                    Bitmap oldBmp1 = BitmapFactory.decodeResource(MainActivity.this.getResources(),R.drawable.ic_account_circle_black_48dp);

                    Drawable drawable1 = new BitmapDrawable(oldBmp1);
                    gailun.setBackground(drawable);

                    user.setBackground(drawable1);
                    break;
                case PAGE_TWO:
                    if (MainActivity.kejiantextView.getVisibility()==View.VISIBLE)
                        MainActivity.kejiantextView.setVisibility(View.INVISIBLE);
                        toolbar.setVisibility(View.GONE);
                        if (genduoshujutextView.getVisibility()==View.VISIBLE)
                        genduoshujutextView.setVisibility(View.GONE);
                        if (userquitloading!=null)
                            userquitloading.setBackgroundColor(getResources().getColor(R.color.grey));
                    Bitmap oldBmp3= BitmapFactory.decodeResource(MainActivity.this.getResources(),R.drawable.usernew);

                    Drawable drawable3 = new BitmapDrawable(oldBmp3);
                    Bitmap oldBmp4 = BitmapFactory.decodeResource(MainActivity.this.getResources(),R.drawable.ic_date_range_black_48dp);

                    Drawable drawable4 = new BitmapDrawable(oldBmp4);
                    user.setBackground(drawable3);

                    gailun.setBackground(drawable4);
                    break;
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            //调用exit()方法{
//                    if (sanchu.getVisibility() == View.VISIBLE) {
//                        danxuam = true;
//                        for (int i : positionSet) {
//                            FileAdapter.ViewHolder holder = (FileAdapter.ViewHolder) WendangYemian.recyclerView.findViewHolderForAdapterPosition(i);
//                            if (holder == null) {
//                                continue;
//                            }
//                            holder.fileCheck.setVisibility(View.GONE);
//                            holder.fileCheck.setChecked(false);
//
//                            sanchu.setVisibility(View.GONE);
//                            l2.setVisibility(View.VISIBLE);
//
//                        }
//                    }
//                    else
                 if (drawerLayout.isDrawerOpen(GravityCompat.START))
                     drawerLayout.closeDrawer(GravityCompat.START);
                 else exit();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    openAlbum();
                else {
                    Toast.makeText(getContext(), "you denied thr permisson", Toast.LENGTH_SHORT).show();
                }
            case 2:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    }
                break;
            default:
                break;
        }
    }
    public void openAlbum() {
        Intent intentn = new Intent("android.intent.action.GET_CONTENT");
        intentn.setType("image/*");
        startActivityForResult(intentn, takePhone);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch ((requestCode)) {
            case takePhone:
                handImageOnkitKat(data);
        }
    }
    private  void handImageOnkitKat(Intent data){
        imagePath=null;
        if(data==null)
            return;
        Uri uri=data.getData();

        if (DocumentsContract.isDocumentUri(getContext(),uri)){
            String docId=DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];
                String seliction= MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,seliction);
            }else  if("com.android. providers.downloads.document".equals(uri.getAuthority())){
                Uri cotentUri= ContentUris.withAppendedId(uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(cotentUri,null);
            }}
        else  if ("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri,null);
        }
        else  if ("file".equalsIgnoreCase(uri.getScheme())){
            imagePath=uri.getPath();
        }
        editor.putString("imgsource",imagePath);
        editor.apply();
        displayImage(imagePath);
    }
    private  String getImagePath(Uri uri,String selection){
        String path=null;
        Cursor cursor=MainActivity.this. getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private  void displayImage(String imagePath) {
        if (imagePath != null) {
            xx = imagePath;
            bitmap = BitmapFactory.decodeFile(imagePath);

            // Toast.makeText(getContext(), ""+imagePath, Toast.LENGTH_SHORT).show();
            // imagePath= preferences.getString("imgsource","");
            //Toast.makeText(getContext(), ""+preferences.getString("imgsource",""), Toast.LENGTH_SHORT).show();
            yonghuyemian_circle.setImageBitmap(bitmap);
        } else {
            Toast.makeText(getContext(), "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {

        editor=PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
      //  Toast.makeText(this, ""+preferences.getBoolean("thisweekisload",false), Toast.LENGTH_SHORT).show();
        imagePath= preferences.getString("imgsource","");
//        if (yonghuyemian_circle.getBackground()==null)
//        yonghuyemian_circle.setImageResource(R.drawable.touxiang);
        bitmap = BitmapFactory.decodeFile(imagePath);
        if (progressDialog!=null)
            progressDialog.dismiss();
        if (bitmap!=null){
            usericon_draw.setImageBitmap(bitmap);
        yonghuyemian_circle.setImageBitmap(bitmap);}
        if (bitmap!=null&&Yonghuyemian.yonghuyemian_circle_new!=null)
        Yonghuyemian.yonghuyemian_circle_new.setImageBitmap(bitmap);
        if (historyholding!=null)
            historyholding.setBackgroundColor(getResources().getColor(R.color.grey));
        if (userloadcount!=null)
        userloadcount.setText(preferences.getString("userloadcount","0"));
        if (userinfoemation!=null)
            userinfoemation.setBackgroundColor(getResources().getColor(R.color.grey));
        if (userunloadcount!=null)
        userunloadcount.setText(preferences.getString("userunloadcount","0"));
        if (!preferences.getBoolean("thisweekisload",false)){
         if (userloadinfmode_draw!=null){
             userloadinfmode_draw.setText("当前状态:本周未提交");
             userloadinfmode_draw.setTextColor(getResources().getColor(R.color.colorAccent));
         }
        }else {
           if (userloadinfmode_draw!=null){
               userloadinfmode_draw.setText("当前状态:本周已提交");
               userloadinfmode_draw.setTextColor(getResources().getColor(R.color.black));
           }
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
//    public class NightModeUtils {
//        public final static int THEME_SUN = 1;
//        public final static int THEME_NIGHT = 2;
//        	 public final static boolean cheched = false;
//	 public  int getSwitchDayNightMode(Context context) {
//	        int mode = getDayNightMode(context);
//	        return mode == THEME_SUN ? THEME_NIGHT : THEME_SUN;
//	    }
//	 public  int getDayNightMode(Context context) {
//	        SharedPreferences sharedPreferences = getSharedPreferences(context);
//	        return sharedPreferences.getInt("SUN_NIGHT_MODE", THEME_SUN);
//	    }
//	 private  SharedPreferences getSharedPreferences(Context context) {
//	        return context.getSharedPreferences("NightModeDemo", Context.MODE_APPEND);
//	    }


}


