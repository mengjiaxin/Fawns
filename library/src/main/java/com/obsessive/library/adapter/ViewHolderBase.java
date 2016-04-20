package com.obsessive.library.adapter;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Project Fawns
 * Package com.obsessive.library.adapter
 * Author Mengjiaxin
 * Date 2016/4/14 16:25
 * Desc 请用一句话来描述作用
 */
public abstract class ViewHolderBase<ItemDataType> {

    protected int mLastPosition;
    protected int mPosition = -1;
    protected View mCurrentView;

    public abstract View createView(LayoutInflater layoutInflater);

    public abstract void showData(int position, ItemDataType itemData);

    public void setItemData(int position, View view) {
        mLastPosition = mPosition;
        mPosition = position;
        mCurrentView = view;
    }

    public boolean stillHoldLastItemData() {
        return mLastPosition == mPosition;
    }
}