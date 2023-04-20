package com.aetherteam.aether.mixin.mixins.client.accessor;

import com.mojang.realmsclient.gui.screens.RealmsPlayerScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RealmsPlayerScreen.class)
public interface RealmsPlayerScreenAccessor {
    @Accessor("OPTIONS_BACKGROUND")
    static ResourceLocation aether$getOptionsBackground() {
        throw new AssertionError();
    }

    @Mutable
    @Accessor("OPTIONS_BACKGROUND")
    static void aether$setOptionsBackground(ResourceLocation location) {
        throw new AssertionError();
    }
}