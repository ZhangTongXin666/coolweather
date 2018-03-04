package com.example.coolweather.util;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 老头儿和老婆儿 on 2018/3/4.
 */

public class OkhttpUtil {

    /**
     * 异步请求
     * @param address 请求的URL
     * @param callback 回掉接口
     */
    public static void sendOkHttpRequest(String address, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 同步请求
     * @param address 请求URL
     * @return
     */
    public static Response sendOkHttpExeRequest(String address){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
