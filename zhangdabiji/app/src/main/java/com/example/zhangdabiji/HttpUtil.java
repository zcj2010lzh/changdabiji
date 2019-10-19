package com.example.zhangdabiji;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    private static Boolean isfailure;
    private List<String>  jianzhi;
    public  static  void sendHttpUtilRequest(String address,RequestBody body,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address)
                .url(address)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    public  static Boolean  generateJsondata(List<JsonData>  data,String address) {
     for (int i=0;i<data.size();i++){
         RequestBody body=new FormBody.Builder()
                 .add(data.get(i).getJianzhi(),data.get(i).getZhi())
                 .build();
         sendHttpUtilRequest(address,body ,new Callback() {
             @Override
             public void onFailure(@NotNull Call call, @NotNull IOException e) {
                 isfailure=true;
             }

             @Override
             public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                 isfailure=false;
             }
         });
     }
     if (isfailure)
     return false;
     else
         return  true;
    }
    public  void  generatejianzhi(String c){

    }
}
