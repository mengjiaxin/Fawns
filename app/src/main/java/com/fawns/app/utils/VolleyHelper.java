package com.fawns.app.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.obsessive.library.utils.OkHttpStack;
import com.squareup.okhttp.OkHttpClient;


/**
 * Project SocialSecurity
 * Package com.mjx.social.app.utils
 * Author Mengjiaxin
 * Date 2016/4/7 14:49
 * Desc volley帮助类
 */
public class VolleyHelper {

    private RequestQueue requestQueue = null;

    private static volatile VolleyHelper instance = null;

    private VolleyHelper() {
    }

    public static VolleyHelper getInstance() {
        if (null == instance) {
            synchronized (VolleyHelper.class) {
                if (null == instance) {
                    instance = new VolleyHelper();
                }
            }
        }
        return instance;
    }

    /**
     * init volley helper
     *
     * @param context
     */
    public void init(Context context) {
        requestQueue = Volley.newRequestQueue(context, new OkHttpStack(new OkHttpClient()));
    }

    /**
     * get request queue
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (null != requestQueue) {
            return requestQueue;
        } else {
            throw new IllegalArgumentException("RequestQueue is not initialized.");
        }
    }
}
