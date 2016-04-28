package com.fawns.app.ui.activity;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.aurelhubert.ahbottomnavigation.AHHelper;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.fawns.app.R;
import com.fawns.app.bean.MessageEntity;
import com.fawns.app.ui.adpater.MessageAdapter;
import com.fawns.app.ui.base.BaseSwipeBackActivity;
import com.melnykov.fab.FloatingActionButton;
import com.obsessive.library.eventbus.EventCenter;
import com.obsessive.library.netstatus.NetUtils;
import com.obsessive.library.utils.TLog;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Project Fawns
 * Package com.fawns.app.ui.activity
 * Author Mengjiaxin
 * Date 2016/4/21 15:19
 * Desc 请用一句话来描述作用
 */
public class ChatActivity extends BaseSwipeBackActivity implements EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {

    private static final String TAG = ChatActivity.class.getSimpleName();

    @InjectView(R.id.chat_message_lv)
    ListView mMessageListView;
    @InjectView(R.id.chat_send_fab)
    FloatingActionButton mSendFloatingActionButton;
    @InjectView(R.id.chat_message_et)
    EmojiconEditText mMessageEditText;
    @InjectView(R.id.chat_more_rl)
    RelativeLayout mMoreRelativeLayout;
    @InjectView(R.id.chat_face_rl)
    RelativeLayout mFaceRelativeLayout;
    @InjectView(R.id.chat_more_ib)
    ImageButton mMoreImageButton;
    @InjectView(R.id.chat_face_ib)
    ImageButton mFaceImageButton;
    @InjectView(R.id.chat_message_text_ll)
    LinearLayout mMessageTextLinearLayout;
    @InjectView(R.id.chat_message_voice_ll)
    LinearLayout mMessageVoiceLinearLayout;


    private MessageAdapter mMessageAdapter;
    private List<MessageEntity> mData = new ArrayList<>();

    // FloatingActionButton的状态值
    private final static int KEYBORAD = 0x001;
    private final static int VOICE = 0x002;
    private final static int SEND = 0x003;

    // 切换动画时间
    private final static int FAB_ANIM_TIME = 450;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_chat_message:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4000777366"));
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViewsAndEvents() {
        initToolbarUI();
        initListViewData();
        setEmojiconFragment(false);

        // 初始化为话筒图标
        mSendFloatingActionButton.setTag(VOICE);

        mMessageEditText.addTextChangedListener(messageTextWatcher);
        mMessageEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mMoreRelativeLayout.getVisibility() == View.VISIBLE)
                    mMoreRelativeLayout.setVisibility(View.GONE);
                if (mFaceRelativeLayout.getVisibility() == View.VISIBLE)
                    mFaceRelativeLayout.setVisibility(View.GONE);

                return false;
            }
        });

        mMessageListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mMoreRelativeLayout.setVisibility(View.GONE);
                mFaceRelativeLayout.setVisibility(View.GONE);
                hideKeyboard(mMessageEditText);
                return false;
            }
        });
    }

    private void initListViewData() {

        MessageEntity entity = new MessageEntity();
        entity.setContent("2003年参演中国首部犯罪题材的电视剧《拯救少年犯》");
        entity.setTime("18:30");
        entity.setIsLeft(false);
        mData.add(entity);


        mMessageAdapter = new MessageAdapter(this, mData);
        mMessageListView.setAdapter(mMessageAdapter);
        mMessageAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.chat_more_ib})
    public void onClickMoreImageButton() {
        if (mFaceRelativeLayout.getVisibility() == View.VISIBLE)
            mFaceRelativeLayout.setVisibility(View.GONE);
        if (mMoreRelativeLayout.getVisibility() == View.GONE) {
            hideKeyboard(mMessageEditText);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mMoreRelativeLayout.setVisibility(View.VISIBLE);
                }
            }, 200);
        } else {
            mMoreRelativeLayout.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.chat_face_ib})
    public void onClickFaceImageButton() {
        if (mMoreRelativeLayout.getVisibility() == View.VISIBLE)
            mMoreRelativeLayout.setVisibility(View.GONE);
        if (mFaceRelativeLayout.getVisibility() == View.GONE) {
            hideKeyboard(mMessageEditText);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFaceRelativeLayout.setVisibility(View.VISIBLE);
                }
            }, 200);
        } else {
            mFaceRelativeLayout.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.chat_send_fab})
    public void onClickSendFloatingActionButton() {


        switch ((int) mSendFloatingActionButton.getTag()) {
            case VOICE:
                YoYo.with(Techniques.SlideOutLeft).duration(FAB_ANIM_TIME).playOn(mMessageTextLinearLayout);
                mMessageVoiceLinearLayout.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInRight).duration(FAB_ANIM_TIME).playOn(mMessageVoiceLinearLayout);
//                mMessageTextLinearLayout.setVisibility(View.GONE);
//                mMessageVoiceLinearLayout.setVisibility(View.VISIBLE);
                changeFABIcon(KEYBORAD);
                hideKeyboard(mMessageEditText);
                break;
            case SEND:
                MessageEntity entity = new MessageEntity();
                entity.setContent(mMessageEditText.getText().toString());
                entity.setTime("刚刚");
                entity.setIsLeft(false);
                mData.add(entity);

                mMessageAdapter.notifyDataSetChanged();
                mMessageListView.setSelection(0);

                mMessageEditText.setText(null);
                changeFABIcon(VOICE);
                break;
            case KEYBORAD:
                YoYo.with(Techniques.SlideOutRight).duration(FAB_ANIM_TIME).playOn(mMessageVoiceLinearLayout);
                YoYo.with(Techniques.SlideInLeft).duration(FAB_ANIM_TIME).playOn(mMessageTextLinearLayout);

//                mMessageVoiceLinearLayout.setVisibility(View.GONE);
//                mMessageTextLinearLayout.setVisibility(View.VISIBLE);
                changeFABIcon(VOICE);
                showKeyboard(mMessageEditText);
                break;

        }
    }

    private TextWatcher messageTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s)) {
                changeFABIcon(VOICE);
            } else {
                changeFABIcon(SEND);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /**
     * 改变Floatingactionbutton的图标
     */
    private void changeFABIcon(int type) {
        Drawable sendDrawable = AHHelper.getTintDrawable(this, R.mipmap.ic_send_white_24dp, ContextCompat.getColor(this, R.color.white));
        Drawable micDrawable = AHHelper.getTintDrawable(this, R.mipmap.ic_mic_white_24dp, ContextCompat.getColor(this, R.color.white));
        Drawable keyboardDrawable = AHHelper.getTintDrawable(this, R.mipmap.ic_keyboard_grey600_24dp, ContextCompat.getColor(this, R.color.white));
        switch (type) {
            case VOICE:
                mSendFloatingActionButton.setImageDrawable(micDrawable);
                mSendFloatingActionButton.setTag(VOICE);
                break;
            case SEND:
                mSendFloatingActionButton.setImageDrawable(sendDrawable);
                mSendFloatingActionButton.setTag(SEND);
                break;
            case KEYBORAD:
                mSendFloatingActionButton.setImageDrawable(keyboardDrawable);
                mSendFloatingActionButton.setTag(KEYBORAD);
                break;
        }
    }

    private void initToolbarUI() {
//        Drawable iconDrawable = AHHelper.getTintDrawable(this, R.mipmap.ic_face_unlock_white_24dp, ContextCompat.getColor(this, R.color.white));
//        mToolbar.setLogo(iconDrawable);
        // 添加副标题
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
        TLog.e(TAG, "type:" + type.name());
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

    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    @Override
    public void onEmojiconBackspaceClicked(View view) {
        EmojiconsFragment.backspace(mMessageEditText);
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(mMessageEditText, emojicon);
    }
}
