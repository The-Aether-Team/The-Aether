package com.aetherteam.aetherfabric.client.events;

import com.google.common.collect.MapMaker;
import net.minecraft.client.gui.screens.Screen;

import java.util.Map;

public class ScreenKeyboardEventsExtension {
    private static final Map<Screen, Boolean> SCREEN_TO_PRESSED_RESULT = new MapMaker().weakKeys().makeMap();

    public static void setPressedResult(Screen screen, boolean pressed) {
        SCREEN_TO_PRESSED_RESULT.put(screen, pressed);
    }

    public static boolean getPressedResult(Screen screen)  {
        return SCREEN_TO_PRESSED_RESULT.getOrDefault(screen, false);
    }
}
