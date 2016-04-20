package com.obsessive.library.adapter;

/**
 * Project Fawns
 * Package com.obsessive.library.adapter
 * Author Mengjiaxin
 * Date 2016/4/14 16:24
 * Desc 请用一句话来描述作用
 */
public interface ViewHolderCreator<ItemDataType> {
    public ViewHolderBase<ItemDataType> createViewHolder(int position);
}