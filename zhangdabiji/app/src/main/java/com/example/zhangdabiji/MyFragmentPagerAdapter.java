package com.example.zhangdabiji;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 3;
    private  List<Fragment>  fragmentList=new ArrayList<>();

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

    }
    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        this.fragmentList.add(new WendangYemian());
        this.fragmentList.add(new  paiming());
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
            case MainActivity.PAGE_THREE:
                fragment = fragmentList.get(2);
                break;
        }
        return fragment;
    }

}