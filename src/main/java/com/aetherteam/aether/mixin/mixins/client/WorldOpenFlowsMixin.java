package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.WorldDisplayHelper;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldOpenFlows.class)
public class WorldOpenFlowsMixin {
    /**
     * Used by the world preview system.<br>
     * Always makes sure the experimental warnings screen is skipped if the world preview is active,
     * since a previewed world should already always have had the confirmation on this screen triggered.<br>
     * This is needed to get around a bug with newly created worlds not working for the world preview when the game is closed and reopened.<br><br>
     * Modifies the <code>confirmExperimentalWarning</code> parameter.
     * @param confirmExperimentalWarning The original {@link Boolean} value for whether to skip the experimental warnings screen.
     * @return The new {@link Boolean} value.
     * @see WorldDisplayHelper#isActive()
     */
    @ModifyVariable(method = "doLoadLevel(Lnet/minecraft/client/gui/screens/Screen;Ljava/lang/String;ZZZ)V", at = @At("HEAD"), ordinal = 2, argsOnly = true, remap = false)
    private boolean confirmExperimentalWarning(boolean confirmExperimentalWarning) {
        if (WorldDisplayHelper.isActive()) {
            return true;
        } else {
            return confirmExperimentalWarning;
        }
    }
}
