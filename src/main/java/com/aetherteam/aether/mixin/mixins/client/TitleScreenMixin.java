package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.WorldDisplayHelper;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    /**
     * Used by the world preview system.<br>
     * Sets the {@link TitleScreen} to pause the game when the world preview is active.
     *
     * @param original The original state of whether this is a pause screen.
     * @return Whether it was a pause screen before (used in case a mod changes it, as it otherwise is false),
     * or if the world preview is active.
     * @see WorldDisplayHelper#isActive()
     */
    @ModifyReturnValue(at = @At(value = "RETURN"), method = "isPauseScreen()Z")
    public boolean isPauseScreen(boolean original) {
        return original || WorldDisplayHelper.isActive();
    }
}
