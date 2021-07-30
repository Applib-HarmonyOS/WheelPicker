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