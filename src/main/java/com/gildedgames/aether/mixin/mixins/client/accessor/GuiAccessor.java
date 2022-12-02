package com.gildedgames.aether.mixin.mixins.client.accessor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Gui.class)
public interface GuiAccessor {
    @Accessor
    long getLastHealthTime();

    @Accessor
    long getHealthBlinkTime();

    @Invoker
    void callRenderHeart(PoseStack poseStack, Gui.HeartType heartType, int x, int y, int p_168705_, boolean p_168706_, boolean p_168707_);
}