package com.example.zhangdabiji;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class netChange {
    private static Boolean wifiunload=false;
    private static Boolean dataunload=false;
    public  static  Boolean   detectInternerState(Context context){
        if (android.os.Build.VERSION.SDK_INT < 24) {
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
             if (!wifiNetworkInfo.isConnected()){
               //  Toast.makeText(context, "请连接网络", Toast.LENGTH_SHORT).show();
             return  false;}
             if (!dataNetworkInfo.isConnected())
             return  true;
        }else {
            //获得ConnectivityManager对象
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            try{
                //获取所有网络连接的信息
                NetworkInfo[] networks = connectivityManager.getAllNetworkInfo();
                //用于存放网络连接信息
                StringBuilder sb = new StringBuilder();
                //通过循环将网络信息逐个取出来
                for (int i=0; i < networks.length; i++){
                    //获取ConnectivityManager对象对应的NetworkInfo对象
                    NetworkInfo networkInfo = networks[i];
                    int type=networkInfo.getType();
                    if(ConnectivityManager.TYPE_WIFI==type||ConnectivityManager.TYPE_MOBILE==type){
                        if ((networkInfo.isConnected()&&ConnectivityManager.TYPE_WIFI==type))
                            wifiunload=true;
                        if ((networkInfo.isConnected()&&ConnectivityManager.TYPE_MOBILE==type))
                           dataunload=true;

                    }

                }
                if (!dataunload&&!wifiunload)
                    return false;
                else return true;

            }
            catch(Exception e){
                       e.printStackTrace();
            }

        }
     return true;
}
}
