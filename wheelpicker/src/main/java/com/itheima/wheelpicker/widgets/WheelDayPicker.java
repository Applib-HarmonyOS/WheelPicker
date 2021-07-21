package com.itheima.wheelpicker.widgets;


import com.itheima.wheelpicker.WheelPicker;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ohos.agp.components.AttrSet;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;


/**
 * Picker for Day.
 *
 * @author AigeStudio 2016-07-12
 * @version 1
 */
public class WheelDayPicker extends WheelPicker implements IWheelDayPicker {
    private static final Map<Integer, List<Integer>> DAYS = new HashMap<>();

    private Calendar mCalendar;

    private int mYear;
    private int mMonth;
    private int mSelectedDay;

    public WheelDayPicker(Context context) {
        super(context);
    }

    /**
     * WheelDayPicker class for WheelPicker.
     *
     * @param context context
     * @param attrs attribute set
     * @throws NotExistException NotExistException
     * @throws WrongTypeException WrongTypeException
     * @throws IOException IOException
     */
    public WheelDayPicker(Context context, AttrSet attrs) throws NotExistException, WrongTypeException, IOException {
        super(context, attrs);

        mCalendar = Calendar.getInstance();

        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);

        updateDays();

        mSelectedDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        updateSelectedDay();
    }


    private void updateDays() {
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.MONTH, mMonth);

        int days = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<Integer> data = DAYS.get(days);
        if (null == data) {
            data = new ArrayList<>();
            for (int i = 1; i <= days; i++) {
                data.add(i);
            }
            DAYS.put(days, data);
        }
        super.setData(data);
    }

    private void updateSelectedDay() {
        setSelectedItemPosition(mSelectedDay - 1);
    }

    @Override
    public void setData(List data) {
        throw new UnsupportedOperationException("You can not invoke setData in WheelDayPicker");
    }

    @Override
    public int getSelectedDay() {
        return mSelectedDay;
    }

    @Override
    public void setSelectedDay(int day) {
        mSelectedDay = day;
        updateSelectedDay();
    }

    @Override
    public int getCurrentDay() {
        return Integer.valueOf(String.valueOf(getData().get(getCurrentItemPosition())));
    }

    @Override
    public void setYearAndMonth(int year, int month) {
        mYear = year;
        mMonth = month - 1;
        updateDays();
    }

    @Override
    public int getYear() {
        return mYear;
    }

    @Override
    public void setYear(int year) {
        mYear = year;
        updateDays();
    }

    @Override
    public int getMonth() {
        return mMonth;
    }

    @Override
    public void setMonth(int month) {
        mMonth = month - 1;
        updateDays();
    }
}