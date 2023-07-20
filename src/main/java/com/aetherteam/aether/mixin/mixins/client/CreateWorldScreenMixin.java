package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.WorldDisplayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {
    @Inject(at = @At(value = "HEAD"), method = "openFresh")
    private static void openFresh(Minecraft minecraft, Screen screen, CallbackInfo ci) {
        if (WorldDisplayHelper.loadedLevel != null && WorldDisplayHelper.loadedSummary != null) {
            WorldDisplayHelper.stopWorld(Minecraft.getInstance(), new GenericDirtMessageScreen(Component.literal("")));
        }
    }
}
