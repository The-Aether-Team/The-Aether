package com.gildedgames.aether.core.mixin.mixins.client;

import com.gildedgames.aether.client.gui.screen.menu.AetherWorldDisplayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(at = @At(value = "HEAD"), method = "createLevel")
    public void createLevel(String levelName, LevelSettings levelSettings, RegistryAccess registryAccess, WorldGenSettings worldGenSettings, CallbackInfo ci) {
        if (AetherWorldDisplayHelper.loadedLevel != null && AetherWorldDisplayHelper.loadedSummary != null) {
            AetherWorldDisplayHelper.stopWorld(Minecraft.getInstance(), new GenericDirtMessageScreen(new TextComponent("")));
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "loadLevel")
    private void loadLevel(String levelName, CallbackInfo ci)  {
        if (AetherWorldDisplayHelper.loadedLevel != null && AetherWorldDisplayHelper.loadedSummary != null) {
            AetherWorldDisplayHelper.stopWorld(Minecraft.getInstance(), new GenericDirtMessageScreen(new TextComponent("")));
        }
    }
}
