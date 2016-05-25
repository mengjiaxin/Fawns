package com.fawns.app.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fawns.app.R;
import com.fawns.app.ui.base.BaseActivity;
import com.fawns.app.ui.base.BaseSwipeBackActivity;
import com.obsessive.library.eventbus.EventCenter;
import com.obsessive.library.netstatus.NetUtils;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;

import butterknife.InjectView;

/**
 * Project Fawns
 * Package com.fawns.app.ui.activity
 * Author Mengjiaxin
 * Date 2016/4/20 21:35
 * Desc 请用一句话来描述作用
 */
public class MessageActivity extends BaseActivity {

    @InjectView(R.id.message_banner)
    ImageView mBannerImageView;

    String url = "http://square.github.io/picasso/static/sample.png";

    @Override
    protected void initViewsAndEvents() {

        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(mBannerImageView);
    }

    @Override
    protected boolean isBackHomeButtonEnabled() {
        return true;
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected String setToolbarTitle() {
        return getString(R.string.menu_message);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_message;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }


}
