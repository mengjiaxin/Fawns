package com.obsessive.library.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Project Fawns
 * Package com.obsessive.library.adapter
 * Author Mengjiaxin
 * Date 2016/4/14 16:24
 * Desc 请用一句话来描述作用
 */
public abstract class ListViewDataAdapterBase<ItemDataType> extends BaseAdapter {

    private static final String LOG_TAG = "cube-list";

    protected ViewHolderCreator<ItemDataType> mViewHolderCreator;
    protected ViewHolderCreator<ItemDataType> mLazyCreator;
    protected boolean mForceCreateView = false;

    public ListViewDataAdapterBase() {

    }

    public ListViewDataAdapterBase(final Object enclosingInstance, final Class<?> cls) {
        setViewHolderClass(enclosingInstance, cls);
    }

    public ListViewDataAdapterBase(ViewHolderCreator<ItemDataType> viewHolderCreator) {
        mViewHolderCreator = viewHolderCreator;
    }

    public void setViewHolderCreator(ViewHolderCreator<ItemDataType> viewHolderCreator) {
        mViewHolderCreator = viewHolderCreator;
    }

    public void setViewHolderClass(final Object enclosingInstance, final Class<?> cls, final Object... args) {
        mLazyCreator = LazyViewHolderCreator.create(enclosingInstance, cls, args);
    }

    private ViewHolderBase<ItemDataType> createViewHolder(int position) {
        if (mViewHolderCreator == null && mLazyCreator == null) {
            throw new RuntimeException("view holder creator is null");
        }
        if (mViewHolderCreator != null) {
            return mViewHolderCreator.createViewHolder(position);
        }
        if (mLazyCreator != null) {
            return mLazyCreator.createViewHolder(position);
        }
        return null;
    }

    public void forceCreateView(boolean yes) {
        mForceCreateView = yes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemDataType itemData = getItem(position);
        ViewHolderBase<ItemDataType> holderBase = null;
        if (mForceCreateView || convertView == null || (!(convertView.getTag() instanceof ViewHolderBase<?>))) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            holderBase = createViewHolder(position);
            if (holderBase != null) {
                convertView = holderBase.createView(inflater);
                if (convertView != null) {
                    if (!mForceCreateView) {
                        convertView.setTag(holderBase);
                    }
                }
            }
        } else {
            holderBase = (ViewHolderBase<ItemDataType>) convertView.getTag();
        }
        if (holderBase != null) {
            holderBase.setItemData(position, convertView);
            holderBase.showData(position, itemData);
        }
        return convertView;
    }

    @Override
    public abstract ItemDataType getItem(int position);
}
