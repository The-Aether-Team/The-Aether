package com.gildedgames.aether.core.mixin.mixins.client;

import com.gildedgames.aether.client.gui.screen.menu.AetherTitleScreen;
import com.gildedgames.aether.client.gui.screen.menu.AetherWorldDisplayHelper;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.util.LevelUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(at = @At(value = "RETURN", ordinal = 2), method = "getSituationalMusic", cancellable = true)
    public void getSituationalMusic_Dimension(CallbackInfoReturnable<Music> cir) {
        Minecraft minecraft = (Minecraft) (Object) this;
        if (minecraft.player != null && minecraft.level != null && LevelUtil.inTag(minecraft.level, AetherTags.Dimensions.AETHER_MUSIC)) {
            cir.setReturnValue(minecraft.player.level.getBiome(minecraft.player.blockPosition()).value().getBackgroundMusic().orElse(Musics.GAME));
        }
    }

    @Inject(at = @At(value = "RETURN", ordinal = 4), method = "getSituationalMusic", cancellable = true)
    public void getSituationalMusic_AetherMenu(CallbackInfoReturnable<Music> cir) {
        if (AetherConfig.CLIENT.enable_aether_menu.get() && !AetherConfig.CLIENT.disable_aether_menu_music.get()) {
            cir.setReturnValue(AetherTitleScreen.MENU);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "getSituationalMusic", cancellable = true)
    public void getSituationalMusic_WorldPreviewMenu(CallbackInfoReturnable<Music> cir) {
        Minecraft minecraft = Minecraft.getInstance();
        if (AetherConfig.CLIENT.enable_world_preview.get() && minecraft.player != null && AetherWorldDisplayHelper.loadedLevel != null && AetherWorldDisplayHelper.loadedSummary != null) {
            if (minecraft.screen instanceof AetherTitleScreen && AetherConfig.CLIENT.enable_aether_menu.get()) {
                if (!AetherConfig.CLIENT.disable_aether_world_preview_menu_music.get()) {
                    cir.setReturnValue(AetherTitleScreen.MENU);
                }
            } else if (minecraft.screen instanceof TitleScreen) {
                if (!AetherConfig.CLIENT.disable_vanilla_world_preview_menu_music.get()) {
                    cir.setReturnValue(Musics.MENU);
                }
            }
        }
    }

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
