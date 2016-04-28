package com.fawns.app.service;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.obsessive.library.utils.TLog;
import com.umeng.message.UTrack;
import com.umeng.message.UmengBaseIntentService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.client.BaseConstants;
import org.json.JSONObject;

import java.util.List;

/**
 * Project Fawns
 * Package com.fawns.app.service
 * Author Mengjiaxin
 * Date 2016/4/28 16:47
 * Desc 请用一句话来描述作用
 */
public class FawnsPushIntentService extends UmengBaseIntentService {
    private static final String TAG = FawnsPushIntentService.class.getName();

    @Override
    protected void onMessage(Context context, Intent intent) {
        // 需要调用父类的函数，否则无法统计到消息送达
        super.onMessage(context, intent);
        try {
            //可以通过MESSAGE_BODY取得消息体
            String message = intent.getStringExtra(BaseConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            TLog.d(TAG, "message=" + message);    //消息体
            TLog.d(TAG, "custom="+msg.custom);    //自定义消息的内容
            TLog.d(TAG, "title="+msg.title);    //通知标题
            TLog.d(TAG, "text="+msg.text);    //通知内容
            // code  to handle message here
            // ...

            // 对完全自定义消息的处理方式，点击或者忽略
            boolean isClickOrDismissed = true;
            if(isClickOrDismissed) {
                //完全自定义消息的点击统计
                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
            } else {
                //完全自定义消息的忽略统计
                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
            }

            // 使用完全自定义消息来开启应用服务进程的示例代码
            // 首先需要设置完全自定义消息处理方式
            // mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
            // code to handle to start/stop service for app process
            JSONObject json = new JSONObject(msg.custom);
            String topic = json.getString("topic");
            TLog.d(TAG, "topic="+topic);
            if(topic != null && topic.equals("appName:startService")) {
                // 在友盟portal上新建自定义消息，自定义消息文本如下
                //{"topic":"appName:startService"}
                if(isServiceRunning(context, NotificationService.class.getName()))
                    return;
                Intent intent1 = new Intent();
                intent1.setClass(context, NotificationService.class);
                context.startService(intent1);
            } else if(topic != null && topic.equals("appName:stopService")) {
                // 在友盟portal上新建自定义消息，自定义消息文本如下
                //{"topic":"appName:stopService"}
                if(!isServiceRunning(context, NotificationService.class.getName()))
                    return;
                Intent intent1 = new Intent();
                intent1.setClass(context, NotificationService.class);
                context.stopService(intent1);
            }
        } catch (Exception e) {
            TLog.e(TAG, e.getMessage());
        }
    }

    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        if(serviceList == null || serviceList.isEmpty())
            return false;
        for(int i = 0; i < serviceList.size(); i++) {
            if(serviceList.get(i).service.getClassName().equals(className) && TextUtils.equals(
                    serviceList.get(i).service.getPackageName(), context.getPackageName())) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}