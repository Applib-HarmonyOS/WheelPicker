package com.itheima.wheelpicker.widgets;


import com.itheima.wheelpicker.IDebug;
import com.itheima.wheelpicker.IWheelPicker;
import com.itheima.wheelpicker.ResourceTable;
import com.itheima.wheelpicker.WheelPicker;
import com.itheima.wheelpicker.util.LogUtil;
import ohos.agp.components.AttrSet;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.agp.text.Font;
import ohos.app.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WheelDatePicker extends DirectionalLayout implements WheelPicker.OnItemSelectedListener,
        IDebug, IWheelPicker, IWheelDatePicker, IWheelYearPicker, IWheelMonthPicker,
        IWheelDayPicker {
    private static final String TAG = WheelDatePicker.class.getSimpleName();
    private final SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-M-d", Locale.getDefault());

    private WheelYearPicker mPickerYear;
    private WheelMonthPicker mPickerMonth;
    private WheelDayPicker mPickerDay;

    private OnDateSelectedListener mListener;

    private Text mTVYear;
    private Text mTVMonth;
    private Text mTVDay;

    private int mYear;
    private int mMonth;
    private int mDay;

    private static final String S_1 = "WheelDatePicker!";
    private static final String S_2 = "WheelDatePicker";

    public WheelDatePicker(Context context) {
        super(context);
    }

    public WheelDatePicker(Context context, AttrSet attrs) {
        super(context, attrs);

        LayoutScatter.getInstance(context).parse(ResourceTable.Layout_view_wheel_date_picker, this, true);

        mPickerYear = (WheelYearPicker) findComponentById(ResourceTable.Id_wheel_date_picker_year);
        mPickerMonth = (WheelMonthPicker) findComponentById(ResourceTable.Id_wheel_date_picker_month);
        mPickerDay = (WheelDayPicker) findComponentById(ResourceTable.Id_wheel_date_picker_day);
        mPickerYear.setOnItemSelectedListener(this);
        mPickerMonth.setOnItemSelectedListener(this);
        mPickerDay.setOnItemSelectedListener(this);

        setMaximumWidthTextYear();
        mPickerMonth.setMaximumWidthText("00");
        mPickerDay.setMaximumWidthText("00");

        mTVYear = (Text) findComponentById(ResourceTable.Id_wheel_date_picker_year_tv);
        mTVMonth = (Text) findComponentById(ResourceTable.Id_wheel_date_picker_month_tv);
        mTVDay = (Text) findComponentById(ResourceTable.Id_wheel_date_picker_day_tv);

        mYear = mPickerYear.getCurrentYear();
        mMonth = mPickerMonth.getCurrentMonth();
        mDay = mPickerDay.getCurrentDay();
    }

    private void setMaximumWidthTextYear() {
        List<String> years = mPickerYear.getData();
        String lastYear = String.valueOf(years.get(years.size() - 1));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lastYear.length(); i++)
            sb.append("0");
        mPickerYear.setMaximumWidthText(sb.toString());
    }

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {
        if (picker.getId() == ResourceTable.Id_wheel_date_picker_year) {
            mYear = (int) data;
            mPickerDay.setYear(mYear);
        } else if (picker.getId() == ResourceTable.Id_wheel_date_picker_month) {
            mMonth = (int) data;
            mPickerDay.setMonth(mMonth);
        }
        mDay = mPickerDay.getCurrentDay();
        String date = mYear + "-" + mMonth + "-" + mDay;
        if (null != mListener) try {
            mListener.onDateSelected(this, sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDebug(boolean isDebug) {
        mPickerYear.setDebug(isDebug);
        mPickerMonth.setDebug(isDebug);
        mPickerDay.setDebug(isDebug);
    }

    @Override
    public int getVisibleItemCount() {
        if (mPickerYear.getVisibleItemCount() == mPickerMonth.getVisibleItemCount() &&
                mPickerMonth.getVisibleItemCount() == mPickerDay.getVisibleItemCount())
            return mPickerYear.getVisibleItemCount();
        throw new ArithmeticException("Can not get visible item count correctly from" +
                S_1);
    }

    @Override
    public void setVisibleItemCount(int count) {
        mPickerYear.setVisibleItemCount(count);
        mPickerMonth.setVisibleItemCount(count);
        mPickerDay.setVisibleItemCount(count);
    }

    @Override
    public boolean isCyclic() {
        return mPickerYear.isCyclic() && mPickerMonth.isCyclic() && mPickerDay.isCyclic();
    }

    @Override
    public void setCyclic(boolean isCyclic) {
        mPickerYear.setCyclic(isCyclic);
        mPickerMonth.setCyclic(isCyclic);
        mPickerDay.setCyclic(isCyclic);
    }

    /**
     * @deprecated (can not set OnItemSelectedListener for WheelDatePicker)
     */
    @Deprecated
    @Override
    public void setOnItemSelectedListener(WheelPicker.OnItemSelectedListener listener) {
        throw new UnsupportedOperationException("You can not set OnItemSelectedListener for" +
                S_2);
    }

    /**
     * @deprecated (can not get position of selected item from WheelDatePicker)
     */
    @Deprecated
    @Override
    public int getSelectedItemPosition() {
        throw new UnsupportedOperationException("You can not get position of selected item from" +
                S_2);
    }

    /**
     * @deprecated (can not set position of selected item for WheelDatePicker)
     */
    @Deprecated
    @Override
    public void setSelectedItemPosition(int position) {
        throw new UnsupportedOperationException("You can not set position of selected item for" +
                S_2);
    }

    /**
     * @deprecated (can not get position of current item from WheelDatePicker)
     */
    @Deprecated
    @Override
    public int getCurrentItemPosition() {
        throw new UnsupportedOperationException("You can not get position of current item from" +
                S_2);
    }

    /**
     * @deprecated (can not get data source from WheelDatePicker)
     */
    @Deprecated
    @Override
    public List getData() {
        throw new UnsupportedOperationException("You can not get data source from WheelDatePicker");
    }

    /**
     * @deprecated (don't need to set data source for WheelDatePicker)
     */
    @Deprecated
    @Override
    public void setData(List data) {
        throw new UnsupportedOperationException("You don't need to set data source for" +
                S_2);
    }

    /**
     * @deprecated (don't need to set same width for WheelDatePicker)
     */
    @Deprecated
    @Override
    public void setSameWidth(boolean hasSameSize) {
        throw new UnsupportedOperationException("You don't need to set same width for" +
                S_2);
    }

    /**
     * @deprecated (don't need to set same width for WheelDatePicker)
     */
    @Deprecated
    @Override
    public boolean hasSameWidth() {
        throw new UnsupportedOperationException("You don't need to set same width for" +
                S_2);
    }

    /**
     * @deprecated (WheelDatePicker unsupport set OnWheelChangeListener)
     */
    @Deprecated
    @Override
    public void setOnWheelChangeListener(WheelPicker.OnWheelChangeListener listener) {
        throw new UnsupportedOperationException("WheelDatePicker unsupport set" +
                "OnWheelChangeListener");
    }

    /**
     * @deprecated (can not get maximum width text from WheelDatePicker)
     */
    @Deprecated
    @Override
    public String getMaximumWidthText() {
        throw new UnsupportedOperationException("You can not get maximum width text from" +
                S_2);
    }

    /**
     * @deprecated (don't need to set maximum width text for WheelDatePicker)
     */
    @Deprecated
    @Override
    public void setMaximumWidthText(String text) {
        throw new UnsupportedOperationException("You don't need to set maximum width text for" +
                S_2);
    }

    /**
     * @deprecated (can not get maximum width text position from WheelDatePicker)
     */
    @Deprecated
    @Override
    public int getMaximumWidthTextPosition() {
        throw new UnsupportedOperationException("You can not get maximum width text position" +
                "from WheelDatePicker");
    }

    /**
     * @deprecated (don't need to set maximum width text position for WheelDatePicker)
     */
    @Deprecated
    @Override
    public void setMaximumWidthTextPosition(int position) {
        throw new UnsupportedOperationException("You don't need to set maximum width text" +
                "position for WheelDatePicker");
    }

    @Override
    public int getSelectedItemTextColor() {
        if (mPickerYear.getSelectedItemTextColor() == mPickerMonth.getSelectedItemTextColor() &&
                mPickerMonth.getSelectedItemTextColor() == mPickerDay.getSelectedItemTextColor())
            return mPickerYear.getSelectedItemTextColor();
        LogUtil.error(TAG, "Can not get color of selected item text correctly from" +
                S_1);
        return 0;
    }

    @Override
    public void setSelectedItemTextColor(int color) {
        mPickerYear.setSelectedItemTextColor(color);
        mPickerMonth.setSelectedItemTextColor(color);
        mPickerDay.setSelectedItemTextColor(color);
    }

    @Override
    public int getItemTextColor() {
        if (mPickerYear.getItemTextColor() == mPickerMonth.getItemTextColor() &&
                mPickerMonth.getItemTextColor() == mPickerDay.getItemTextColor())
            return mPickerYear.getItemTextColor();
        LogUtil.error(TAG, "Can not get color of item text correctly from" +
                S_1);
        return 0;
    }

    @Override
    public void setItemTextColor(int color) {
        mPickerYear.setItemTextColor(color);
        mPickerMonth.setItemTextColor(color);
        mPickerDay.setItemTextColor(color);
    }

    @Override
    public int getItemTextSize() {
        if (mPickerYear.getItemTextSize() == mPickerMonth.getItemTextSize() &&
                mPickerMonth.getItemTextSize() == mPickerDay.getItemTextSize())
            return mPickerYear.getItemTextSize();
        LogUtil.error(TAG, "Can not get size of item text correctly from" +
                S_1);
        return 0;
    }

    @Override
    public void setItemTextSize(int size) {
        mPickerYear.setItemTextSize(size);
        mPickerMonth.setItemTextSize(size);
        mPickerDay.setItemTextSize(size);
    }

    @Override
    public int getItemSpace() {
        if (mPickerYear.getItemSpace() == mPickerMonth.getItemSpace() &&
                mPickerMonth.getItemSpace() == mPickerDay.getItemSpace())
            return mPickerYear.getItemSpace();
        LogUtil.error(TAG, "Can not get item space correctly from WheelDatePicker!");
        return 0;
    }

    @Override
    public void setItemSpace(int space) {
        mPickerYear.setItemSpace(space);
        mPickerMonth.setItemSpace(space);
        mPickerDay.setItemSpace(space);
    }

    @Override
    public void setIndicator(boolean hasIndicator) {
        mPickerYear.setIndicator(hasIndicator);
        mPickerMonth.setIndicator(hasIndicator);
        mPickerDay.setIndicator(hasIndicator);
    }

    @Override
    public boolean hasIndicator() {
        return mPickerYear.hasIndicator() && mPickerMonth.hasIndicator() &&
                mPickerDay.hasIndicator();
    }

    @Override
    public int getIndicatorSize() {
        if (mPickerYear.getIndicatorSize() == mPickerMonth.getIndicatorSize() &&
                mPickerMonth.getIndicatorSize() == mPickerDay.getIndicatorSize())
            return mPickerYear.getIndicatorSize();
        LogUtil.error(TAG, "Can not get indicator size correctly from WheelDatePicker!");
        return 0;
    }

    @Override
    public void setIndicatorSize(int size) {
        mPickerYear.setIndicatorSize(size);
        mPickerMonth.setIndicatorSize(size);
        mPickerDay.setIndicatorSize(size);
    }

    @Override
    public int getIndicatorColor() {
        if (mPickerYear.getCurtainColor() == mPickerMonth.getCurtainColor() &&
                mPickerMonth.getCurtainColor() == mPickerDay.getCurtainColor())
            return mPickerYear.getCurtainColor();
        LogUtil.error(TAG, "Can not get indicator color correctly from WheelDatePicker!");
        return 0;
    }

    @Override
    public void setIndicatorColor(int color) {
        mPickerYear.setIndicatorColor(color);
        mPickerMonth.setIndicatorColor(color);
        mPickerDay.setIndicatorColor(color);
    }

    @Override
    public void setCurtain(boolean hasCurtain) {
        mPickerYear.setCurtain(hasCurtain);
        mPickerMonth.setCurtain(hasCurtain);
        mPickerDay.setCurtain(hasCurtain);
    }

    @Override
    public boolean hasCurtain() {
        return mPickerYear.hasCurtain() && mPickerMonth.hasCurtain() &&
                mPickerDay.hasCurtain();
    }

    @Override
    public int getCurtainColor() {
        if (mPickerYear.getCurtainColor() == mPickerMonth.getCurtainColor() &&
                mPickerMonth.getCurtainColor() == mPickerDay.getCurtainColor())
            return mPickerYear.getCurtainColor();
        LogUtil.error(TAG, "Can not get curtain color correctly from WheelDatePicker!");
        return 0;
    }

    @Override
    public void setCurtainColor(int color) {
        mPickerYear.setCurtainColor(color);
        mPickerMonth.setCurtainColor(color);
        mPickerDay.setCurtainColor(color);
    }

    @Override
    public void setAtmospheric(boolean hasAtmospheric) {
        mPickerYear.setAtmospheric(hasAtmospheric);
        mPickerMonth.setAtmospheric(hasAtmospheric);
        mPickerDay.setAtmospheric(hasAtmospheric);
    }

    @Override
    public boolean hasAtmospheric() {
        return mPickerYear.hasAtmospheric() && mPickerMonth.hasAtmospheric() &&
                mPickerDay.hasAtmospheric();
    }

    @Override
    public boolean isCurved() {
        return mPickerYear.isCurved() && mPickerMonth.isCurved() && mPickerDay.isCurved();
    }

    @Override
    public void setCurved(boolean isCurved) {
        mPickerYear.setCurved(isCurved);
        mPickerMonth.setCurved(isCurved);
        mPickerDay.setCurved(isCurved);
    }

    /**
     * @deprecated (can not get item align from WheelDatePicker)
     */
    @Deprecated
    @Override
    public int getItemAlign() {
        throw new UnsupportedOperationException("You can not get item align from WheelDatePicker");
    }

    /**
     * @deprecated (don't need to set item align for WheelDatePicker)
     */
    @Deprecated
    @Override
    public void setItemAlign(int align) {
        throw new UnsupportedOperationException("You don't need to set item align for" +
                S_2);
    }

    @Override
    public Font getTypeface() {
        if (mPickerYear.getTypeface().equals(mPickerMonth.getTypeface()) &&
                mPickerMonth.getTypeface().equals(mPickerDay.getTypeface()))
            return mPickerYear.getTypeface();
        LogUtil.error(TAG, "Can not get typeface correctly from WheelDatePicker!");
        return null;
    }

    @Override
    public void setTypeface(Font tf) {
        mPickerYear.setTypeface(tf);
        mPickerMonth.setTypeface(tf);
        mPickerDay.setTypeface(tf);
    }

    @Override
    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        mListener = listener;
    }

    @Override
    public Date getCurrentDate() {
        String date = mYear + "-" + mMonth + "-" + mDay;
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getItemAlignYear() {
        return mPickerYear.getItemAlign();
    }

    @Override
    public void setItemAlignYear(int align) {
        mPickerYear.setItemAlign(align);
    }

    @Override
    public int getItemAlignMonth() {
        return mPickerMonth.getItemAlign();
    }

    @Override
    public void setItemAlignMonth(int align) {
        mPickerMonth.setItemAlign(align);
    }

    @Override
    public int getItemAlignDay() {
        return mPickerDay.getItemAlign();
    }

    @Override
    public void setItemAlignDay(int align) {
        mPickerDay.setItemAlign(align);
    }

    @Override
    public WheelYearPicker getWheelYearPicker() {
        return mPickerYear;
    }

    @Override
    public WheelMonthPicker getWheelMonthPicker() {
        return mPickerMonth;
    }

    @Override
    public WheelDayPicker getWheelDayPicker() {
        return mPickerDay;
    }

    @Override
    public Text getTextViewYear() {
        return mTVYear;
    }

    @Override
    public Text getTextViewMonth() {
        return mTVMonth;
    }

    @Override
    public Text getTextViewDay() {
        return mTVDay;
    }

    @Override
    public void setYearFrame(int start, int end) {
        mPickerYear.setYearFrame(start, end);
    }

    @Override
    public int getYearStart() {
        return mPickerYear.getYearStart();
    }

    @Override
    public void setYearStart(int start) {
        mPickerYear.setYearStart(start);
    }

    @Override
    public int getYearEnd() {
        return mPickerYear.getYearEnd();
    }

    @Override
    public void setYearEnd(int end) {
        mPickerYear.setYearEnd(end);
    }

    @Override
    public int getSelectedYear() {
        return mPickerYear.getSelectedYear();
    }

    @Override
    public void setSelectedYear(int year) {
        mYear = year;
        mPickerYear.setSelectedYear(year);
        mPickerDay.setYear(year);
    }

    @Override
    public int getCurrentYear() {
        return mPickerYear.getCurrentYear();
    }

    @Override
    public int getSelectedMonth() {
        return mPickerMonth.getSelectedMonth();
    }

    @Override
    public void setSelectedMonth(int month) {
        mMonth = month;
        mPickerMonth.setSelectedMonth(month);
        mPickerDay.setMonth(month);
    }

    @Override
    public int getCurrentMonth() {
        return mPickerMonth.getCurrentMonth();
    }

    @Override
    public int getSelectedDay() {
        return mPickerDay.getSelectedDay();
    }

    @Override
    public void setSelectedDay(int day) {
        mDay = day;
        mPickerDay.setSelectedDay(day);
    }

    @Override
    public int getCurrentDay() {
        return mPickerDay.getCurrentDay();
    }

    @Override
    public void setYearAndMonth(int year, int month) {
        mYear = year;
        mMonth = month;
        mPickerYear.setSelectedYear(year);
        mPickerMonth.setSelectedMonth(month);
        mPickerDay.setYearAndMonth(year, month);
    }

    @Override
    public int getYear() {
        return getSelectedYear();
    }

    @Override
    public void setYear(int year) {
        setSelectedYear(year);
    }

    @Override
    public int getMonth() {
        return getSelectedMonth();
    }

    @Override
    public void setMonth(int month) {
        setSelectedMonth(month);
    }

    public interface OnDateSelectedListener {
        void onDateSelected(WheelDatePicker picker, Date date);
    }
}