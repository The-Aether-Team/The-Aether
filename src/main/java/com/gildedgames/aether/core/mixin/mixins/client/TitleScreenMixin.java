package com.gildedgames.aether.core.mixin.mixins.client;

import com.gildedgames.aether.client.gui.screen.menu.AetherWorldDisplayHelper;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(at = @At(value = "HEAD"), method = "isPauseScreen", cancellable = true)
    public void isPauseScreen(CallbackInfoReturnable<Boolean> cir) {
        if (AetherWorldDisplayHelper.loadedSummary != null && AetherWorldDisplayHelper.loadedLevel != null) {
            cir.setReturnValue(true);
        }
    }
}
