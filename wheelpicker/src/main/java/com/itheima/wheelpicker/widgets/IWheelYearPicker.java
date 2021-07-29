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