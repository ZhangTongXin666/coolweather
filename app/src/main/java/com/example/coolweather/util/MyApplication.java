package com.example.coolweather.util;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * Created by 老头儿和老婆儿 on 2018/3/4.
 */

public class MyApplication extends Application {

    public static Context context;

    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
        LitePalApplication.initialize(context);
    }
    
    public static Context getContext(){
        return context;
    }

}
