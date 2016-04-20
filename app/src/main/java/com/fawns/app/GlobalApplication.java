package com.fawns.app;

import android.app.Application;
import android.content.Context;

import com.fawns.app.common.ApiConstants;
import com.fawns.app.common.Constant;
import com.fawns.app.utils.ImageLoaderHelper;
import com.fawns.app.utils.VolleyHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.obsessive.library.base.BaseAppManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
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

        MobclickAgent.setDebugMode(true);
        MobclickAgent.updateOnlineConfig(this);
        MobclickAgent.openActivityDurationTrack(false);
        UmengUpdateAgent.update(this);

        VolleyHelper.getInstance().init(this);
        ImageLoader.getInstance().init(ImageLoaderHelper.getInstance(this).getImageLoaderConfiguration(ApiConstants.Paths.IMAGE_LOADER_CACHE_PATH));
    }

    @Override
    public void onLowMemory() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onLowMemory();
    }

    public void exitApp() {
        BaseAppManager.getInstance().clear();
        System.gc();
        MobclickAgent.onKillProcess(this);
        android.os.Process.killProcess(android.os.Process.myPid());
    }


}
