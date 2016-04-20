package com.fawns.app.common;

import android.os.Environment;

/**
 * Project Fawns
 * Package com.fawns.app.common
 * Author Mengjiaxin
 * Date 2016/4/12 11:26
 * Desc 接口
 */
public class ApiConstants {

    public static final class Paths {
        public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        public static final String IMAGE_LOADER_CACHE_PATH = "/Fawns/Images/";
    }

}
