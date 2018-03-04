package com.example.coolweather.db;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 老头儿和老婆儿 on 2018/3/4.
 */

public class Basic {

    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public int weatherId;
    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }

}
