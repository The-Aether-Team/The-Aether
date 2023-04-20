package com.aetherteam.aether.mixin.mixins.client.accessor;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiComponent.class)
public interface GuiComponentAccessor {
    @Mutable
    @Accessor("BACKGROUND_LOCATION")
    static void aether$setBackgroundLocation(ResourceLocation location) {
        throw new AssertionError();
    }

    @Mutable
    @Accessor("LIGHT_DIRT_BACKGROUND")
    static void aether$setLightDirtBackground(ResourceLocation location) {
        throw new AssertionError();
    }
}