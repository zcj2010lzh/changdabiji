package com.example.zhangdabiji;

public class JsonData {
    JsonData(String jianzhi1,String zhi1){
        jianzhi=jianzhi1;
        zhi=zhi1;
    }
    public String getJianzhi() {
        return jianzhi;
    }

    public void setJianzhi(String jianzhi) {
        this.jianzhi = jianzhi;
    }

    public String getZhi() {
        return zhi;
    }

    public void setZhi(String zhi) {
        this.zhi = zhi;
    }

    String jianzhi;
    String zhi;
}
