package com.example.zhangdabiji;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Yonghuyemian extends Fragment {
    public static final int takePhone = 1;
    public Button genghuantouxiang;
    public CircleImageView yonghuyemian_circle;
    View head;
    String xx;
    String imagePath=null;
    public Bitmap bitmap;
    private Uri imguri;
      private SharedPreferences preferences;
      private  SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_yonghuyemian, container, false);
        NavigationView navigationView = view.findViewById(R.id.yonghuyemian_navigation);
        Menu menu = navigationView.getMenu();
        //menu.se
        head=navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dengluzhuce:
                        Intent intent = new Intent(getContext(), ZhuCe.class);
                        startActivity(intent);
                        getActivity().onBackPressed();
                        break;
                    case R.id.menu_chakanqitawendang:
                        Intent intent1 = new Intent(getContext(), Zhedieshitu.class);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        yonghuyemian_circle = head. findViewById(R.id.yonghuyemian_circleView);
        preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        editor=PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        yonghuyemian_circle.setImageResource(R.drawable.touxiang);
        imagePath= preferences.getString("imgsource","");
        Toast.makeText(getContext(), ""+preferences.getString("imgsource",""), Toast.LENGTH_SHORT).show();
        genghuantouxiang = head. findViewById(R.id.yonghuyemian_genhuantouxiang);
        genghuantouxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    openAlbum();
                else {
                    Toast.makeText(getContext(), "you denied thr permisson", Toast.LENGTH_SHORT).show();
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
        switch ((requestCode)){
            case  takePhone:
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

        displayImage(imagePath);
    }
    private  String getImagePath(Uri uri,String selection){
        String path=null;
        Cursor cursor=getActivity(). getContentResolver().query(uri,null,selection,null,null);
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
            editor.putString("imgsource",imagePath);
           // Toast.makeText(getContext(), ""+imagePath, Toast.LENGTH_SHORT).show();
           // imagePath= preferences.getString("imgsource","");
            Toast.makeText(getContext(), ""+preferences.getString("imgsource",""), Toast.LENGTH_SHORT).show();
            yonghuyemian_circle.setImageBitmap(bitmap);
        } else {
            Toast.makeText(getContext(), "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}

