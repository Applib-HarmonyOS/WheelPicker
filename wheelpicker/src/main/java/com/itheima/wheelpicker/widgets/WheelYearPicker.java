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
 * Picker for Years.
 *
 * @author AigeStudio 2016-07-12
 * @version 1
 */
public class WheelYearPicker extends WheelPicker implements IWheelYearPicker {
    private int mYearStart = 1000;
    private int mYearEnd = 3000;
    private int mSelectedYear;

    public WheelYearPicker(Context context) {
        super(context);
    }

    /**
     * WheelYearPicker class for WheelPicker.
     *
     * @param context context
     * @param attrs attribute set
     * @throws NotExistException NotExistException
     * @throws WrongTypeException WrongTypeException
     * @throws IOException IOException
     */
    public WheelYearPicker(Context context, AttrSet attrs) throws NotExistException, WrongTypeException, IOException {
        super(context, attrs);

        updateYears();
        mSelectedYear = Calendar.getInstance().get(Calendar.YEAR);
        updateSelectedYear();
    }

    private void updateYears() {
        List<Integer> data = new ArrayList<>();
        for (int i = mYearStart; i <= mYearEnd; i++) {
            data.add(i);
        }
        super.setData(data);
    }

    private void updateSelectedYear() {
        setSelectedItemPosition(mSelectedYear - mYearStart);
    }

    @Override
    public void setData(List data) {
        throw new UnsupportedOperationException("You can not invoke setData in WheelYearPicker");
    }

    @Override
    public void setYearFrame(int start, int end) {
        mYearStart = start;
        mYearEnd = end;
        mSelectedYear = getCurrentYear();
        updateYears();
        updateSelectedYear();
    }

    @Override
    public int getYearStart() {
        return mYearStart;
    }

    @Override
    public void setYearStart(int start) {
        mYearStart = start;
        mSelectedYear = getCurrentYear();
        updateYears();
        updateSelectedYear();
    }

    @Override
    public int getYearEnd() {
        return mYearEnd;
    }

    @Override
    public void setYearEnd(int end) {
        mYearEnd = end;
        updateYears();
    }

    @Override
    public int getSelectedYear() {
        return mSelectedYear;
    }

    @Override
    public void setSelectedYear(int year) {
        mSelectedYear = year;
        updateSelectedYear();
    }

    @Override
    public int getCurrentYear() {
        return Integer.valueOf(String.valueOf(getData().get(getCurrentItemPosition())));
    }
}