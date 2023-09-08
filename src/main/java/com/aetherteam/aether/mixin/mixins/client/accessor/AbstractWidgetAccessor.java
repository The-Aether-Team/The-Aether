package com.aetherteam.aether.mixin.mixins.client.accessor;

import net.minecraft.client.gui.components.AbstractWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractWidget.class)
public interface AbstractWidgetAccessor {
    @Accessor("isHovered")
    boolean aether$isIsHovered();
}
