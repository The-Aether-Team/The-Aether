package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.gui.screen.menu.CustomPosition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.SplashRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SplashRenderer.class)
public class SplashRendererMixin {
    @WrapOperation(method = "render(Lnet/minecraft/client/gui/GuiGraphics;ILnet/minecraft/client/gui/Font;I)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    private void render(PoseStack instance, float x, float y, float z, Operation<Void> original) {
        SplashRenderer renderer = (SplashRenderer) (Object) this;
        if (renderer instanceof CustomPosition customPosition) {
            original.call(instance, customPosition.getXOffset(x), customPosition.getYOffset(y), z);
        } else {
            original.call(instance, x, y, z);
        }
    }
}
