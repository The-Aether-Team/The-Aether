package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.WorldDisplayHelper;
import com.mojang.serialization.Dynamic;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldOpenFlows.class)
public class WorldOpenFlowsMixin {
    /**
     * Used by the world preview system.<br>
     * Always makes sure the experimental warnings screen is skipped if the world preview is active,
     * since a previewed world should already always have had the confirmation on this screen triggered.<br>
     * This is needed to get around a bug with newly created worlds not working for the world preview when the game is closed and reopened.<br><br>
     * Modifies the <code>confirmExperimentalWarning</code> parameter.
     *
     * @param confirmExperimentalWarning The original {@link Boolean} value for whether to skip the experimental warnings screen.
     * @return The new {@link Boolean} value.
     * @see WorldDisplayHelper#isActive()
     */
    @ModifyVariable(method = "loadLevel(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lcom/mojang/serialization/Dynamic;ZZLjava/lang/Runnable;Z)V", at = @At("HEAD"), ordinal = 2, argsOnly = true, remap = false)
    private boolean confirmExperimentalWarning(boolean confirmExperimentalWarning) {
        if (WorldDisplayHelper.isActive()) {
            return true;
        } else {
            return confirmExperimentalWarning;
        }
    }

    /**
     * Used by the world preview system.<br>
     * Unloads the currently loaded world preview level if another level is being loaded.
     *
     * @param ci The {@link CallbackInfo} for the void method return.
     * @see WorldDisplayHelper#isActive()
     * @see WorldDisplayHelper#sameSummaries(LevelSummary)
     * @see WorldDisplayHelper#stopLevel(Screen)
     */
    @Inject(method = "loadLevel(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lcom/mojang/serialization/Dynamic;ZZLjava/lang/Runnable;Z)V", at = @At("HEAD"))
    private void closeActiveWorld(LevelStorageSource.LevelStorageAccess access, Dynamic<?> dynamic, boolean safeMode, boolean checkForBackup, Runnable runnable, boolean confirmExperimentalWarning, CallbackInfo ci) {
        if (WorldDisplayHelper.isActive() && !WorldDisplayHelper.sameSummaries(access.getSummary(dynamic))) {
            WorldDisplayHelper.stopLevel(new GenericDirtMessageScreen(Component.literal("")));
        }
    }
}
