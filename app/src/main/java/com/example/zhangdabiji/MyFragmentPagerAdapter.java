package com.example.zhangdabiji;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFragmentPagerAdapter<onInterceptTouchEvent, onTouchEvent> extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 2;
    private  List<Fragment>  fragmentList=new ArrayList<>();

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

    }
    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
   public Boolean onInterceptTouchEvent(){
        if (MainActivity.viewpagerisskip)
            return false;
        else return  true;
   }
  public Boolean  onTouchEvent(){
      if (!MainActivity.viewpagerisskip)
          return false;
      else return  true;
    }
    public Object instantiateItem(ViewGroup container, int position) {
        this.fragmentList.add(new mainView());
        this.fragmentList.add(new Yonghuyemian());

        return super.instantiateItem(container,position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = fragmentList.get(0);
                break;
            case MainActivity.PAGE_TWO:
                fragment = fragmentList.get(1);
                break;
        }
        return fragment;
    }

}