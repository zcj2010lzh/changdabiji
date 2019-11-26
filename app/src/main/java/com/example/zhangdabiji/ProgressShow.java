package com.example.zhangdabiji;

import android.app.ProgressDialog;

public class ProgressShow {
    protected static void showProgressDialog(ProgressDialog progressDialog) {

            progressDialog.setMessage("load....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            progressDialog.setCancelable(true);// 设置是否可以通过点击Back键取消
            progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            progressDialog.setIcon(R.drawable.jiazai);//
            progressDialog.setMax(18);


    }

    static  void closeProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null)
            progressDialog.dismiss();
    }
}
