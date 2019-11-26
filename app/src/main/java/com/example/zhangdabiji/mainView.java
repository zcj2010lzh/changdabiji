package com.example.zhangdabiji;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;


public class mainView extends Fragment {
    int refreshcount=0;
   static ArrayList<ArrayList<Weekly_sketle>>  weekLyList=new ArrayList<>();
      private ArrayList<MainviewBean>  beans=new ArrayList<>();
    private static final String TAG = "mainView";
    private int[] bili = new int[6];
    private RecyclerView recyclerView;
    private MainViewAdapter adapter;
    public static   SharedPreferences preferences;
   public  static     SharedPreferences.Editor editor;
    public  static String thisday=null;
    private long startingtime;
    Boolean isfresh=false;
    private String mainview_maxweek="0";
    private long todaytime;
    private  final   String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    public  static String thisweek;
    private static String[]  group=new String[]{"安卓组7","运维组3","运营组3","前端组3","UI组3","PHP组4"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.mainview,container,false);
       recyclerView= view.findViewById(R.id.mainview_recycler);

        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        preferences= PreferenceManager.getDefaultSharedPreferences(view.getContext());
        editor= PreferenceManager.getDefaultSharedPreferences(view.getContext()).edit();
        for (int i=0;i<6;i++){
           weekLyList.add(new ArrayList<Weekly_sketle>());}
        recyclerView.setLayoutManager(layoutManager);
       adapter=new MainViewAdapter(beans);
        Calendar calendar=Calendar.getInstance();
        //Date date=new Date();
//         startingtime=1574571014568L;
//         todaytime=date.getTime();
//        editor.putLong("startingtime",startingtime);
        thisday=weekDays[calendar.get(Calendar.DAY_OF_WEEK)-1];
        editor.apply();
        thisweek= preferences.getString("week","-1");
        final SmartRefreshLayout refreshLayout=view.findViewById(R.id.mainview_smartrefresh);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0;i<bili.length;i++) {
                            bili[i] = 0;
                            if (i>=weekLyList.size())
                                continue;
                           weekLyList.get(i).clear();
                        }
                        loagMessge();
                    }
                }).start();
                if (refreshlayout.isRefreshing())
                    refreshlayout.finishRefresh(500);
                if (MainActivity.kejiantextView.getVisibility()== View.VISIBLE)
                    MainActivity.kejiantextView.setVisibility(View.GONE);
              isfresh=true;
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MainViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent=new Intent(view.getContext(),HistoryHandind.class);
                intent.putExtra("frommainview",true);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                loagMessge();
            }
                }).start();

        return view;
    }
    private void  loagMessge(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                Request request = new Request.Builder()
                        .url(" http://47.103.205.169/api/all_summary/?token="+MainActivity.token)//请求接口。如果需要传参拼接到接口后面。
                        .build();//创建Request 对象

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        if (!isfresh)
                            MainActivity.kejiantextView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final String responsedata = response.body().string();
                       // Log.d(TAG, "onResponse: "+responsedata);
                        try {
                            final JSONArray array = new JSONArray(responsedata);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String title = object.getString("title");
                                String file_type = object.getString("file_type");
                                String ispublic=object.getString("is_public");
                                String url = "http://47.103.205.169" + object.getString("file");
                                String week = object.getString("week");
                                ArrayList<Weekly_sketle> items = new ArrayList<>();
                                if (Integer.parseInt(week)>Integer.parseInt(mainview_maxweek))
                                    mainview_maxweek=week;
                                String[] username = object.getString("author").split("-");
//                               if (Integer.parseInt(week)<Integer.parseInt(preferences.getString("week","-1")))
//                                   Log.d(TAG, "onResponse: "+Integer.parseInt(preferences.getString("week","-1")));
                                   int j = 0;

                                int groupsize=0;
                               // Log.d(TAG, "onResponse: "+array.length());
                                   for (; j <beans.size()||beans.size()==0; j++) {
                                      // MainViewAdapter.MainHolder holder= (MainViewAdapter.MainHolder) recyclerView.findViewHolderForAdapterPosition(j);
                                       //Log.d(TAG, "onResponse: "+group[j]+""+username[0]);
                                       int n=0;
                                       for (;n<6;n++){
                                           if (username[0].equals(group[n].substring(0,group[n].length()-1)))
                                               break;//用户是哪个组的

                                       }
                                       if (beans.size()==0){
                                           //Log.d(TAG, "onResponse: "+j+""+n);
                                           bili[0]++;
                                           beans.add(new MainviewBean((group[n].charAt(group[n].length()-1)-48),bili[j],username[0],bili[j]+"/"+(group[n].charAt(group[n].length()-1)-48)));

                                           items.add(new Weekly_sketle(week, username[username.length-1],title,url,file_type,ispublic.equals("true")));
                                           weekLyList.add(0,items);
//                                        assert holder != null;
//                                        holder.bar.setMax(group[x].charAt(group[x].length()-1)-48);
//                                        holder.bar.setProgress(bili[j]);
//                                        holder.biLi.setText(holder.bar.getProgress()+"/"+holder.bar.getMax());
                                           //Log.d(TAG, "onResponse: "+weekLyList.get(0).size());
                                           continue;
                                       }
                                       if (username[0].equals(beans.get(j).getGroupName())) {
                                           // Log.d(TAG, "onResponse: "+weekLyList.get(j).size()+""+j);
                                           bili[j]++;
                                           beans.get(j).setProgress(bili[j]);
                                           beans.get(j).setBili(bili[j]+"/"+(group[n].charAt(group[n].length()-1)-48));
                                           weekLyList.get(j).add(new Weekly_sketle(week,username[username.length-1], title, url,file_type,ispublic.equals("true")));
//                                           weekLyList.add(j, weekLyList.get(j));//手贱
                                           // Log.d(TAG, "onResponse: "+weekLyList.get(j).size());

                                           //Log.d(TAG, "onResponse: "+bili[j]);
                                           Log.d(TAG, "onResponse: "+j);
                                           break;
                                       }

                                               if (!(username[0].equals(beans.get(j).getGroupName()))){
                                                   groupsize++;
                                                   if (groupsize==beans.size()) {
                                                       if (j < 5)
                                                           bili[++j]++;
                                                       else bili[5]++;
//                                                        for (int s=0;s<)
                                                       beans.add(new MainviewBean((group[n].charAt(group[n].length() - 1) - 48), bili[j], username[0], bili[j] + "/" + (group[n].charAt(group[n].length() - 1) - 48)));
//                                                       Log.d(TAG, "onResponse: " + beans.get(beans.size() - 1).getGroupName());
                                                       //Log.d(TAG, "onResponse: "+items.size());
                                                       items.add(new Weekly_sketle(week, username[username.length - 1], title, url, file_type, ispublic.equals("true")));
                                                       weekLyList.add(j,items);
                                                       Log.d(TAG, "onResponse: "+beans.get(beans.size()-1).getGroupName());

                                                       Log.d(TAG, "onResponse: "+j);
                                                       Log.d(TAG, "onResponse: "+weekLyList.get(j).size());
                                                       break;
                                                   }
                                                   }
                                   }
                               }


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Log.d(TAG, "run: "+beans.size());
//                                    for (int i=0;i<beans.size();i++){
//                                        for (int j=0;j<weekLyList.get(i).size();j++){
//                                            if (Integer.parseInt(weekLyList.get(i).get(j).getNewdate())<Integer.parseInt(mainview_maxweek))
//                                                weekLyList.get(i).remove(j);
//                                        }
//                                    }
                                    if (beans.size()<=0){
                                        if (!(MainActivity.kejiantextView.getVisibility()==View.VISIBLE))
                                        MainActivity.genduoshujutextView.setVisibility(View.VISIBLE);
                                    //    Toast.makeText(getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                                    }
                                    else      MainActivity.genduoshujutextView.setVisibility(View.INVISIBLE);
                                    adapter.notifyDataSetChanged();
                                    for (int i=0;i<weekLyList.size();i++){
                                        Log.d(TAG, "run: "+weekLyList.get(i).size());
                                    }
                                    thisweek=mainview_maxweek;
                                    if (preferences.getString("week","-1").equals("-1")){

                                        editor.putString("week",mainview_maxweek);
                                        editor.apply();
                                    }
                                    if (MainActivity.kejiantextView.getVisibility()==View.VISIBLE&&beans.size()>0)
                                        MainActivity.kejiantextView.setVisibility(View.INVISIBLE);
                                    if (thisday.equals("星期一")&&(Integer.parseInt(preferences.getString("week","-1"))==Integer.parseInt(mainview_maxweek))) {
                                        if (preferences.getBoolean("thisweekisload",false)){
                                            int unoadcount=Integer.parseInt(preferences.getString("userunloadcount","0"));
                                            editor.putString("userunloadcount",String.valueOf(++unoadcount));
                                        }
                                        String beforework=  preferences.getString("week","-1");
                                        editor.putString("week",String.valueOf((Integer.parseInt(beforework)+1)));
                                        editor.putBoolean("thisweekisload",false);
                                        editor.apply();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        }).start();

    }
}
