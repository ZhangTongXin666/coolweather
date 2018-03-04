package com.example.coolweather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolweather.db.City;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;
import com.example.coolweather.util.LogUtil;
import com.example.coolweather.util.OkhttpUtil;
import com.example.coolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 老头儿和老婆儿 on 2018/3/4.
 */

public class ChooseAreaFragment extends Fragment {
    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private Province selectedProvince;
    private City selectedCity;
    private County selectedCounty;
    private int currentLevel;
    private String baseAddress = "http://guolin.tech/api/china";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.choose_area, container);
        titleText = view.findViewById(R.id.title_text);
        backButton = view.findViewById(R.id.back_button);
        listView = view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE){
                    selectedProvince = provinceList.get(position);
                    queryCity();
                }else if (currentLevel == LEVEL_CITY){
                    selectedCity = cityList.get(position);
                    queryCounty();
                }else {
                    String weatherId = countyList.get(position).getWeatherId();
                    if (getActivity() instanceof MainActivity){
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weaher_id", weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    }else if (getActivity() instanceof WeatherActivity){
                        WeatherActivity weatherActivity = (WeatherActivity)getActivity();
                        weatherActivity.drawerLayout.closeDrawers();
                        weatherActivity.swipeLayout.setRefreshing(true);
                        weatherActivity.requestWeather(weatherId);
                    }
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY){
                    queryCity();
                }else if (currentLevel == LEVEL_CITY){
                    queryProvince();
                }
            }
        });
        queryProvince();
    }

    private void queryProvince() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        LogUtil.e("TAG", "省份数据量："+provinceList.size());
        if (provinceList.size() > 0){
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
        currentLevel = LEVEL_PROVINCE;
        }else {
            queryFromServe(baseAddress, "province");
        }
    }

    private void queryFromServe(String address, final String type) {
        showProgressDialog();
        LogUtil.e("TAG", "请求地址："+address);
        OkhttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseTest = response.body().string();
                LogUtil.e("TAG", "请求内容："+responseTest);
                boolean result = false;
                if (type.equals("province")){
                    result = Utility.handleProvinceResponse(responseTest);
                }else if (type.equals("city")){
                    result = Utility.handleCityResponse(responseTest, selectedProvince.getId());
                }else{
                    result = Utility.handleCountyResponse(responseTest, selectedCity.getId());
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if (type.equals("province")){
                                queryProvince();
                            }else if (type.equals("city")){
                                queryCity();
                            }else {
                                queryCounty();
                            }
                        }
                    });
                }
            }
        });
    }

    private void closeProgressDialog() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (progressDialog == null){
         progressDialog = new ProgressDialog(getActivity());
         progressDialog.setMessage("正在加载。。。");
         progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();

    }


    private void queryCounty() {
        titleText.setText(selectedCity.getCityName());
        countyList = DataSupport.where("cityId = ?", String.valueOf(selectedCity.getId()))
                .find(County.class);
        if (countyList.size() > 0){
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        }else {
            String address = baseAddress+"/"+selectedProvince.getProvinceCode()+"/"+selectedCity.getCityCode();
            queryFromServe(address, "county");
        }
    }

    private void queryCity() {
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceId = ?", String.valueOf(selectedProvince.getId()))
                .find(City.class);
        if (cityList.size() > 0){
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        }else {
                int provinceCode = selectedProvince.getProvinceCode();
                String address = baseAddress+"/"+provinceCode;
                queryFromServe(address, "city");
        }
    }
}
