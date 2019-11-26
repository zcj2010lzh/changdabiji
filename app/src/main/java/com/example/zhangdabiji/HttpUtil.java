package com.example.zhangdabiji;

import android.content.Context;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {


     public static void  getOkhttp(String address, Callback ca){
         OkHttpClient client=new OkHttpClient();
         Request request=new Request.Builder().url(address).build();
         client.newCall(request).enqueue(ca);
     }

    static void upLoadeFile(final String filename, String docurl, String ispublic, final Context context, String token){
         char[] is=ispublic.toCharArray();

         is[0]-=32;;
         ispublic=String.copyValueOf(is);
        RequestBody body=new FormBody.Builder()
              .add("is_public",ispublic)
                .add("file_type","docx")
              .add("title",filename).build();
        OkGo.<File>post("http://47.103.205.169/api/summary/?token="+token)
                .isMultipart(true)
                .params("file",new File(docurl),filename)
                .upFile(new File(docurl))
                .params("file",new File(docurl))
                .upRequestBody(body)
                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(Response<File> response) {

                    }
                });
               OkHttpClient client=new OkHttpClient();
        MediaType type=MediaType.parse("application/octet-stream");//"text/xml;charset=utf-8"
        File file=new File(docurl);
        RequestBody fileBody=RequestBody.create(type,file);
        RequestBody body1=new FormBody.Builder()
                .add("is_public",ispublic)
                .add("file_type","docx")
                .add("title",filename).build();
          Request request=new Request
                  .Builder()
                  .post(body1)
                  .url("http://47.103.205.169/api/summary/?token="+token)
                  .post(fileBody).build();
          client.newCall(request).enqueue(new Callback() {
              @Override
              public void onFailure(@NotNull Call call, @NotNull IOException e) {
                  Log.d("Zhedieshitu", "onFailure: ");
              }

              @Override
              public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {

              }
          });
                       
    }
    }

