package com.example.zhangdabiji;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import static com.example.zhangdabiji.MainActivity.imagePath;
import static com.example.zhangdabiji.mainView.preferences;

public class Yonghuyemian extends Fragment implements View.OnClickListener {
    public static final int takePhone = 1;
    public Button genghuantouxiang;

    View head;
    String xx;
    //private String imagePath=null;
    public Bitmap bitmap;
    private Uri imguri;
    private  static View view;
    private TextView user_name,user_group;
   public  static   TextView userloadcount,userunloadcount;
   public  static   ImageView yonghuyemian_circle_new;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.yonghuyemian, container, false);

        //menu.se
        head=view.findViewById(R.id.head);

        LinearLayout l1=view.findViewById(R.id.historyHanding);
        LinearLayout l2=view.findViewById(R.id.change_user_ingormation);
        LinearLayout l3=view.findViewById(R.id.notification);
        LinearLayout l4=view.findViewById(R.id.unload);
        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        userloadcount=view.findViewById(R.id.userloadingcount);
        userunloadcount=view.findViewById(R.id.userunloadcount);
        userloadcount.setText(preferences.getString("userloadcount","0"));
        userunloadcount.setText(preferences.getString("userunloadcount","0"));
        yonghuyemian_circle_new=view.findViewById(R.id.user_icon);
        bitmap = BitmapFactory.decodeFile( imagePath);
        if (bitmap!=null&&yonghuyemian_circle_new!=null)
            yonghuyemian_circle_new.setImageBitmap(bitmap);

        imagePath= preferences.getString("imgsource","");
//        if (yonghuyemian_circle.getBackground()==null)
//        yonghuyemian_circle.setImageResource(R.drawable.touxiang);

//        ViewGroup.LayoutParams params = navigationView.getLayoutParams();
//        params.width = getResources().getDisplayMetrics().widthPixels ;
//        params.height=getResources().getDisplayMetrics().heightPixels*5/11;
//        head.setLayoutParams(params);
//        Menu menu=navigationView.getMenu();
//        Drawable icon= (Drawable)  menu.getItem(0).getIcon();
//        int width=icon.getMinimumHeight()/2;
//        int height=icon.getMinimumWidth()/2;
//        ImageView lastingFile= (ImageView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.historyHanding));
//        lastingFile.setImageResource(R.drawable.ic_chevron_right);
//
//        ImageView denluzhuc= (ImageView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.change_user_ingormation));
//        denluzhuc.setImageResource(R.drawable.ic_chevron_right);
//        denluzhuc.setMaxWidth(width);
//
//        denluzhuc.setMaxHeight(height/2);
//        ImageView notification= (ImageView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.notification));
//        notification.setImageResource(R.drawable.ic_chevron_right);
//        notification.setMaxWidth(width/2);
//        notification.setMaxHeight(height/2);

        user_name=view.findViewById(R.id.user_name);
        user_group=view.findViewById(R.id.user_group);
        user_name.setText(preferences.getString("username",""));
        user_group.setText(preferences.getString("usergroup",""));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        yonghuyemian_circle = head. findViewById(R.id.yonghuyemian_circleView);
//        preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
//        editor=PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
//        imagePath= preferences.getString("imgsource","");
//        if (yonghuyemian_circle.getBackground()==null)
//            yonghuyemian_circle.setImageResource(R.drawable.touxiang);
//        if (!imagePath.equals(""))
//            displayImage(imagePath);
//        //Toast.makeText(getContext(), ""+preferences.getString("imgsource",""), Toast.LENGTH_SHORT).show();
//       // genghuantouxiang = head. findViewById(R.id.yonghuyemian_genhuantouxiang);
//        genghuantouxiang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                } else {
//                    openAlbum();
//                }
//            }
//        });
    }


//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case  1:
//                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
//                    openAlbum();
//                else {
//                    Toast.makeText(getContext(), "you denied thr permisson", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//                break;
//        }
//    }
//    public void openAlbum() {
//        Intent intentn = new Intent("android.intent.action.GET_CONTENT");
//        intentn.setType("image/*");
//        startActivityForResult(intentn, takePhone);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        switch ((requestCode)){
//            case  takePhone:
//                handImageOnkitKat(data);
//        }
//    }
//    private  void handImageOnkitKat(Intent data){
//        imagePath=null;
//        if(data==null)
//            return;
//        Uri uri=data.getData();
//
//        if (DocumentsContract.isDocumentUri(getContext(),uri)){
//            String docId=DocumentsContract.getDocumentId(uri);
//            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
//                String id=docId.split(":")[1];
//                String seliction= MediaStore.Images.Media._ID+"="+id;
//                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,seliction);
//            }else  if("com.android. providers.downloads.document".equals(uri.getAuthority())){
//                Uri cotentUri= ContentUris.withAppendedId(uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
//                imagePath=getImagePath(cotentUri,null);
//            }}
//        else  if ("content".equalsIgnoreCase(uri.getScheme())){
//            imagePath=getImagePath(uri,null);
//        }
//        else  if ("file".equalsIgnoreCase(uri.getScheme())){
//            imagePath=uri.getPath();
//        }
//        editor.putString("imgsource",imagePath);
//        editor.apply();
//        displayImage(imagePath);
//    }
//    private  String getImagePath(Uri uri,String selection){
//        String path=null;
//        Cursor cursor=getActivity(). getContentResolver().query(uri,null,selection,null,null);
//        if (cursor!=null){
//            if (cursor.moveToFirst()){
//                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            }
//            cursor.close();
//        }
//        return path;
//    }
//    private  void displayImage(String imagePath) {
//        if (imagePath != null) {
//            xx = imagePath;
//            bitmap = BitmapFactory.decodeFile(imagePath);
//
//            // Toast.makeText(getContext(), ""+imagePath, Toast.LENGTH_SHORT).show();
//            // imagePath= preferences.getString("imgsource","");
//            //Toast.makeText(getContext(), ""+preferences.getString("imgsource",""), Toast.LENGTH_SHORT).show();
//            yonghuyemian_circle.setImageBitmap(bitmap);
//        } else {
//            Toast.makeText(getContext(), "failed to get image", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.historyHanding:
                Intent intent=new Intent(view.getContext(),HistoryHandind.class);
                startActivity(intent);
                //  Objects.requireNonNull(getActivity()).onBackPressed();//销毁碎片
                break;
            case R.id.change_user_ingormation:
                Intent intent1=new Intent(view.getContext(),UserInformation.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);

                break;
            case R.id.notification:
                Intent intent2=new Intent(view.getContext(),Notification.class);
                startActivity(intent2);
                break;
            case R.id.unload:
                Intent intent3=new Intent(view.getContext(),ZhuCe.class);
                intent3.putExtra("is",true);
                startActivity(intent3);
                SharedPreferences.Editor e= PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit();
                e.putBoolean("yonghuiszhidongdenglu",false);
                e.apply();
                Objects.requireNonNull(getActivity()).onBackPressed();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }
}

