package com.fawns.app.ui.activity;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.fawns.app.R;
import com.fawns.app.bean.NavigationEntity;
import com.fawns.app.ui.adpater.HomeFragmentAdapter;
import com.fawns.app.ui.base.BaseActivity;
import com.fawns.app.ui.base.BaseFragment;
import com.fawns.app.ui.fragment.DemoFragment;
import com.fawns.app.ui.fragment.IndexFragment;
import com.fawns.app.ui.fragment.MyFragment;
import com.fawns.app.ui.fragment.SearchFragment;
import com.fawns.app.ui.fragment.TransactionFragment;
import com.fawns.app.view.HomeView;
import com.obsessive.library.base.BaseLazyFragment;
import com.obsessive.library.eventbus.EventCenter;
import com.obsessive.library.netstatus.NetUtils;
import com.obsessive.library.widgets.BadgeView;
import com.obsessive.library.widgets.XViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.jpush.android.api.JPushInterface;

/**
 * Project Fawns
 * Package com.fawns.app.ui.activity
 * Author Mengjiaxin
 * Date 2016/4/12 11:05
 * Desc 请用一句话来描述作用
 */
public class HomeActivity extends BaseActivity implements HomeView {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private static long DOUBLE_CLICK_TIME = 0L;

    @InjectView(R.id.bottom_navigation)
    AHBottomNavigation mBottomNavigation;
    @InjectView(R.id.home_container)
    XViewPager mViewPager;

    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private List<BaseFragment> homeFragments = new ArrayList<BaseFragment>();
    private HomeFragmentAdapter fragmentAdapter;

    public static boolean isForeground;

    @Override
    protected void initViewsAndEvents() {
        initBottomUI();
        initFragments();
    }


    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;
        JPushInterface.onPause(this);
    }

    @Override
    protected boolean isBackHomeButtonEnabled() {
        return false;
    }


    /**
     * 先把关闭抽屉暂停一下,再打开Activity,不然看着有点卡顿
     *
     * @param c
     */
    private void readyGoCloseDrawers(Class c) {
        Message msg = new Message();
        msg.obj = c;
        readyGoHandler.sendMessageDelayed(msg, 300);
    }

    private Handler readyGoHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            readyGo((Class) msg.obj);
            return false;
        }
    });

    @Override
    public void initializeViews(List<BaseLazyFragment> fragments, List<NavigationEntity> navigationList) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        showMessageBadgeView(2);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_message:
                hideMessageBadgeView();
                readyGoCloseDrawers(MessageActivity.class);
                break;
            case R.id.action_chat:
                readyGoCloseDrawers(ChatActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return true;
    }

    @Override
    protected String setToolbarTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return ButterKnife.findById(this, R.id.home_container);
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
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
                showToast(getString(R.string.double_click_exit));
                DOUBLE_CLICK_TIME = System.currentTimeMillis();
            } else {
                getBaseApplication().exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void initBottomUI() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.bottom_home), R.mipmap.ic_home_white_24dp);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.bottom_query), R.mipmap.ic_find_in_page_white_24dp);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.bottom_transaction), R.mipmap.ic_assignment_black_24dp);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getString(R.string.bottom_my), R.mipmap.ic_timer_auto_white_24dp);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);

        mBottomNavigation.addItems(bottomNavigationItems);
        mBottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        mBottomNavigation.setInactiveColor(getResources().getColor(R.color.colorInactive));
        mBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                if (!wasSelected) {
                    mViewPager.setCurrentItem(position);
                } else if (position > 0) {

                }
            }
        });

    }

    private void initFragments() {
        homeFragments.add(new IndexFragment());
        homeFragments.add(new SearchFragment());
        homeFragments.add(new TransactionFragment());
        homeFragments.add(new MyFragment());

        fragmentAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), homeFragments);
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.setCurrentItem(0);
    }

    public void updateBottomNavigationColor(boolean isColored) {
        mBottomNavigation.setColored(isColored);
    }

    public boolean isBottomNavigationColored() {
        return mBottomNavigation.isColored();
    }

    public void updateBottomNavigationItems(boolean addItems) {

        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Label Four", R.mipmap.ic_maps_local_bar, Color.parseColor("#6C4A42"));
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("Label Five", R.mipmap.ic_maps_place, Color.parseColor("#F63D2B"));

        if (addItems) {
            mBottomNavigation.addItem(item4);
            mBottomNavigation.addItem(item5);
        } else {
            mBottomNavigation.removeAllItems();
            mBottomNavigation.addItems(bottomNavigationItems);
        }
    }

    public int getBottomNavigationNbItems() {
        return mBottomNavigation.getItemsCount();
    }


    private BadgeView mMessageBadgeView;

    private static final int BADGE_VIEW_SHOW = 0x0001;
    private static final int BADGE_VIEW_HIDE = 0x0002;

    public Handler mBadgeviewHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            View mMenuMessageView = ButterKnife.findById(HomeActivity.this, R.id.action_message);
            switch (msg.what) {
                case BADGE_VIEW_SHOW:
                    if (mMessageBadgeView == null)
                        mMessageBadgeView = new BadgeView(HomeActivity.this);
                    mMessageBadgeView.setTargetView(mMenuMessageView);
                    mMessageBadgeView.setBadgeCount(msg.arg1);
                    mMessageBadgeView.setBadgeMargin(0, 4, 4, 0);
                    mMessageBadgeView.setVisibility(View.VISIBLE);
                    break;
                case BADGE_VIEW_HIDE:
                    if (mMessageBadgeView != null) {
                        mMessageBadgeView.setBadgeCount(0);
                        mMessageBadgeView.setVisibility(View.GONE);
                    }
                    break;
            }

            return false;
        }
    });

    /**
     * 显示badgeView
     *
     * @param showCount
     */
    public void showMessageBadgeView(int showCount) {
        Message msg = new Message();
        msg.what = BADGE_VIEW_SHOW;
        msg.arg1 = showCount;
        mBadgeviewHandler.sendMessage(msg);
    }

    /**
     * 隐藏badgeView
     */
    public void hideMessageBadgeView() {
        Message msg = new Message();
        msg.what = BADGE_VIEW_HIDE;
        mBadgeviewHandler.sendMessage(msg);
    }


}
