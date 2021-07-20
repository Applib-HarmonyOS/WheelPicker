package com.itheima.wheelpickerdemo.wheelpicker;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import com.itheima.wheelpicker.WheelPicker;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.components.Attr;
import ohos.agp.components.AttrSet;
import ohos.agp.text.Font;
import ohos.agp.utils.Color;
import ohos.app.Context;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

public class WheelPickerOhosTest {
    private WheelPicker wheelPicker;
//    private Context context;

    private final AttrSet attrSet = new AttrSet() {
        @Override
        public Optional<String> getStyle() {
            return Optional.empty();
        }

        @Override
        public int getLength() {
            return 0;
        }

        @Override
        public Optional<Attr> getAttr(int i) {
            return Optional.empty();
        }

        @Override
        public Optional<Attr> getAttr(String s) {
            return Optional.empty();
        }
    };

    @Before
    public void setup() {
        Context context = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
        wheelPicker = new WheelPicker(context, attrSet);
    }

    @Test
    public void testBundleName() {
        final String actualBundleName = AbilityDelegatorRegistry.getArguments().getTestBundleName();
        assertEquals("com.itheima.wheelpickerdemo.wheelpicker", actualBundleName);
    }

    /**
     * test case for VisibleItemCount
     */
    @Test
    public void testVisibleItemCount() {
        wheelPicker.setVisibleItemCount(4);
        assertEquals(5, wheelPicker.getVisibleItemCount());
    }

    /**
     * test case for Cyclic
     */
    @Test
    public void testCyclic() {
        wheelPicker.setCyclic(true);
        assertTrue(wheelPicker.isCyclic());
    }

    /**
     * test case for set data
     */
    @Test
    public void testSetData() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");

        wheelPicker.setData(list);
        assertEquals(list, wheelPicker.getData());
    }

    /**
     * test case for SameWidth
     */
    @Test
    public void testSameWidth() {
        wheelPicker.setSameWidth(true);
        assertTrue(wheelPicker.hasSameWidth());
    }

    /**
     * test case for MaximumWidthText
     */
    @Test
    public void testMaximumWidthText() {
        wheelPicker.setMaximumWidthText("wheelpicker");
        assertEquals("wheelpicker", wheelPicker.getMaximumWidthText());
    }

    /**
     * test case for MaximumWidthTextPosition
     */
    @Test
    public void testMaximumWidthTextPosition() {
        wheelPicker.setMaximumWidthTextPosition(10);
        assertEquals(10, wheelPicker.getMaximumWidthTextPosition());
    }

    /**
     * test case for SelectedItemColor
     */
    @Test
    public void testSelectedItemColor() {
        wheelPicker.setSelectedItemTextColor(Color.RED.getValue());
        assertEquals(Color.RED.getValue(), wheelPicker.getSelectedItemTextColor());
    }

    /**
     * test case for ItemTextColor
     */
    @Test
    public void testItemTextColor() {
        wheelPicker.setItemTextColor(Color.RED.getValue());
        assertEquals(Color.RED.getValue(), wheelPicker.getItemTextColor());
    }

    /**
     * test case for ItemTextSize
     */
    @Test
    public void testItemTextSize() {
        wheelPicker.setItemTextSize(10);
        assertEquals(10, wheelPicker.getItemTextSize());
    }

    /**
     * test case for ItemSpace
     */
    @Test
    public void testItemSpace() {
        wheelPicker.setItemSpace(10);
        assertEquals(10, wheelPicker.getItemSpace());
    }

    /**
     * test case for Indicator
     */
    @Test
    public void testIndicator() {
        wheelPicker.setIndicator(true);
        assertTrue(wheelPicker.hasIndicator());
    }

    /**
     * test case for IndicatorSize
     */
    @Test
    public void testIndicatorSize() {
        wheelPicker.setIndicatorSize(15);
        assertEquals(15, wheelPicker.getIndicatorSize());
    }

    /**
     * test case for IndicatorColor
     */
    @Test
    public void testIndicatorColor() {
        wheelPicker.setIndicatorColor(Color.RED.getValue());
        assertEquals(Color.RED.getValue(), wheelPicker.getIndicatorColor());
    }

    /**
     * test case for Curtain
     */
    @Test
    public void testCurtain() {
        wheelPicker.setCurtain(true);
        assertTrue(wheelPicker.hasCurtain());
    }

    /**
     * test case for CurtainColor
     */
    @Test
    public void testCurtainColor() {
        wheelPicker.setCurtainColor(Color.RED.getValue());
        assertEquals(Color.RED.getValue(), wheelPicker.getCurtainColor());
    }

    /**
     * test case for Atmospheric
     */
    @Test
    public void testAtmospheric() {
        wheelPicker.setAtmospheric(true);
        assertTrue(wheelPicker.hasAtmospheric());
    }

    /**
     * test case for Curved
     */
    @Test
    public void testCurved() {
        wheelPicker.setCurved(true);
        assertTrue(wheelPicker.isCurved());
    }

    /**
     * test case for ItemAlign
     */
    @Test
    public void testItemAlign() {
        wheelPicker.setItemAlign(WheelPicker.ALIGN_RIGHT);
        assertEquals(WheelPicker.ALIGN_RIGHT, wheelPicker.getItemAlign());
    }

    /**
     * test case for Typeface
     */
    @Test
    public void testTypeface() {
        wheelPicker.setTypeface(Font.MONOSPACE);
        assertEquals(Font.MONOSPACE, wheelPicker.getTypeface());
    }

    /**
     * test case for SelectedItemPosition
     */
    @Test
    public void testSelectedItemPosition() {
        wheelPicker.helperSetSelectedItemPosition(2, false);
        assertEquals(2, wheelPicker.getSelectedItemPosition());
    }
}