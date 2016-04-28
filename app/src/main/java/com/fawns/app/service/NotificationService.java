package com.fawns.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Project Fawns
 * Package com.fawns.app.service
 * Author Mengjiaxin
 * Date 2016/4/28 16:49
 * Desc 请用一句话来描述作用
 */
public class NotificationService extends Service {

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        // code to handle to create service
        // ......
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        // code to handler to start service
        // ......

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
