package com.gildedgames.aether.mixin.mixins.accessor;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiComponent.class)
public interface GuiComponentAccessor {
    @Accessor("BACKGROUND_LOCATION")
    static void setBackgroundLocation(ResourceLocation location) {
        throw new AssertionError();
    }
}
