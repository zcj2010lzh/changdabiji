package com.example.zhangdabiji;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {



    public static String sendjianzhiHttpUtilRequest(String address, RequestBody body, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        String reponsedata="";
        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        try {
            Response response= client.newCall(request).execute();
            reponsedata =response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

      return reponsedata;
    }

    public static String  generateJsondata(List<JsonData> data, String address,okhttp3.Callback callback) {
        String x="";
        for (int i = 0; i < data.size(); i++) {
            RequestBody body = new FormBody.Builder()
                    .add(data.get(i).getJianzhi(), data.get(i).getZhi())
                    .build();
             x=   sendjianzhiHttpUtilRequest(address,body,callback);
             if (x=="")
                 return "";
        }
        return x;
    }
    public void sendfiletxtiHttpUtilRequest (String address){
        File file = new File("");
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/msword"), file);//将file转换成RequestBody文件
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("name", "filename", fileBody)
                .addFormDataPart("name", "value")
                .build();
    }
}