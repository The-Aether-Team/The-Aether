package com.aetherteam.aether.mixin.mixins.client.accessor;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Button.Builder.class)
public interface ButtonBuilderAccessor {

    @Accessor("message") Component nitrogen_fabric$message();
    @Accessor("onPress") Button.OnPress nitrogen_fabric$onPress();
    @Accessor("tooltip") @Nullable Tooltip nitrogen_fabric$tooltip();
    @Accessor("x") int nitrogen_fabric$x();
    @Accessor("y") int nitrogen_fabric$y();
    @Accessor("width") int nitrogen_fabric$width();
    @Accessor("height") int nitrogen_fabric$height();
    @Accessor("createNarration") Button.CreateNarration nitrogen_fabric$createNarration();
}
