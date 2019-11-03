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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.zhangdabiji.FileManager.mContentResolver;

public class WendangYemian extends Fragment {
   protected List <FileBean> fileList = new ArrayList<>();
   protected  static   FileAdapter adapter;
  static   Boolean swipe=true;
   protected static RecyclerView recyclerView;
    SmartRefreshLayout refreshLayout;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.wendangyemian,container,false);
        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getActivity(). getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
            //拥有权限，执行操作
            fileList = getFilesByType(0);
            jiazai();
        }
        else{
            //没有权限，向用户请求权限
           ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
            if (fileList.size()==0){
              MainActivity.gejiantextView.setVisibility(View.VISIBLE);
            }
        }
        if (fileList.size()>0)
            swipe=false;
       recyclerView = view. findViewById(R.id.main_recycler);
        refreshLayout=view.findViewById(R.id.wendangyemian_sperefresh);

        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
       adapter = new FileAdapter(fileList);
        recyclerView.setItemViewCacheSize(100);
        recyclerView.setAdapter(adapter);
       /* adapter.setLongClickLisenter(new FileAdapter.LongClickLisenter() {
            @Override
            public void LongClickLisenter(int position) {


                adapter.del(position);
            }
        });*/
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                   swipe=false;
                fileList = getFilesByType(0);
                jiazai();
                adapter.mfileList = fileList;
                MainActivity.gejiantextView.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
                   if (refreshLayout.isRefreshing())
                refreshLayout.finishRefresh(1000);
            }
        });
                return view;
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
        SharedPreferences preferences1;
        SharedPreferences.Editor editor;
        editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        preferences1= PreferenceManager.getDefaultSharedPreferences(getContext());
        String s=preferences1.getString("bukandewendang","");
        if (!s.equals("")) {
            int i;
            String x[] = s.split(",");
            List <Integer> a=new ArrayList<>();

            for (i = 0; i < x.length; i++) {
              a.add  (Integer.parseInt(x[i]));
            }
            s = "";
            Toast.makeText(getContext(),a.get(0)+"", Toast.LENGTH_LONG).show();
            int x1;
            for (int j = 0; j < x.length; j++) {
                for (int c = j + 1; c < x.length; c++) {
                    if (a.get(c) >= a.get(i)) {
                       x1=a.get(c);
                       x1+=1;
                        a.set(c,x1);
                    }
                }
                fileList.remove(a.get(j));
            }
            for (i = 0; i < x.length; i++)
                s += String.valueOf(a.get(i)) + ",";
            //Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
            editor.putString("bukandewendang", s);
            editor.apply();
        }
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
                    FileBean fileBean = new FileBean(path, FileUtil.getFileIconByPath(path),path,FileUtil.getModifiedTime(path));
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
