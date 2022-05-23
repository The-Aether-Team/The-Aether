package com.gildedgames.aether.core.mixin.mixins.client;

import com.gildedgames.aether.client.gui.screen.menu.AetherWorldDisplayHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {
    @Inject(at=@At(value="HEAD"), method="render", cancellable=true)
    public void render(PoseStack pPoseStack, int pTickCount, CallbackInfo info) {
        if (AetherWorldDisplayHelper.loadedLevel != null && Minecraft.getInstance().level != null) {
            info.cancel();
        }
    }
}
