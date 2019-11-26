package com.example.zhangdabiji;


public class Item {
  //  protected int id;

  /*  public int getiId() {
        return id;
    }

    public void setiId(int id) {
        this.id = id;
    }*/

  Item(String name,String date){
     this. name=name;
     this.date=date;
  }
    Item(String name,String date,String url,String file_type){
        this. name=name;
        this.url=url;
        this.file_type=file_type;
        this.date=date;
    }
    Item(String name){
        this. name=name;
    }
    public String getiName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private  String url;
    private String date;
    public void setiName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    private  String file_type;
    protected  String name;
}
