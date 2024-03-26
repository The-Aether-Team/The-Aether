package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.WorldDisplayHelper;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    /**
     * Used by the world preview system.<br>
     * Sets the {@link TitleScreen} to pause the game when the world preview is active.
     *
     * @param cir The {@link Boolean} {@link CallbackInfoReturnable} used for the method's return value.
     * @see WorldDisplayHelper#isActive()
     */
    @Inject(at = @At(value = "HEAD"), method = "isPauseScreen()Z", cancellable = true)
    public void isPauseScreen(CallbackInfoReturnable<Boolean> cir) {
        if (WorldDisplayHelper.isActive()) {
            cir.setReturnValue(true);
        }
    }
}
