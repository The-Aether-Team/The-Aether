package com.aetherteam.aether.client.gui.screen.menu;

import net.minecraft.client.gui.GuiGraphics;

import java.util.function.BiConsumer;

public interface CustomBranding {
    boolean forEachLineBranding(boolean includeMC, boolean reverse, BiConsumer<Integer, String> lineConsumer, GuiGraphics guiGraphics, int i);

    boolean forEachAboveCopyrightLineBranding(BiConsumer<Integer, String> lineConsumer, GuiGraphics guiGraphics, int i);
}
