package com.aetherteam.aether.mixin.mixins.client.fabric;

import com.aetherteam.aether.client.event.listeners.MenuListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "setScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;added()V"))
    private void openMenu(Screen guiScreen, CallbackInfo ci) {
        MenuListener.onGuiOpenHighest();
    }
}
