package com.aetherteam.aether.mixin.mixins.client.fabric;

import com.aetherteam.aether.client.event.listeners.WorldPreviewListener;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderWithTooltip(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"))
    private void shouldCancel(Screen instance, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, Operation<Void> original) {
        if (!WorldPreviewListener.onScreenRender(instance))
            original.call(instance, guiGraphics, mouseX, mouseY, partialTick);
    }
}
