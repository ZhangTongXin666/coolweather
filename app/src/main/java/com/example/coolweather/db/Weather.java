package com.example.coolweather.db;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 老头儿和老婆儿 on 2018/3/4.
 */

public class Weather {

    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;
    @SerializedName("daily_forecase")
    public List<Forecase> forecaseList;
}
