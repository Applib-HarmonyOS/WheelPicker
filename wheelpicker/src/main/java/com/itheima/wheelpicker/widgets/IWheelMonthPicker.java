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
