package com.fawns.app.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.aurelhubert.ahbottomnavigation.AHHelper;
import com.fawns.app.R;
import com.fawns.app.ui.base.BaseSwipeBackActivity;
import com.melnykov.fab.FloatingActionButton;
import com.obsessive.library.eventbus.EventCenter;
import com.obsessive.library.netstatus.NetUtils;

import butterknife.InjectView;

/**
 * Project Fawns
 * Package com.fawns.app.ui.activity
 * Author Mengjiaxin
 * Date 2016/4/21 15:19
 * Desc 请用一句话来描述作用
 */
public class ChatActivity extends BaseSwipeBackActivity {

    @InjectView(R.id.chat_send_fab)
    FloatingActionButton mSendFloatingActionButton;
    @InjectView(R.id.chat_message_et)
    EditText mMessageEditText;
    @InjectView(R.id.chat_more_rl)
    RelativeLayout mMoreRelativeLayout;
    @InjectView(R.id.chat_more_ib)
    ImageButton mMoreImageButton;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_chat;
    }

    @Override
    protected String setToolbarTitle() {
        return getString(R.string.chat_toolbar_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    protected void initViewsAndEvents() {
        initToolbarUI();

        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s))
                    changeFABIcon(false);
                else
                    changeFABIcon(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mMoreImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMoreRelativeLayout.getVisibility() == View.GONE) {
                    mMoreRelativeLayout.setVisibility(View.VISIBLE);
//                    mMoreImageButton.setImageResource(R.mipmap.ic_clear_grey600_24dp);
                } else {
                    mMoreRelativeLayout.setVisibility(View.GONE);
//                    mMoreImageButton.setImageResource(R.mipmap.ic_add_grey600_24dp);
                }
            }
        });
    }

    private void changeFABIcon(boolean isSend) {
        Drawable sendDrawable = AHHelper.getTintDrawable(this, R.mipmap.ic_send_white_24dp, ContextCompat.getColor(this, R.color.white));
        Drawable micDrawable = AHHelper.getTintDrawable(this, R.mipmap.ic_mic_white_24dp, ContextCompat.getColor(this, R.color.white));
        if (isSend)
            mSendFloatingActionButton.setImageDrawable(sendDrawable);
        else
            mSendFloatingActionButton.setImageDrawable(micDrawable);
    }

    private void initToolbarUI() {
//        Drawable iconDrawable = AHHelper.getTintDrawable(this, R.mipmap.ic_face_unlock_white_24dp, ContextCompat.getColor(this, R.color.white));
//        mToolbar.setLogo(iconDrawable);
        mToolbar.setTitleTextAppearance(this, R.style.MenuTitleStyle);
        mToolbar.setSubtitleTextAppearance(this, R.style.MenuSubTitleStyle);
        mToolbar.setSubtitle(getString(R.string.chat_toolbar_sub_title));
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

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
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }
}
