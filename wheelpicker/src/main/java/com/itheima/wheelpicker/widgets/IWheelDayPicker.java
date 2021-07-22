package com.itheima.wheelpicker.widgets;

/**
 * Interface of WheelDayPicker.
 *
 * @author AigeStudio 2016-07-12
 * @version 1
 */
public interface IWheelDayPicker {
    /**
     * Get the date selected when the date picker was initialized.
     *
     * @return Selected date
     */
    int getSelectedDay();

    /**
     * Set the date selected when the date picker is initialized.
     *
     * @param day selected Date
     */
    void setSelectedDay(int day);

    /**
     * Get the currently selected date.
     *
     * @return Selected date
     */
    int getCurrentDay();

    /**
     * Set the year and month.
     *
     * @param year  particular year
     * @param month Month
     */
    void setYearAndMonth(int year, int month);

    /**
     * Get the year.
     *
     * @return particular year
     */
    int getYear();

    /**
     * Set year.
     *
     * @param year ...
     */
    void setYear(int year);

    /**
     * Get the month.
     *
     * @return Month
     */
    int getMonth();

    /**
     * Set month.
     *
     * @param month Month
     */
    void setMonth(int month);
}