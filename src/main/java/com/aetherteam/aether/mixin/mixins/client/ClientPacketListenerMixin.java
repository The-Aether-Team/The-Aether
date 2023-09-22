package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.AetherConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    /**
     * Sends the player straight back to the title screen if the server button is enabled.
     * @param reason The {@link Component} detailing the reason for disconnecting.
     * @param ci The {@link CallbackInfo} for the void method return.
     */
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/multiplayer/JoinMultiplayerScreen;<init>(Lnet/minecraft/client/gui/screens/Screen;)V"), method = "onDisconnect(Lnet/minecraft/network/chat/Component;)V", cancellable = true)
    public void onDisconnect(Component reason, CallbackInfo ci) {
        if (AetherConfig.CLIENT.enable_server_button.get()) {
            Minecraft.getInstance().setScreen(new DisconnectedScreen(new TitleScreen(), Component.translatable("disconnect.lost"), reason));
            ci.cancel();
        }
    }
}
