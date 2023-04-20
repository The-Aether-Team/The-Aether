package com.aetherteam.aether.mixin.mixins.client.accessor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Gui.class)
public interface GuiAccessor {
    @Accessor("random")
    RandomSource aether$getRandom();

    @Accessor("lastHealthTime")
    long aether$getLastHealthTime();

    @Accessor("healthBlinkTime")
    long aether$getHealthBlinkTime();

    @Invoker
    void callRenderHeart(PoseStack poseStack, Gui.HeartType heartType, int x, int y, int p_168705_, boolean p_168706_, boolean p_168707_);
}