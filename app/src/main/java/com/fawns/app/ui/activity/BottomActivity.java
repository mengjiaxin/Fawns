package com.fawns.app.ui.activity;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.fawns.app.R;
import com.fawns.app.ui.fragment.DemoFragment;

import java.util.ArrayList;


/**
 * Project Fawns
 * Package com.fawns.app.ui.activity
 * Author Mengjiaxin
 * Date 2016/4/19 17:07
 * Desc 请用一句话来描述作用
 */
public class BottomActivity extends AppCompatActivity {

    private DemoFragment currentFragment;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private FragmentManager fragmentManager = getFragmentManager();
    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        initUI();
    }

    /**
     * Init UI
     */
    private void initUI() {

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.bottom_home), R.mipmap.ic_maps_place);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.bottom_query), R.mipmap.ic_maps_local_bar);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.bottom_transaction), R.mipmap.ic_maps_local_restaurant);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);

        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                if (!wasSelected) {
                    currentFragment = DemoFragment.newInstance(position);
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace(R.id.fragment_container, currentFragment)
                            .commit();
                } else if (position > 0) {
                    currentFragment.refresh();
                }
            }
        });

        currentFragment = DemoFragment.newInstance(0);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, currentFragment)
                .commit();
    }

    /**
     * Update the bottom navigation colored param
     */
    public void updateBottomNavigationColor(boolean isColored) {
        bottomNavigation.setColored(isColored);
    }

    /**
     * Return if the bottom navigation is colored
     */
    public boolean isBottomNavigationColored() {
        return bottomNavigation.isColored();
    }

    /**
     * Add or remove items of the bottom navigation
     */
    public void updateBottomNavigationItems(boolean addItems) {

        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Label Four", R.mipmap.ic_maps_local_bar, Color.parseColor("#6C4A42"));
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("Label Five", R.mipmap.ic_maps_place, Color.parseColor("#F63D2B"));

        if (addItems) {
            bottomNavigation.addItem(item4);
            bottomNavigation.addItem(item5);
        } else {
            bottomNavigation.removeAllItems();
            bottomNavigation.addItems(bottomNavigationItems);
        }
    }

    /**
     * Return the number of items in the bottom navigation
     */
    public int getBottomNavigationNbItems() {
        return bottomNavigation.getItemsCount();
    }
}
