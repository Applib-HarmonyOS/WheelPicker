package com.itheima.wheelpickerdemo.wheelpicker;

import com.itheima.wheelpickerdemo.wheelpicker.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

/**
 * MainAbility Class for testing WheelPicker Library.
 */
public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
    }
}
