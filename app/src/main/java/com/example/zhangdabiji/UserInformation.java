package com.example.zhangdabiji;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.zhangdabiji.MainActivity.imagePath;
import static org.litepal.LitePalApplication.getContext;

public class UserInformation extends AppCompatActivity implements View.OnClickListener {
    public static final int takePhone = 1;
    String xx;
    Switch s;

    public Bitmap bitmap;
    TextView textView;
    private static final String TAG = "UserInformation";
    Dialog dialog;
    SharedPreferences.Editor editor;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        editor= PreferenceManager.getDefaultSharedPreferences(UserInformation.this).edit();
        textView=findViewById(R.id.change_user_ingormation_group);
        LinearLayout layout=findViewById(R.id.changeavater);
        layout.setOnClickListener(this);
        LinearLayout layout1=findViewById(R.id.changeemail);
        layout1.setOnClickListener(this);
        ImageView imageView=findViewById(R.id.changeqroup);
        imageView.setOnClickListener(this);
        s=findViewById(R.id.openPrivacy);

        if (mainView.preferences.getBoolean("openPrivacy",false))
            s.setChecked(true);
        else s.setChecked(false);

       s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked){
                   editor.putBoolean("openPrivacy",isChecked);

               }else editor.putBoolean("openPrivacy",isChecked);
               editor.apply();

           }
       });
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()) {
          case R.id.changeavater:
              openAlbum();
              break;
          case R.id.changeemail:
              Intent intent=new Intent(UserInformation.this,ChangePassword.class);
              startActivity(intent);
              break;
          case R.id.changeqroup:
              dialog = new Dialog(this);
              //填充对话框的布局
              final View inflate = LayoutInflater.from(this).inflate(R.layout.userinformationchangegroup, null);
              RadioGroup group=inflate.findViewById(R.id.change_user_ingormation_group_radio);
              group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                  @Override
                  public void onCheckedChanged(RadioGroup group, int checkedId) {
                      RadioButton button=inflate. findViewById(R.id.anzhuo);
                      RadioButton button1=inflate. findViewById(R.id.yunwei);
                      RadioButton button2=inflate. findViewById(R.id.yunying);
                      RadioButton button3=inflate. findViewById(R.id.qianduan);
                      RadioButton button4=inflate. findViewById(R.id.ui);
                      RadioButton button5=inflate. findViewById(R.id.php);
                      switch (checkedId){
                          case R.id.anzhuo:
                              textView.setText(button.getText());
                              break;
                          case R.id.yunwei:
                              textView.setText(button1.getText());
                              break;
                          case R.id.yunying:
                              textView.setText(button2.getText());
                              break;
                          case R.id.qianduan:
                              textView.setText(button3.getText());
                              break;
                          case R.id.ui:
                              textView.setText(button4.getText());
                              break;
                          case R.id.php:
                              textView.setText(button5.getText());
                              break;
                      }

                      dialog.dismiss();
                  }
              });
              //初始化控件
              //将布局设置给Dialog
              dialog.setContentView(inflate);
              //获取当前Activity所在的窗体
              Window dialogWindow = dialog.getWindow();
              //设置Dialog从窗体底部弹出
              assert dialogWindow != null;
              dialogWindow.setGravity(Gravity.BOTTOM);
              //获得窗体的属性
              WindowManager.LayoutParams lp = dialogWindow.getAttributes();
              lp.width=getResources().getDisplayMetrics().widthPixels*3/7;
              lp.height=getResources().getDisplayMetrics().heightPixels/3;
              lp.y=getResources().getDisplayMetrics().heightPixels/8;
              lp.x = getResources().getDisplayMetrics().widthPixels/6;//设置Dialog距离底部的距离
//    将属性设置给窗体
              dialogWindow.setAttributes(lp);
              dialog.show();//显示对话框
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
        Cursor cursor=UserInformation.this. getContentResolver().query(uri,null,selection,null,null);
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
            Toast.makeText(this, "修改头像成功", Toast.LENGTH_SHORT).show();
            // Toast.makeText(getContext(), ""+imagePath, Toast.LENGTH_SHORT).show();
            // imagePath= preferences.getString("imgsource","");
            //Toast.makeText(getContext(), ""+preferences.getString("imgsource",""), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
            finish();
            System.exit(0);
        }
    }

}

