package com.example.zhangdabiji;
public class FileBean { //文件格式
    public String getPath() {
        return path;
    }
    public String getrealPath() {
        return  realpath;
    }
    public int getIconId() {
        return iconId;
    }
    /**文件的路径*/
    public String path;
    /**文件图片资源的id，drawable或mipmap文件中已经存放doc、type_xml、xls等文件的图片*/
    public int iconId;
    public  String realpath;
    public FileBean(String path, int iconId,String realpath) {
        this.path = path;
        this.realpath=realpath;
        this.iconId = iconId;
    }
}