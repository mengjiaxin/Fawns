package com.obsessive.library.base;

import android.os.Bundle;
import android.view.View;

import com.obsessive.library.swipeback.SwipeBackActivityBase;
import com.obsessive.library.swipeback.SwipeBackActivityHelper;
import com.obsessive.library.swipeback.SwipeBackLayout;
import com.obsessive.library.swipeback.Utils;

/**
 * Project Fawns
 * Package com.obsessive.library.base
 * Author Mengjiaxin
 * Date 2016/4/11 17:06
 * Desc 请用一句话来描述作用
 */
public abstract class BaseSwipeBackCompatActivity extends BaseAppCompatActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
