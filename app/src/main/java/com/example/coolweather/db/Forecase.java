package com.example.coolweather.db;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 老头儿和老婆儿 on 2018/3/4.
 */

public class Forecase {

    public String date;
    @SerializedName("tem")
    public Temperature temperature;
    @SerializedName("cond")
    public More more;

    public class More{
        @SerializedName("txt_d")
        public String info;
    }

    public class Temperature {
        public String max;
        public String min;
    }
}
