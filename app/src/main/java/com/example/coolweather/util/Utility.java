package com.example.coolweather.util;

import android.text.TextUtils;

import com.example.coolweather.db.City;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;
import com.example.coolweather.db.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 老头儿和老婆儿 on 2018/3/4.
 */

public class Utility {

    public static boolean handleProvinceResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                List<Province> provinceList = new ArrayList<>();
                JSONArray allJsonObject = new JSONArray(response);
               final int length = allJsonObject.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jsonObject = allJsonObject.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(jsonObject.getString("name"));
                    province.setProvinceCode(jsonObject.getInt("id"));
                    provinceList.add(province);
                }
                DataSupport.saveAll(provinceList);
                provinceList.clear();
                provinceList = null;
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCityResponse(String response, int provinceId){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allJsonObject = new JSONArray(response);
               final int length = allJsonObject.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jsonObject = allJsonObject.getJSONObject(i);
                    City city = new City();
                    city.setCityCode(jsonObject.getInt("id"));
                    city.setCityName(jsonObject.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCountyResponse(String response, int cityId){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allJsonObject = new JSONArray(response);
               final int length = allJsonObject.length();
               LogUtil.e("TAG", "县区数据长度："+length);
                for (int i = 0; i < length; i++) {
                    JSONObject jsonObject = allJsonObject.getJSONObject(i);
                    County county = new County();
                    county.setWeatherId(jsonObject.getString("weather_id"));
                    county.setCountyName(jsonObject.getString("name"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static Weather handleWeatherResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
