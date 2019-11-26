package com.example.zhangdabiji;

import android.content.ContentResolver;
import android.content.Context;

public class FileManager {

    private static FileManager mInstance;
    private static Context mContext;
     public static ContentResolver mContentResolver;
    private static Object mLock = new Object();

    public static FileManager getInstance(Context context) {//        WendangYemian一定要调用这个方法
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new FileManager();
                    mContext = context;
                    mContentResolver = context.getContentResolver();
                }
            }
        }
        return mInstance;
    }
}