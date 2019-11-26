package com.example.zhangdabiji;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.List;

import static com.example.zhangdabiji.FileAdapter.positionSet;
import static com.example.zhangdabiji.FileManager.mContentResolver;
import static org.litepal.LitePalApplication.getContext;

public class WendangYemian extends AppCompatActivity {
   List <FileBean> fileList = new ArrayList<>();
   static   FileAdapter adapter;
    static Boolean  danxuam=true;
  static   Boolean swipe=true;
  public static   FloatingActionButton floatingActionButton;
   static RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
  private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wendangyemian);
        QbSdk.initX5Environment(getContext(), new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。
            }

            @Override
            public void onViewInitFinished(boolean b) {
            }
        });
        textView=findViewById(R.id.kejianshuju_wendangyemian);
        fileList = getFilesByType(0);
        jiazai();
        Toolbar toolbar=findViewById(R.id.wendangjiazaiyemian_toolbar);
        setSupportActionBar(toolbar);
       floatingActionButton=findViewById(R.id.floatingbutton);

        if (fileList.size()>0)
            swipe=false;
        recyclerView = findViewById(R.id.main_recycler);
        refreshLayout=findViewById(R.id.wendangyemian_sperefresh);

        LinearLayoutManager layoutManager= new LinearLayoutManager(WendangYemian.this);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new FileAdapter(fileList);
        recyclerView.setItemViewCacheSize(150);
        recyclerView.setAdapter(adapter);
        if (MainActivity.progressDialog!=null){
            MainActivity.progressDialog.dismiss();
            MainActivity.progressDialog=null;}
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileAdapter.delete();
                    positionSet.clear();
                    WendangYemian.adapter.notifyDataSetChanged();
            }
        });
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
                FileAdapter.mfileList = fileList;
                MainActivity.kejiantextView.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
                if (refreshLayout.isRefreshing());
                    refreshlayout.finishRefresh(200);
            }
        });
    }


    public    void  jiazai(){
        SharedPreferences preferences1;
        //SharedPreferences.Editor editor;
        //editor = PreferenceManager.getDefaultSharedPreferences(WendangYemian.this).edit();
        preferences1= PreferenceManager.getDefaultSharedPreferences(getContext());
        String s=preferences1.getString("bukandewendang","");
        if (!s.equals("")) {
            int i;
            String x[] = s.split(",");
            List <String> a=new ArrayList<>();

            for (i = 0; i < x.length; i++) {
              a.add  (x[i]);
            }
            s = "";
           // Toast.makeText(getContext(),a.get(0)+"", Toast.LENGTH_LONG).show();
            int x2,x1;
//            for (int j = 0; j < x.length-1; j++) {
//                for (int c = j + 1; c < x.length; c++) {
//                    if (a.get(c) < a.get(j)) {
//                        x1=a.get(c);
//                        a.set(c,a.get(j));
//                        a.set(j,x1);
//                    }
//                }
//            }排序
//            for (int j = 0; j < x.length-1; j++) {
//                for (int c = j+1; c < x.length; c++) {
//                    if (a.get(c)==a.get(j)){
//                        x2=a.get(c)+1;
//                    a.set(c,x2);}
//                }
//            }
//            for (int j = 0; j < (int)a.size()/2; j++) {
//                int z=a.get(a.size()-1-j);
//                a.set(a.size()-1-j,a.get(j));
//                a.set(j,z);
//            }

            for (i = 0; i < x.length; i++){
                for(int j=0;j<fileList.size();j++){
                    if (x[i].equals(fileList.get(j).getPath())) {
                        fileList.remove(j);
                      break;
                    }
                }
            }
           //Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
//            editor.putString("bukandewendang", s);
//            editor.apply();排序再存
        }
        if (adapter!=null)
        adapter.notifyDataSetChanged();
        if (fileList.size()<=0)
            textView.setVisibility(View.VISIBLE);
    }
    List<FileBean> getFilesByType(int fileType) {
        List<FileBean> files = new ArrayList<FileBean>();
        // 扫描files文件库

        Cursor c = null;
        try {
            FileManager.getInstance(getContext(). getApplicationContext());
            String[] columns = new String[] {MediaStore.Files.FileColumns._ID,MediaStore.Files.FileColumns.MIME_TYPE,MediaStore.Files.FileColumns.SIZE,MediaStore.Files.FileColumns.DATE_MODIFIED,MediaStore.Files.FileColumns.DATA };

            String select = "(" + MediaStore.Files.FileColumns.DATA + " LIKE '%.doc'" + " or " + MediaStore.Files.FileColumns.DATA + " LIKE '%.docx'" + " or " + MediaStore.Files.FileColumns.DATA + " LIKE '%.xls'" + " or " + MediaStore.Files.FileColumns.DATA + " LIKE '%.ppt'" + " or " + MediaStore.Files.FileColumns.DATA + " LIKE '%.txt'" + ")";

            c = mContentResolver.query(MediaStore.Files.getContentUri("external"),columns, select, null, null);
            int dataindex = c.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            //int sizeindex = c.getColumnIndex(MediaStore.Files.FileColumns.SIZE);
            while (c.moveToNext()) {
                String path = c.getString(dataindex);

                if (FileUtil.getFileType(path) == fileType) {
                    if (!FileUtil.isExists(path)) {
                        continue;
                    }


                    String [] name=path.split("/");
                    FileBean fileBean = new FileBean(name[name.length-1], FileUtil.getFileIconByPath(path),path,FileUtil.getModifiedTime(path),false);
                    files.add(fileBean);
                }
            }
            c = mContentResolver.query(MediaStore.Files.getContentUri(Environment.getExternalStorageDirectory().getAbsolutePath()+"/tencent/QQfile_recv"),columns, select, null, null);
            int datainde = c.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            //int sizeindex = c.getColumnIndex(MediaStore.Files.FileColumns.SIZE);
            while (c.moveToNext()) {
                String path = c.getString(datainde);

                if (FileUtil.getFileType(path) == fileType) {
                    if (!FileUtil.isExists(path)) {
                        continue;
                    }


                    String [] name=path.split("/");
                    FileBean fileBean = new FileBean(name[name.length-1], FileUtil.getFileIconByPath(path),path,FileUtil.getModifiedTime(path),false);
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
    public void onResume() {
        super.onResume();

    }

    @SuppressLint("RestrictedApi")
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            //调用exit()方法{
                    if (floatingActionButton.getVisibility() == View.VISIBLE) {
                        danxuam = true;
                        floatingActionButton.setVisibility(View.GONE);
                        for (int i : positionSet) {
                            FileAdapter.ViewHolder holder = (FileAdapter.ViewHolder) WendangYemian.recyclerView.findViewHolderForAdapterPosition(i);
                            if (holder == null) {
                                continue;
                            }
                            holder.fileCheck.setVisibility(View.GONE);
                            holder.fileCheck.setChecked(false);

                           floatingActionButton.setVisibility(View.GONE);

                        }
                    }
             else
                   finish();
        }
        return false;
    }
    }
