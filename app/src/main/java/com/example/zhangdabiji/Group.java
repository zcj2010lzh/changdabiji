package com.example.zhangdabiji;

public class Group {
    public String getgName() {
        return name;
    }

    public void setgName(String name) {

        this.name = name;
    }
Group(String name,String zu){
        this.name=name;
        this.zu=zu;
    }
    Group(String name){
        this.name=name;
    }

    public String getZu() {
        return zu;
    }

    public void setZu(String zu) {
        this.zu = zu;
    }

    protected  String zu;
    protected    String name;
}
