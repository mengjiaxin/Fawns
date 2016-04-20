package com.obsessive.library.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Project Fawns
 * Package com.obsessive.library.widgets
 * Author Mengjiaxin
 * Date 2016/4/12 11:07
 * Desc 请用一句话来描述作用
 */
public class XViewPager extends ViewPager {

    private boolean isEnableScroll = true;

    public void setEnableScroll(boolean isEnableScroll) {
        this.isEnableScroll = isEnableScroll;
    }

    public XViewPager(Context context) {
        super(context);
    }

    public XViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isEnableScroll) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isEnableScroll) {
            return false;
        }
        return super.onTouchEvent(ev);
    }
}

