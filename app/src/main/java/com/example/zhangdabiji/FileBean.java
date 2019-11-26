package com.example.zhangdabiji;
public class FileBean { //文件格式
    public String getPath() {
        return path;
    }
    public String getrealPath() {
        return  realpath;
    }
    public String getFiledate() {
        return  filedate;
    }
    public int getIconId() {
        return iconId;
    }
    /**文件的路径*/
    private String path;
    /**文件图片资源的id，drawable或mipmap文件中已经存放doc、type_xml、xls等文件的图片*/
    private int iconId;
    private String realpath;

    public Boolean getCheched() {
        return isCheched;
    }

    public void setCheched(Boolean cheched) {
        isCheched = cheched;
    }

    private  Boolean isCheched;
    private String filedate;
    public FileBean(String path, int iconId,String realpath,String filedate,Boolean isCheched) {
        this.path = path;
        this.isCheched=isCheched;
        this.realpath=realpath;
        this.iconId = iconId;
        this.filedate = filedate;
    }
}