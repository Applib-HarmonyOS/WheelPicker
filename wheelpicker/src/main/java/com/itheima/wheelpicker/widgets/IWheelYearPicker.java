package com.itheima.wheelpicker.widgets;

/**
 * Interface of WheelYearPicker.
 *
 * @author AigeStudio 2016-07-12
 * @version 1
 */
public interface IWheelYearPicker {
    /**
     * Set the year range.
     *
     * @param start Year of start
     * @param end   End year
     */
    void setYearFrame(int start, int end);

    /**
     * Get the start year.
     *
     * @return Year of start
     */
    int getYearStart();

    /**
     * Set the start year.
     *
     * @param start Year of start
     */
    void setYearStart(int start);

    /**
     * Get the end year.
     *
     * @return End year
     */
    int getYearEnd();

    /**
     * Set the end year.
     *
     * @param end End year
     */
    void setYearEnd(int end);

    /**
     * Get the year selected when the year picker was initialized.
     *
     * @return The year selected when the year selector was initialized
     */
    int getSelectedYear();

    /**
     * Set the year selected when the year picker is initialized.
     *
     * @param year The year selected when the year selector was initialized
     */
    void setSelectedYear(int year);

    /**
     * Get the currently selected year.
     *
     * @return Currently selected year
     */
    int getCurrentYear();
}