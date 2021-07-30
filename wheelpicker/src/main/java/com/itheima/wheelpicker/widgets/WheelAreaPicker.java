/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.itheima.wheelpicker.widgets;


import com.itheima.wheelpicker.WheelPicker;
import com.itheima.wheelpicker.model.City;
import com.itheima.wheelpicker.model.Province;
import com.itheima.wheelpicker.util.LogUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import ohos.agp.colors.RgbPalette;
import ohos.agp.components.AttrSet;
import ohos.agp.components.DirectionalLayout;
import ohos.app.Context;


/**
 * WheelAreaPicker
 * Created by Administrator on 2016/9/14 0014.
 */
public class WheelAreaPicker extends DirectionalLayout implements IWheelAreaPicker {
    private static final float ITEM_TEXT_SIZE = 18;
    private static final String SELECTED_ITEM_COLOR = "#353535";
    private static final int PROVINCE_INITIAL_INDEX = 0;
    private static final String TAG = WheelAreaPicker.class.getSimpleName();

    private List<Province> mProvinceList;
    private List<City> mCityList;
    private List<String> mProvinceName;
    private List<String> mCityName;

    private LayoutConfig layoutConfig;

    private WheelPicker mWPProvince;
    private WheelPicker mWPCity;
    private WheelPicker mWPArea;

    /**
     * WheelAreaPicker class for WheelPicker.
     *
     * @param context context
     * @param attrs attribute set
     */
    public WheelAreaPicker(Context context, AttrSet attrs) {
        super(context, attrs);

        initLayoutParams();

        initView(context);

        try {
            mProvinceList = getJsonDataFromAssets(context);
        } catch (IOException e) {
            LogUtil.error(TAG, "loadRegionsFromFile : IOException");
        }

        obtainProvinceData();

        addListenerToWheelPicker();
    }

    @SuppressWarnings("unchecked")
    private List<Province> getJsonDataFromAssets(Context context) throws IOException {
        ObjectInputStream objectInputStream = null;
        String path = "resources/rawfile/" + "RegionJsonData.dat";
        List<Province> provinceList = null;
        try (
            InputStream inputStream = context.getResourceManager().getRawFileEntry(path).openRawFile()
        ) {
            objectInputStream = new ObjectInputStream(inputStream);
            provinceList = (List<Province>) objectInputStream.readObject();

        } catch (FileNotFoundException exception) {
            LogUtil.error(TAG, "loadRegionsFromFile : FileNotFoundException");
        } catch (IOException exception) {
            LogUtil.error(TAG, "loadRegionsFromFile : IOException");
        } catch (ClassNotFoundException e) {
            LogUtil.error(TAG, "loadRegionsFromFile : ClassNotFoundException");
        }
        return provinceList;
    }

    private void initLayoutParams() {
        layoutConfig = new LayoutConfig();
        layoutConfig.setMargins(5, 5, 5, 5);
        layoutConfig.width = 0;
    }

    private void initView(Context context) {
        setOrientation(HORIZONTAL);

        mProvinceName = new ArrayList<>();
        mCityName = new ArrayList<>();

        mWPProvince = new WheelPicker(context);
        mWPCity = new WheelPicker(context);
        mWPArea = new WheelPicker(context);

        initWheelPicker(mWPProvince, context, 1);
        initWheelPicker(mWPCity, context, 1.5f);
        initWheelPicker(mWPArea, context, 1.5f);
    }

    private void initWheelPicker(WheelPicker wheelPicker, Context context, float weight) {
        layoutConfig.weight = weight;
        wheelPicker.setItemTextSize(dip2px(context, ITEM_TEXT_SIZE));
        wheelPicker.setSelectedItemTextColor(RgbPalette.parse(SELECTED_ITEM_COLOR));
        wheelPicker.setCurved(true);
        wheelPicker.setLayoutConfig(layoutConfig);
        addComponent(wheelPicker);
    }

    private void obtainProvinceData() {
        for (Province province : mProvinceList) {
            mProvinceName.add(province.getName());
        }
        mWPProvince.setData(mProvinceName);
        setCityAndAreaData(PROVINCE_INITIAL_INDEX);
    }

    private void addListenerToWheelPicker() {
        mWPProvince.setOnItemSelectedListener((picker, data, position) -> {
            //Get a collection of all cities in the province
            mCityList = mProvinceList.get(position).getCity();
            setCityAndAreaData(position);
        });

        mWPCity.setOnItemSelectedListener((picker, data, position) ->
                mWPArea.setData(mCityList.get(position).getArea())
        );
    }

    private void setCityAndAreaData(int position) {
        //Get a collection of all cities in the province
        mCityList = mProvinceList.get(position).getCity();
        //Get the names of all cities
        //Reset the previous city collection data
        mCityName.clear();
        for (City city : mCityList) {
            mCityName.add(city.getName());
        }
        mWPCity.setData(mCityName);
        mWPCity.setSelectedItemPosition(0);
        //Get the name of the district corresponding to the first city
        //Reset the data of the previous city collection
        mWPArea.setData(mCityList.get(0).getArea());
        mWPArea.setSelectedItemPosition(0);
    }

    @Override
    public String getProvince() {
        return mProvinceList.get(mWPProvince.getCurrentItemPosition()).getName();
    }

    @Override
    public String getCity() {
        return mCityList.get(mWPCity.getCurrentItemPosition()).getName();
    }

    @Override
    public String getArea() {
        return mCityList.get(mWPCity.getCurrentItemPosition()).getArea().get(mWPArea.getCurrentItemPosition());
    }

    @Override
    public void hideArea() {
        this.removeComponentAt(2);
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResourceManager().getDeviceCapability().screenDensity;
        return (int) (dpValue * scale + 0.5f);
    }

}
