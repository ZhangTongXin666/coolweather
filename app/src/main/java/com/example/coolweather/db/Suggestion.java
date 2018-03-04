package com.example.coolweather.db;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 老头儿和老婆儿 on 2018/3/4.
 */

public class Suggestion {

    @SerializedName("comf")
    public Comfore comfore;
    @SerializedName("cw")
    public CarWash carWash;
    public Sport sport;

    public class Comfore{
        @SerializedName("txt")
        public String info;
    }
    public class CarWash{
        @SerializedName("txt")
        public String info;
    }
    public class Sport{
        @SerializedName("txt")
        public String info;
    }

}
