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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import ohos.agp.components.AttrSet;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;


/**
 * Picker for Months.
 *
 * @author AigeStudio 2016-07-12
 * @version 1
 */
public class WheelMonthPicker extends WheelPicker implements IWheelMonthPicker {
    private int mSelectedMonth;

    public WheelMonthPicker(Context context) {
        super(context);
    }

    /**
     * WheelMonthPicker class for WheelPicker.
     *
     * @param context context
     * @param attrs attribute set
     * @throws NotExistException NotExistException
     * @throws WrongTypeException WrongTypeException
     * @throws IOException IOException
     */
    public WheelMonthPicker(Context context, AttrSet attrs) throws NotExistException, WrongTypeException, IOException {
        super(context, attrs);

        List<Integer> data = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            data.add(i);
        }
        super.setData(data);

        mSelectedMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        updateSelectedYear();
    }


    private void updateSelectedYear() {
        setSelectedItemPosition(mSelectedMonth - 1);
    }

    @Override
    public void setData(List data) {
        throw new UnsupportedOperationException("You can not invoke setData in WheelMonthPicker");
    }

    @Override
    public int getSelectedMonth() {
        return mSelectedMonth;
    }

    @Override
    public void setSelectedMonth(int month) {
        mSelectedMonth = month;
        updateSelectedYear();
    }

    @Override
    public int getCurrentMonth() {
        return Integer.valueOf(String.valueOf(getData().get(getCurrentItemPosition())));
    }
}