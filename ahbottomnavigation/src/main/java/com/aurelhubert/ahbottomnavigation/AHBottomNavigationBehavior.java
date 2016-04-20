package com.aurelhubert.ahbottomnavigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

/**
 * Project Fawns
 * Package com.aurelhubert.ahbottomnavigation
 * Author Mengjiaxin
 * Date 2016/4/19 16:45
 * Desc 请用一句话来描述作用
 */
public class AHBottomNavigationBehavior<V extends View> extends VerticalScrollingBehavior<V> {

    private static final Interpolator INTERPOLATOR = new LinearOutSlowInInterpolator();
    private int mTabLayoutId;
    private boolean hidden = false;
    private ViewPropertyAnimatorCompat mTranslationAnimator;
    private TabLayout mTabLayout;
    private View mTabsHolder;

    public AHBottomNavigationBehavior() {
        super();
    }

    public AHBottomNavigationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AHBottomNavigationBehavior_Params);
        mTabLayoutId = a.getResourceId(R.styleable.AHBottomNavigationBehavior_Params_tabLayoutId, View.NO_ID);
        a.recycle();
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        boolean layoutChild = super.onLayoutChild(parent, child, layoutDirection);
        if (mTabLayout == null && mTabLayoutId != View.NO_ID) {
            mTabLayout = findTabLayout(child);
            getTabsHolder();
        }
        return layoutChild;
    }

    private TabLayout findTabLayout(View child) {
        if (mTabLayoutId == 0) return null;
        return (TabLayout) child.findViewById(mTabLayoutId);
    }

    @Override
    public void onNestedVerticalOverScroll(CoordinatorLayout coordinatorLayout, V child, @ScrollDirection int direction, int currentOverScroll, int totalOverScroll) {
    }

    @Override
    public void onDirectionNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed, @ScrollDirection int scrollDirection) {
        handleDirection(child, scrollDirection);
    }

    private void handleDirection(V child, int scrollDirection) {
        if (scrollDirection == ScrollDirection.SCROLL_DIRECTION_DOWN && hidden) {
            hidden = false;
            animateOffset(child, 0);
        } else if (scrollDirection == ScrollDirection.SCROLL_DIRECTION_UP && !hidden) {
            hidden = true;
            animateOffset(child, child.getHeight());
        }
    }

    @Override
    protected boolean onNestedDirectionFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY, @ScrollDirection int scrollDirection) {
        handleDirection(child, scrollDirection);
        return true;
    }

    private void animateOffset(final V child, final int offset) {
        ensureOrCancelAnimator(child);
        mTranslationAnimator.translationY(offset).start();
        animateTabsHolder(offset);
    }

    private void animateTabsHolder(int offset) {
        if (mTabsHolder != null) {
            offset = offset > 0 ? 0 : 1;
            ViewCompat.animate(mTabsHolder).alpha(offset).setDuration(300).start();
        }
    }


    private void ensureOrCancelAnimator(V child) {
        if (mTranslationAnimator == null) {
            mTranslationAnimator = ViewCompat.animate(child);
            mTranslationAnimator.setDuration(300);
            mTranslationAnimator.setInterpolator(INTERPOLATOR);
        } else {
            mTranslationAnimator.cancel();
        }
    }

    private void getTabsHolder() {
        if (mTabLayout != null) {
            mTabsHolder = mTabLayout.getChildAt(0);
        }
    }

    public static <V extends View> AHBottomNavigationBehavior<V> from(V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params)
                .getBehavior();
        if (!(behavior instanceof AHBottomNavigationBehavior)) {
            throw new IllegalArgumentException(
                    "The view is not associated with AHBottomNavigationBehavior");
        }
        return (AHBottomNavigationBehavior<V>) behavior;
    }

    public void setTabLayoutId(int tabId) {
        this.mTabLayoutId = tabId;
    }
}