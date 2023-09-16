package com.aetherteam.aether.mixin.mixins.client.accessor;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Gui.class)
public interface GuiAccessor {
    @Accessor("random")
    RandomSource aether$getRandom();

    @Accessor("lastHealthTime")
    long aether$getLastHealthTime();

    @Accessor("healthBlinkTime")
    long aether$getHealthBlinkTime();
}