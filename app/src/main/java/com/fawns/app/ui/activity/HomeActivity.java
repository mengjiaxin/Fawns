package com.fawns.app.ui.activity;

import android.app.FragmentManager;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHHelper;
import com.fawns.app.R;
import com.fawns.app.bean.NavigationEntity;
import com.fawns.app.receiver.PushMessageReceiver;
import com.fawns.app.ui.base.BaseActivity;
import com.fawns.app.ui.fragment.DemoFragment;
import com.fawns.app.view.HomeView;
import com.obsessive.library.adapter.ListViewDataAdapter;
import com.obsessive.library.adapter.ViewHolderBase;
import com.obsessive.library.adapter.ViewHolderCreator;
import com.obsessive.library.base.BaseLazyFragment;
import com.obsessive.library.eventbus.EventCenter;
import com.obsessive.library.netstatus.NetUtils;
import com.obsessive.library.utils.TLog;
import com.obsessive.library.widgets.BadgeView;

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

    @InjectView(R.id.home_drawer)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.home_navigation_list)
    ListView mNavListView;
    @InjectView(R.id.bottom_navigation)
    AHBottomNavigation mBottomNavigation;

    private DemoFragment currentFragment;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private FragmentManager fragmentManager = getFragmentManager();

    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ListViewDataAdapter<NavigationEntity> mNavListAdapter;

    public static boolean isForeground;

    @Override
    protected void initViewsAndEvents() {
        initDrawer();
        initBottomUI();
        initNavList();


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

    private void initNavList() {
        mNavListAdapter = new ListViewDataAdapter<>(new ViewHolderCreator<NavigationEntity>() {
            @Override
            public ViewHolderBase<NavigationEntity> createViewHolder(int position) {
                return new ViewHolderBase<NavigationEntity>() {

                    private ImageView itemIcon;
                    private TextView itemName;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.list_item_navigation, null);
                        itemIcon = ButterKnife.findById(convertView, R.id.list_item_navigation_icon);
                        itemName = ButterKnife.findById(convertView, R.id.list_item_navigation_name);
                        return convertView;
                    }

                    @Override
                    public void showData(int position, NavigationEntity navigationEntity) {
                        Drawable iconDrawable = AHHelper.getTintDrawable(HomeActivity.this, navigationEntity.getIconResId(), ContextCompat.getColor(HomeActivity.this, R.color.colorInactive));
//                        itemIcon.setImageResource(navigationEntity.getIconResId());
                        itemIcon.setImageDrawable(iconDrawable);
                        itemName.setText(navigationEntity.getName());
                    }
                };
            }
        });

        mNavListView.setAdapter(mNavListAdapter);
        mNavListAdapter.getDataList().addAll(getNavigationListData());
        mNavListAdapter.notifyDataSetChanged();

        mNavListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TLog.d(TAG, "position:" + position);
                switch (position) {
                    case 0:
                        readyGoCloseDrawers(MessageActivity.class);
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                }
            }
        });
    }

    /**
     * 先把关闭抽屉暂停一下,再打开Activity,不然看着有点卡顿
     *
     * @param c
     */
    private void readyGoCloseDrawers(Class c) {
        mDrawerLayout.closeDrawers();
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

    public List<NavigationEntity> getNavigationListData() {
        List<NavigationEntity> navigationEntities = new ArrayList<>();
        String[] navigationArrays = getResources().getStringArray(R.array.navigation_list);
        navigationEntities.add(new NavigationEntity("", navigationArrays[0], R.mipmap.ic_maps_local_attraction));
        navigationEntities.add(new NavigationEntity("", navigationArrays[1], R.mipmap.ic_maps_local_bar));
        navigationEntities.add(new NavigationEntity("", navigationArrays[2], R.mipmap.ic_settings_black_24dp));
        return navigationEntities;
    }

    @Override
    public void initializeViews(List<BaseLazyFragment> fragments, List<NavigationEntity> navigationList) {

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mActionBarDrawerToggle != null) {
            mActionBarDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mActionBarDrawerToggle != null) {
            mActionBarDrawerToggle.onConfigurationChanged(newConfig);
        }
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
        return "";
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
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
                    showToast(getString(R.string.double_click_exit));
                    DOUBLE_CLICK_TIME = System.currentTimeMillis();
                } else {
                    getBaseApplication().exitApp();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void initDrawer() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }


    private void initBottomUI() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.bottom_home), R.mipmap.ic_home_white_24dp);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.bottom_query), R.mipmap.ic_find_in_page_white_24dp);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.bottom_transaction), R.mipmap.ic_assignment_black_24dp);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);

        mBottomNavigation.addItems(bottomNavigationItems);
        mBottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        mBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                mDrawerLayout.closeDrawers();
                if (!wasSelected) {
                    currentFragment = DemoFragment.newInstance(position);
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace(R.id.home_container, currentFragment)
                            .commit();
                } else if (position > 0) {
                    currentFragment.refresh();
                }
            }
        });

        currentFragment = DemoFragment.newInstance(0);
        fragmentManager.beginTransaction()
                .replace(R.id.home_container, currentFragment)
                .commit();
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
