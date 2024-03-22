package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.WorldDisplayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public class PauseScreenMixin {
    /**
     * Used by the world preview system.<br>
     * Stops the level disconnect behavior when the world preview is active, and instead
     * sets the {@link net.minecraft.client.gui.screens.TitleScreen} back up so there is no load time to return to the menu.
     *
     * @param ci The {@link CallbackInfo} for the void method return.
     * @see WorldDisplayHelper#setActive()
     * @see WorldDisplayHelper#setupLevelForDisplay()
     */
    @Inject(at = @At(value = "HEAD"), method = "onDisconnect()V", cancellable = true)
    public void onDisconnectWorldPreview(CallbackInfo ci) {
        if (AetherConfig.CLIENT.enable_world_preview.get() && Minecraft.getInstance().getSingleplayerServer() != null) {
            WorldDisplayHelper.setActive();
            WorldDisplayHelper.setupLevelForDisplay();
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                player.setXRot(0);
            }
            ci.cancel();
        }
    }

    /**
     * Sends the player straight back to the title screen if the server button is enabled.
     *
     * @param ci The {@link CallbackInfo} for the void method return.
     * @see WorldDisplayHelper#setActive()
     * @see WorldDisplayHelper#setupLevelForDisplay()
     */
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/multiplayer/JoinMultiplayerScreen;<init>(Lnet/minecraft/client/gui/screens/Screen;)V"), method = "onDisconnect()V", cancellable = true)
    public void onDisconnectServerButton(CallbackInfo ci) {
        if (AetherConfig.CLIENT.enable_server_button.get()) {
            Minecraft.getInstance().setScreen(new TitleScreen());
            ci.cancel();
        }
    }
}
