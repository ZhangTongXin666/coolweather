package com.example.coolweather.util;

import android.util.Log;

/**
 * Created by 老头儿和老婆儿 on 2018/3/4.
 */

public class LogUtil {
    public static final int LEVEL_VERBOSE = 1;
    public static final int LEVEL_DEBUG = 2;
    public static final int LEVEL_INFO = 3;
    public static final int LEVEL_WARN = 4;
    public static final int LEVEL_ERROR = 5;
    public static int currentLevel = 1;

    public static void v(String tag, String content){
        if (currentLevel <= currentLevel){
            Log.v(tag, content);
        }
    }
    public static void d(String tag, String content){
        if (currentLevel <= LEVEL_DEBUG){
            Log.d(tag, content);
        }
    }
    public static void i(String tag, String content){
        if (currentLevel <= LEVEL_INFO){
            Log.i(tag, content);
        }
    }
    public static void w(String tag, String content){
        if (currentLevel <= LEVEL_WARN){
            Log.w(tag, content);
        }
    }
    public static void e(String tag, String content){
        if (currentLevel <= LEVEL_ERROR){
            Log.e(tag, content);
        }
    }

}
