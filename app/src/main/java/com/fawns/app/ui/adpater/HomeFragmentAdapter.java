package com.fawns.app.ui.adpater;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fawns.app.ui.base.BaseFragment;

import java.util.List;

/**
 * Project Fawns
 * Package com.fawns.app.ui.adpater
 * Author Mengjiaxin
 * Date 2016/5/24 17:50
 * Desc 请用一句话来描述作用
 */
public class HomeFragmentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;


    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public HomeFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}