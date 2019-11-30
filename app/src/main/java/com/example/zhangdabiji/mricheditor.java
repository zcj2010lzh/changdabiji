package com.example.zhangdabiji;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static org.litepal.LitePalApplication.getContext;

public class mricheditor<fun> extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mricheditor);
        // editor.setServerImgDir("http://192.168.0.107:8080/UpLoadDemo/upload");
        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(/*Objects.requireNonNull(WendangYemian.this). getApplication()*/getContext(), Manifest.permission.CAMERA);
        if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
            //拥有权限，执行操作
        }

        else{
            //没有权限，向用户请求权限
            ActivityCompat.requestPermissions(mricheditor.this, new String[]{Manifest.permission.CAMERA},2);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "取消操作", Toast.LENGTH_LONG).show();
        }
    }


}
