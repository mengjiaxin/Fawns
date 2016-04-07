package com.fawns.app;

import android.app.Application;
import android.content.Context;

import com.mjx.social.app.common.Constant;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.autolayout.config.AutoLayoutConifg;

/**
 * Project SocialSecurity
 * Package com.mjx.social.app
 * Author Mengjiaxin
 * Date 2016/4/7 11:46
 * Desc APP的全局Application
 */
public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 添加屏幕自适应
        AutoLayoutConifg.getInstance().useDeviceSize();
        // 腾讯Bug统计管理 Bugly Android,
        CrashReport.initCrashReport(this, Constant.BUGLY_APP_KEY, false);
        // 模拟一次crash
        // CrashReport.testJavaCrash();

    }

    public static Context getContext() {
        return getContext();
    }


}
