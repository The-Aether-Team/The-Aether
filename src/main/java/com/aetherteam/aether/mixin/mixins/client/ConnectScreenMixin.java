package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.api.WorldDisplayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectScreen.class)
public class ConnectScreenMixin {
    /**
     * Prevents a deadlock when connecting to servers with the world preview.
     * @see net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedOutEvent for a possible alternative method.
     */
    @Inject(at = @At(value = "HEAD"), method = "startConnecting")
    private static void startConnecting(Screen screen, Minecraft minecraft, ServerAddress serverAddress, ServerData serverData, CallbackInfo ci) {
        if (WorldDisplayHelper.loadedLevel != null && WorldDisplayHelper.loadedSummary != null) {
            WorldDisplayHelper.stopWorld(Minecraft.getInstance(), new GenericDirtMessageScreen(Component.literal("")));
        }
    }
}
