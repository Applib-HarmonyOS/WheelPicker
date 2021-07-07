package com.itheima.wheelpicker.widgets;

import ohos.agp.components.Text;


import java.util.Date;

public interface IWheelDatePicker {
    void setOnDateSelectedListener(WheelDatePicker.OnDateSelectedListener listener);

    Date getCurrentDate();

    int getItemAlignYear();

    void setItemAlignYear(int align);

    int getItemAlignMonth();

    void setItemAlignMonth(int align);

    int getItemAlignDay();

    void setItemAlignDay(int align);

    WheelYearPicker getWheelYearPicker();

    WheelMonthPicker getWheelMonthPicker();

    WheelDayPicker getWheelDayPicker();

    Text getTextViewYear();

    Text getTextViewMonth();

    Text getTextViewDay();
}