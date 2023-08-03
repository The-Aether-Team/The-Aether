package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.WorldDisplayHelper;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldOpenFlows.class)
public class WorldOpenFlowsMixin {
    @ModifyVariable(method = "doLoadLevel(Lnet/minecraft/client/gui/screens/Screen;Ljava/lang/String;ZZZ)V", at = @At("HEAD"), ordinal = 2, argsOnly = true, remap = false)
    private boolean confirmExperimentalWarning(boolean confirmExperimentalWarning) {
        if (WorldDisplayHelper.isActive()) {
            return true;
        } else {
            return confirmExperimentalWarning;
        }
    }
}
