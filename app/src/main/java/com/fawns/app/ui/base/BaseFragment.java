package com.fawns.app.ui.base;

import com.fawns.app.view.base.BaseView;
import com.obsessive.library.base.BaseLazyFragment;
import com.umeng.analytics.MobclickAgent;

/**
 * Project Fawns
 * Package com.fawns.app.ui.base
 * Author Mengjiaxin
 * Date 2016/4/12 11:01
 * Desc 请用一句话来描述作用
 */
public abstract class BaseFragment extends BaseLazyFragment implements BaseView {

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(setFragmentName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(setFragmentName());
    }

    protected abstract String setFragmentName();

    @Override
    public void showError(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showException(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, null);
    }

    @Override
    public void showLoading(String msg) {
        toggleShowLoading(true, null);
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false, null);
    }
}