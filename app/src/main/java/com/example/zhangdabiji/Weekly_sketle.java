package com.example.zhangdabiji;

public class Weekly_sketle {
    public Weekly_sketle(String week, String weekly_name,String url,String file_type,Boolean ispublic) {
        this.week = week;
        this.ispublic=ispublic;
        this.file_type=file_type;
        this.url=url;
        Weekly_name = weekly_name;
    }
    public Weekly_sketle(String newdate, String week, String weekly_name,String url,String file_type,Boolean ispublic) {
        this.week = week;
        this.ispublic=ispublic;
        this.newdate=newdate;
        this.file_type=file_type;
        this.url=url;
        Weekly_name = weekly_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private  String url;
    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeekly_name() {
        return Weekly_name;
    }

    public void setWeekly_name(String weekly_name) {
        Weekly_name = weekly_name;
    }

    private String week;

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public Boolean getIspublic() {
        return ispublic;
    }

    public void setIspublic(Boolean ispublic) {
        this.ispublic = ispublic;
    }

    private Boolean ispublic;
    private String file_type;
    private String Weekly_name;

    public String getNewdate() {
        return newdate;
    }

    public void setNewdate(String newdate) {
        this.newdate = newdate;
    }

    private  String newdate;
}
