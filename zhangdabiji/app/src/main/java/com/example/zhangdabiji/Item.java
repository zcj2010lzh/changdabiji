package com.example.zhangdabiji;


public class Item {
  //  protected int id;

  /*  public int getiId() {
        return id;
    }

    public void setiId(int id) {
        this.id = id;
    }*/

  Item(String name){
     this. name=name;
  }
    public String getiName() {
        return name;
    }

    public void setiName(String name) {
        this.name = name;
    }

    protected  String name;
}
