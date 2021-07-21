package com.itheima.wheelpicker;

import java.util.List;
import ohos.agp.text.Font;

/**
 * Wheel selector method interface.
 *
 * <p>Interface of WheelPicker
 *
 * @author AigeStudio 2015-12-03
 * @author AigeStudio 2015-12-08
 * @author AigeStudio 2015-12-12
 * @author AigeStudio 2016-06-17
 *         Update project structure
 * @version 1.1.0
 */
public interface IWheelPicker {

    /**
     * Get the number of visible data items of the wheel selector.
     *
     * <p>Get the count of current visible items in WheelPicker
     *
     * @return The number of visible data items in the wheel selector
     */
    int getVisibleItemCount();

    /**
     * Set the count of current visible items in WheelPicker.
     * The count of current visible items in WheelPicker must greater than 1
     * Notice:count of current visible items in WheelPicker will always is an odd number, even you
     * can set an even number for it, it will be change to an odd number eventually
     * By default, the count of current visible items in WheelPicker is 7
     *
     * @param count The number of visible data items on the wheel selector
     */
    void setVisibleItemCount(int count);

    /**
     * Whether WheelPicker is cyclic or not.
     *
     * @return Whether it is a cyclic state
     */
    boolean isCyclic();

    /**
     * Set whether WheelPicker is cyclic or not.
     * WheelPicker's items will be end to end and in an infinite loop if setCyclic true, and there
     * is no border whit scroll when WheelPicker in cyclic state
     *
     * @param isCyclic Whether it is a cyclic state
     */
    void setCyclic(boolean isCyclic);

    /**
     * Set the scroll wheel Item to select the listener.
     *
     * @param listener Scroller Item selects the listener{@link WheelPicker.OnItemSelectedListener}
     */
    void setOnItemSelectedListener(WheelPicker.OnItemSelectedListener listener);

    /**
     * Get the position of current selected item in data source.
     * Notice:The value by return will not change when WheelPicker scroll, this method will always
     * return the value which {@link #setSelectedItemPosition(int)} set, the value this method
     * return will be changed if and only if call the
     * {@link #setSelectedItemPosition(int)}
     * set a new value
     * If you only want to get the position of current selected item in data source, you can get it
     * through {@link WheelPicker.OnItemSelectedListener} or call
     * {@link #getCurrentItemPosition()} directly
     *
     * @return The position of the data displayed by the currently selected data item in the data source
     */
    int getSelectedItemPosition();

    /**
     * Set the position of current selected item in data source.
     * Call this method and set a new value may be reinitialize the location of WheelPicker. For
     * example, you call this method after scroll the WheelPicker and set selected item position
     * with a new value, WheelPicker will clear the related parameters last scroll set and reset
     * series of data, and make the position 3 as a new starting point of WheelPicker, this behavior
     * maybe influenced some attribute you set last time, such as parameters of method in
     * {@link WheelPicker.OnWheelChangeListener} and
     * {@link WheelPicker.OnItemSelectedListener}, so you must always
     * consider the influence when you call this method set a new value
     * You should always set a value which greater than or equal to 0 and less than data source's
     * length
     * By default, position of current selected item in data source is 0
     *
     * @param position The position of the data displayed by the currently selected data item in the data source
     */
    void setSelectedItemPosition(int position);

    /**
     * Get the position of current selected item in data source.
     * The difference between {@link #getSelectedItemPosition()}, the value this method return will
     * change by WheelPicker scrolled
     *
     * @return The position of the data displayed by the currently selected data item in the data source
     */
    int getCurrentItemPosition();

    /**
     * Get data source of WheelPicker.
     *
     * @return Datasheets
     */
    List<String> getData();

    /**
     * Set data source of WheelPicker.
     * The data source can be any type, WheelPicker will change the data to string when it draw the
     * item.
     * There is a default data source when you not set the data source for WheelPicker.
     * Set data source for WheelPicker will reset state of it, you can refer to
     * {@link #setSelectedItemPosition(int)} for more details.
     *
     * @param data Datasheets
     */
    void setData(List<String> data);

    /**
     * Set items of WheelPicker if has same width.
     * WheelPicker will traverse the data source to calculate each data text width to find out the
     * maximum text width for the final view width, this process maybe spends a lot of time and
     * reduce efficiency when data source has large amount data, in most large amount data case,
     * data text always has same width, you can call this method tell to WheelPicker your data
     * source has same width to save time and improve efficiency.
     * Sometimes the data source you set is positively has different text width, but maybe you know
     * the maximum width text's position in data source, then you can call
     * {@link #setMaximumWidthTextPosition(int)} tell to WheelPicker where is the maximum width text
     * in data source, WheelPicker will calculate its width base on this text which found by
     * position. If you don't know the position of maximum width text in data source, but you have
     * maximum width text, you can call {@link #setMaximumWidthText(String)} tell to WheelPicker
     * what maximum width text is directly, WheelPicker will calculate its width base on this text.
     *
     * @param hasSameSize Whether it has the same width
     */
    void setSameWidth(boolean hasSameSize);

    /**
     * Whether items has same width or not.
     *
     * @return Whether it has the same width
     */
    boolean hasSameWidth();

    /**
     * Set the scroll wheel scrolling state change listener.
     *
     * @param listener Scroll wheel status change listener
     * @see WheelPicker.OnWheelChangeListener
     */
    void setOnWheelChangeListener(WheelPicker.OnWheelChangeListener listener);

    /**
     * Get maximum width text.
     *
     * @return Widest text
     */
    String getMaximumWidthText();

    /**
     * Set maximum width text.
     *
     * @param text Widest text
     * @see #setSameWidth(boolean)
     */
    void setMaximumWidthText(String text);

    /**
     * Get the position of maximum width text in data source.
     *
     * @return The position of the widest text in the data source
     */
    int getMaximumWidthTextPosition();

    /**
     * Set the position of maximum width text in data source.
     *
     * @param position The position of the widest text in the data source
     * @see #setSameWidth(boolean)
     */
    void setMaximumWidthTextPosition(int position);

    /**
     * Get text color of current selected item.
     * For example 0xFF123456
     *
     * @return The text color of the currently selected data item
     */
    int getSelectedItemTextColor();

    /**
     * Set text color of current selected item.
     * For example 0xFF123456
     *
     * @param color The text color of the currently selected data item，16-bit color value
     */
    void setSelectedItemTextColor(int color);

    /**
     * Get text color of items.
     * For example 0xFF123456
     *
     * @return Data item text color
     */
    int getItemTextColor();

    /**
     * Set text color of items.
     * For example 0xFF123456
     *
     * @param color Data item text color，16-bit color value
     */
    void setItemTextColor(int color);

    /**
     * Get text size of items.
     * Unit in px
     *
     * @return Data item text size
     */
    int getItemTextSize();

    /**
     * Set text size of items.
     * Unit in px
     *
     * @param size Set the text size of the data item，UNIT：px
     */
    void setItemTextSize(int size);

    /**
     * Get space between items.
     * Unit in px
     *
     * @return The spacing between data items of the wheel selector
     */
    int getItemSpace();

    /**
     * Set space between items.
     * Unit in px
     *
     * @param space The spacing between data items of the wheel selector，unit：px
     */
    void setItemSpace(int space);

    /**
     * Set whether WheelPicker display indicator or not.
     * WheelPicker will draw two lines above an below current selected item if display indicator
     * Notice:Indicator's size will not participate in WheelPicker's size calculation, it will drawn
     * above the content
     *
     * @param hasIndicator Is there an indicator
     */
    void setIndicator(boolean hasIndicator);

    /**
     * Whether WheelPicker display indicator or not.
     *
     * @return Whether the wheel selector has an indicator
     */
    boolean hasIndicator();

    /**
     * Get size of indicator.
     * Unit in px
     *
     * @return Indicator Size
     */
    int getIndicatorSize();

    /**
     * Set size of indicator.
     * Unit in px
     *
     * @param size set Indicator Size，Unit：px
     */
    void setIndicatorSize(int size);

    /**
     * Get color of indicator.
     * For example 0xFF123456
     *
     * @return Wheel selector indicator color，16-bit color value
     */
    int getIndicatorColor();

    /**
     * Set color of indicator.
     * For example 0xFF123456
     *
     * @param color Wheel selector indicator color，16-bit color value
     */
    void setIndicatorColor(int color);

    /**
     * Set whether WheelPicker display curtain or not.
     * WheelPicker will draw a rectangle as big as current selected item and fill specify color
     * above content if curtain display
     *
     * @param hasCurtain Whether the wheel selector shows the curtain
     */
    void setCurtain(boolean hasCurtain);

    /**
     * Whether WheelPicker display curtain or not.
     *
     * @return Whether the wheel selector shows the curtain
     */
    boolean hasCurtain();

    /**
     * Get color of curtain.
     * For example 0xFF123456
     *
     * @return Wheel selector screen color，16-bit color value
     */
    int getCurtainColor();

    /**
     * Set color of curtain.
     * For example 0xFF123456
     *
     * @param color Wheel selector screen color，16-bit color value
     */
    void setCurtainColor(int color);

    /**
     * Set whether WheelPicker has atmospheric or not.
     * WheelPicker's items will be transparent from center to ends if atmospheric display
     *
     * @param hasAtmospheric Whether the wheel selector has a sense of air
     */
    void setAtmospheric(boolean hasAtmospheric);

    /**
     * Whether WheelPicker has atmospheric or not.
     *
     * @return Whether the wheel selector has a sense of air
     */
    boolean hasAtmospheric();

    /**
     * Whether WheelPicker enable curved effect or not.
     *
     * @return Whether the scroll wheel selector turns on the curling effect
     */
    boolean isCurved();

    /**
     * Set whether WheelPicker enable curved effect or not.
     * If setCurved true, WheelPicker will display with curved effect looks like ends bend into
     * screen with perspective.
     * WheelPicker's curved effect base on strict geometric model, some parameters relate with size
     * maybe invalidated, for example each item size looks like different because of perspective in
     * curved, the space between items looks like have a little difference
     *
     * @param isCurved Whether the scroll wheel selector turns on the curling effect
     */
    void setCurved(boolean isCurved);

    /**
     * Get alignment of WheelPicker.
     *
     * @return Alignment of wheel selector data items
     */
    int getItemAlign();

    /**
     * Set alignment of WheelPicker.
     * The default alignment of WheelPicker is {@link WheelPicker#ALIGN_CENTER}
     *
     * @param align Alignment identification value
     *              This value can only be one of the following values：
     *              {@link WheelPicker#ALIGN_CENTER}
     *              {@link WheelPicker#ALIGN_LEFT}
     *              {@link WheelPicker#ALIGN_RIGHT}
     */
    void setItemAlign(int align);

    /**
     * Get typeface of item text.
     *
     * @return Text font object
     */
    Font getTypeface();

    /**
     * Set typeface of item text.
     * Set typeface of item text maybe cause WheelPicker size change
     *
     * @param tf Font object
     */
    void setTypeface(Font tf);
}