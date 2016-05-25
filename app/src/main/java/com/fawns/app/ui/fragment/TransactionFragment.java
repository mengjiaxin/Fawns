package com.fawns.app.ui.fragment;

import android.view.View;

import com.fawns.app.R;
import com.fawns.app.ui.base.BaseFragment;
import com.obsessive.library.eventbus.EventCenter;

/**
 * Project Fawns
 * Package com.fawns.app.ui.fragment
 * Author Mengjiaxin
 * Date 2016/5/24 17:54
 * Desc 请用一句话来描述作用
 */
public class TransactionFragment extends BaseFragment {

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected String setFragmentName() {
        return null;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_transaction;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }
}