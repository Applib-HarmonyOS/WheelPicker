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