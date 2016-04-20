package com.aurelhubert.ahbottomnavigation;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Project Fawns
 * Package com.aurelhubert.ahbottomnavigation
 * Author Mengjiaxin
 * Date 2016/4/19 16:46
 * Desc 请用一句话来描述作用
 */
public class AHHelper {

    /**
     * Return a tint drawable
     *
     * @param context
     * @param drawableResource
     * @param color
     * @return
     */
    public static Drawable getTintDrawable(Context context, int drawableResource, int color) {
        Drawable normalDrawable = ContextCompat.getDrawable(context, drawableResource);
        Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
        DrawableCompat.setTint(wrapDrawable, color);
        return wrapDrawable;
    }

    public static Drawable getTintDrawable(Context context, Drawable normalDrawable, int color) {
        Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
        DrawableCompat.setTint(wrapDrawable, color);
        return wrapDrawable;
    }

    /**
     * Update top padding with animation
     */
    public static void updateTopPadding(final View view, int fromPadding, int toPadding) {
        ValueAnimator animator = ValueAnimator.ofFloat(fromPadding, toPadding);
        animator.setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                view.setPadding(view.getPaddingLeft(),
                        (int) animatedValue,
                        view.getPaddingRight(),
                        view.getPaddingBottom());
            }
        });
        animator.start();
    }

    /**
     * Update text size with animation
     */
    public static void updateTextSize(final TextView textView, float fromSize, float toSize) {
        ValueAnimator animator = ValueAnimator.ofFloat(fromSize, toSize);
        animator.setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, animatedValue);
            }
        });
        animator.start();
    }

    /**
     * Update alpha
     */
    public static void updateAlpha(final View view, float fromValue, float toValue) {
        ValueAnimator animator = ValueAnimator.ofFloat(fromValue, toValue);
        animator.setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                view.setAlpha(animatedValue);
            }
        });
        animator.start();
    }

    /**
     * Update text color with animation
     */
    public static void updateTextColor(final TextView textView, int fromColor, int toColor) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        colorAnimation.setDuration(150);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textView.setTextColor((Integer) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

    /**
     * Update text color with animation
     */
    public static void updateViewBackgroundColor(final View view, int fromColor, int toColor) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        colorAnimation.setDuration(150);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.setBackgroundColor((Integer) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

    /**
     * Update image view color with animation
     */
    public static void updateDrawableColor(final Context context, final int drawable,
                                           final ImageView imageView, int fromColor, int toColor) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        colorAnimation.setDuration(150);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                imageView.setImageDrawable(AHHelper.getTintDrawable(context, drawable,
                        (Integer) animator.getAnimatedValue()));
            }
        });
        colorAnimation.start();
    }

    /**
     * Update width
     */
    public static void updateWidth(final View view, float fromWidth, float toWidth) {
        ValueAnimator animator = ValueAnimator.ofFloat(fromWidth, toWidth);
        animator.setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.width = Math.round((float) animator.getAnimatedValue());
                view.setLayoutParams(params);
            }
        });
        animator.start();
    }

    /**
     * Check if the status bar is translucent
     *
     * @param context Context
     * @return
     */
    public static boolean isTranslucentStatusBar(Context context) {
        Window w = unwrap(context).getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        int flags = lp.flags;
        if ((flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION) == WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION) {
            return true;
        }

        return false;
    }

    /**
     * Get the height of the buttons bar
     *
     * @param context Context
     * @return
     */
    public static int getSoftButtonsBarSizePort(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            Window window = unwrap(context).getWindow();
            window.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            window.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }

    /**
     * Unwrap wactivity
     *
     * @param context Context
     * @return Activity
     */
    public static Activity unwrap(Context context) {
        while (!(context instanceof Activity)) {
            ContextWrapper wrapper = (ContextWrapper) context;
            context = wrapper.getBaseContext();
        }
        return (Activity) context;
    }
}
