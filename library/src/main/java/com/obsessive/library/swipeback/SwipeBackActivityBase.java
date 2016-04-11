package com.obsessive.library.swipeback;

/**
 * Project Fawns
 * Package com.obsessive.library.swipeback
 * Author Mengjiaxin
 * Date 2016/4/11 17:06
 * Desc 请用一句话来描述作用
 */
public interface SwipeBackActivityBase {

    SwipeBackLayout getSwipeBackLayout();

    void setSwipeBackEnable(boolean enable);

    void scrollToFinishActivity();
}
