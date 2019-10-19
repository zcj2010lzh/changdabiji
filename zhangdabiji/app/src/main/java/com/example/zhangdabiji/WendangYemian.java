package com.example.zhangdabiji;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.zhangdabiji.FileManager.mContentResolver;

public class WendangYemian extends Fragment {
    private List<FileBean> fileList = new ArrayList<>();
 SharedPreferences preferences;
    SharedPreferences.Editor  editor;
    SwipeRefreshLayout refreshLayout;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.wendangyemian,container,false);
        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getActivity(). getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
            //拥有权限，执行操作

            fileList = getFilesByType(0);

        }
        else{
            //没有权限，向用户请求权限
           ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }
        RecyclerView recyclerView = view. findViewById(R.id.main_recycler);
        refreshLayout=view.findViewById(R.id.wendangyemian_sperefresh);
        refreshLayout.setColorSchemeColors(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        jiazai();
                        fileList=getFilesByType(0);
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                if (refreshLayout.isRefreshing())
                    refreshLayout.setRefreshing(false);
            }
        });
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
           jiazai();
        FileAdapter adapter = new FileAdapter(fileList);
        recyclerView.setAdapter(adapter);
        return  view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //通过requestCode来识别是否同一个请求
        if (requestCode == 2){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                fileList=getFilesByType(0);
            }else{
                //用户不同意，向用户展示该权限作用
              /*  if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                           // .setMessage(R.string.storage_permissions_remind)
                            .setPositiveButton("OK", (dialog1, which) ->
                                    ActivityCompat.requestPermissions(this,
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            EventConstConfig.CODE_FOR_CAMERA_PERMISSION))
                            .setNegativeButton("Cancel", null)
                            .create()
                            .show();*/
                }
            }
        }
    public    void  jiazai(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final   SharedPreferences preferences1;
                SharedPreferences.Editor editor;
                editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                preferences1= PreferenceManager.getDefaultSharedPreferences(getContext());
                String s=preferences1.getString("bukandewendang","");
                fileList=getFilesByType(0);
                if (!s.equals("")){
                    int i;
                    String x []   =s.split(",");
                    int a[]=new int[100];
                    //  Toast.makeText(view.getContext(),x+"", Toast.LENGTH_LONG).show();
                    for ( i= 0; i < x.length; i++) {
                        a[i]=Integer.parseInt(x[i]);
                    }
                    s="";
                    for (int j = 0; j < x.length; j++) {
                       /*  for(int c=j+1;c<x.length;c++){
                             if (a[j]<=a[c])
                                 a[c]++;
                         }*/

                        fileList.remove(a[j]);
                    }
                    for(i=0;i<x.length;i++)
                        s+=String.valueOf(a[i])+",";
                    editor.putString("bukandewendang",s);
                    editor.apply();
                }
            }
        }).start();
    }
    public List<FileBean> getFilesByType(int fileType) {
        List<FileBean> files = new ArrayList<FileBean>();
        // 扫描files文件库

        Cursor c = null;
        try {
            FileManager.getInstance(getContext(). getApplicationContext());
            c = mContentResolver.query(MediaStore.Files.getContentUri("external"), new String[]{"_id", "_data", "_size"}, null, null, null);
            int dataindex = c.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int sizeindex = c.getColumnIndex(MediaStore.Files.FileColumns.SIZE);
            while (c.moveToNext()) {
                String path = c.getString(dataindex);

                if (FileUtil.getFileType(path) == fileType) {
                    if (!FileUtil.isExists(path)) {
                        continue;
                    }
                    long size = c.getLong(sizeindex);
                    String [] name=path.split("/");
                    FileBean fileBean = new FileBean(name[name.length-1], FileUtil.getFileIconByPath(path),path);
                    files.add(fileBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return files;
    }
    }
