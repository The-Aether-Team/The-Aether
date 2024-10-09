package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.WorldDisplayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.GenericMessageScreen;
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
     * Used by the world preview system.<br>
     * Prevents a deadlock when connecting to servers with the world preview.
     *
     * @param screen        The parent {@link Screen}.
     * @param minecraft     The {@link Minecraft} instance.
     * @param serverAddress The {@link ServerAddress} of the server being connected to.
     * @param serverData    The {@link ServerData} of the server being connected to.
     * @param isQuickPlay   A {@link Boolean} for whether quick play is used from the launcher.
     * @param ci            The {@link CallbackInfo} for the void method return.
     * @see WorldDisplayHelper#isActive()
     * @see WorldDisplayHelper#stopLevel(Screen)
     */
    @Inject(at = @At(value = "HEAD"), method = "startConnecting(Lnet/minecraft/client/gui/screens/Screen;Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/multiplayer/resolver/ServerAddress;Lnet/minecraft/client/multiplayer/ServerData;Z)V")
    private static void startConnecting(Screen screen, Minecraft minecraft, ServerAddress serverAddress, ServerData serverData, boolean isQuickPlay, CallbackInfo ci) {
        if (WorldDisplayHelper.isActive()) {
            WorldDisplayHelper.stopLevel(new GenericMessageScreen(Component.literal("")));
        }
    }
}
