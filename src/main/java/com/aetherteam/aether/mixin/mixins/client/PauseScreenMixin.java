package com.aetherteam.aether.mixin.mixins.client;

import net.minecraft.client.gui.screens.PauseScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PauseScreen.class)
public class PauseScreenMixin { //todo need to figure out how to mixin into the disconnect button lambda. examine bytecode
//    /**
//     * Used by the world preview system.<br>
//     * Stops the level disconnect behavior when the world preview is active, and instead
//     * sets the {@link net.minecraft.client.gui.screens.TitleScreen} back up so there is no load time to return to the menu.
//     * @param ci The {@link CallbackInfo} for the void method return.
//     * @see WorldDisplayHelper#setActive()
//     * @see WorldDisplayHelper#setupLevelForDisplay()
//     */
//    @Inject(at = @At(value = "HEAD"), method = "onDisconnect()V", cancellable = true)
//    public void onDisconnect(CallbackInfo ci) {
//        if (AetherConfig.CLIENT.enable_world_preview.get() && Minecraft.getInstance().getSingleplayerServer() != null) {
//            WorldDisplayHelper.setActive();
//            WorldDisplayHelper.setupLevelForDisplay();
//            Player player = Minecraft.getInstance().player;
//            if (player != null) {
//                player.setXRot(0);
//            }
//            ci.cancel();
//        }
//    }
}
