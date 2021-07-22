package com.itheima.wheelpicker.widgets;

/**
 * Interface of WheelMonthPicker.
 *
 * @author AigeStudio 2016-07-12
 * @version 1
 */
public interface IWheelMonthPicker {
    /**
     * Get the month selected when the month picker was initialized.
     *
     * @return Selected month
     */
    int getSelectedMonth();

    /**
     * Set the month selected when the month picker is initialized.
     *
     * @param month Selected month
     */
    void setSelectedMonth(int month);

    /**
     * Get the currently selected month.
     *
     * @return Currently selected month
     */
    int getCurrentMonth();
}
