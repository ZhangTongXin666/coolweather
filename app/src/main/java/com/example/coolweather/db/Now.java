package com.example.coolweather.db;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 老头儿和老婆儿 on 2018/3/4.
 */

public class Now {

    @SerializedName("tem")
    public int temperature;
    @SerializedName("cond")
    public More more;

    public class More{
        @SerializedName("txt")
        public String info;
    }

}
