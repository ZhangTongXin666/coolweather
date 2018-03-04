package com.example.coolweather.db;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 老头儿和老婆儿 on 2018/3/4.
 */

public class AQI {

    public AQICity city;

    public class AQICity{
        public String aqi;
        public String pm25;
    }
}
