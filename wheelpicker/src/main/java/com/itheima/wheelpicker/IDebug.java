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

package com.itheima.wheelpicker;

/**
 * Debug mode method interface.
 *
 * @author AigeStudio
 * @since 2011-04-11
 */
public interface IDebug {
    /**
     * Set debug mode.
     * Enabling debug mode may reduce code execution efficiency to a certain extent，
     * Please be sure to turn off the debug mode when it is officially released
     *
     * @param isDebug Whether it is debug mode
     */
    void setDebug(boolean isDebug);
}