package com.fawns.app.view.base;

/**
 * Project Fawns
 * Package com.fawns.app.view.base
 * Author Mengjiaxin
 * Date 2016/4/11 16:31
 * Desc 通用的View接口
 */
public interface BaseView {

    /**
     * 显示加载信息
     *
     * @param msg
     */
    void showLoading(String msg);

    /**
     * 隐藏加载信息
     */
    void hideLoading();

    /**
     * 显示错误信息
     */
    void showError(String msg);

    /**
     * 显示异常信息
     */
    void showException(String msg);

    /**
     * 显示网络错误
     */
    void showNetError();
}
