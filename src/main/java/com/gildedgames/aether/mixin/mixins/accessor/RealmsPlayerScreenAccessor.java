package com.gildedgames.aether.mixin.mixins.accessor;

import com.mojang.realmsclient.gui.screens.RealmsPlayerScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RealmsPlayerScreen.class)
public interface RealmsPlayerScreenAccessor {
    @Accessor("OPTIONS_BACKGROUND")
    static ResourceLocation getOptionsBackground() {
        throw new AssertionError();
    }

    @Mutable
    @Accessor("OPTIONS_BACKGROUND")
    static void setOptionsBackground(ResourceLocation location) {
        throw new AssertionError();
    }
}