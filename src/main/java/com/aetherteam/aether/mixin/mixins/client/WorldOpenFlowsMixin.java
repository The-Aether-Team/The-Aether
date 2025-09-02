package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.WorldDisplayHelper;
import com.aetherteam.aether.mixin.mixins.common.accessor.MinecraftServerAccessor;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Dynamic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(WorldOpenFlows.class)
public class WorldOpenFlowsMixin {
    @Inject(method = "loadLevel(Lnet/minecraft/client/gui/screens/Screen;Ljava/lang/String;)V", at = @At(value = "HEAD"), cancellable = true)
    private void openWorld(Screen lastScreen, String levelName, CallbackInfo ci) {
        if (WorldDisplayHelper.isActive() && Minecraft.getInstance().hasSingleplayerServer() && ((MinecraftServerAccessor) Minecraft.getInstance().getSingleplayerServer()).aether$getStorageSource().getLevelId().equals(levelName)) {
            WorldDisplayHelper.enterLoadedLevel();
            ci.cancel();
        }
    }

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

    @Inject(method = "doLoadLevel(Lnet/minecraft/client/gui/screens/Screen;Ljava/lang/String;ZZZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/ServerPacksSource;createPackRepository(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;)Lnet/minecraft/server/packs/repository/PackRepository;", shift = At.Shift.BEFORE, remap = true), remap = false)
    private void doLoadLevel(Screen lastScreen, String levelName, boolean safeMode, boolean checkAskForBackup, boolean confirmExperimentalWarning, CallbackInfo ci, @Local LevelStorageSource.LevelStorageAccess levelStorage) {
        if (WorldDisplayHelper.isActive() && !WorldDisplayHelper.sameSummaries(levelStorage.getSummary())) {
            WorldDisplayHelper.stopLevel(new GenericDirtMessageScreen(Component.translatable("menu.savingLevel")));
            WorldDisplayHelper.resetSummary();
        }
    }
}
