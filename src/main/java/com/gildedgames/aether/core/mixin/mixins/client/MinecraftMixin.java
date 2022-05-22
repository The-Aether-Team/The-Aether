package com.gildedgames.aether.core.mixin.mixins.client;

import com.gildedgames.aether.client.event.hooks.GuiHooks;
import com.gildedgames.aether.client.gui.screen.menu.AetherTitleScreen;
import com.gildedgames.aether.client.gui.screen.menu.AetherWorldDisplayHelper;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.util.LevelUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.server.WorldStem;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(Minecraft.class)
public class MinecraftMixin
{
    @Inject(at = @At(value = "RETURN", ordinal = 2), method = "getSituationalMusic", cancellable = true)
    public void getSituationalMusic_Dimension(CallbackInfoReturnable<Music> cir) {
        Minecraft minecraft = (Minecraft) (Object) this;
        if (minecraft.player != null && minecraft.level != null && LevelUtil.inTag(minecraft.level, AetherTags.Dimensions.AETHER_MUSIC)) {
            cir.setReturnValue(minecraft.player.level.getBiome(minecraft.player.blockPosition()).value().getBackgroundMusic().orElse(Musics.GAME));
        }
    }

    /**
     * {@link Minecraft#getSituationalMusic()}
     * Injector mixin to make sure the game recognizes the Aether menu and doesn't try to interrupt with its own music,
     * while also making sure that the game cuts off the Aether menu music as soon as necessary. This code is
     * injected right before the end of the method where the main menu music is returned, so all it needs to check is
     * the config.
     */
    @Inject(at = @At(value = "RETURN"), method = "getSituationalMusic", cancellable = true)
    public void getSituationalMusic_Menu(CallbackInfoReturnable<Music> cir) {
        if (AetherConfig.CLIENT.enable_aether_menu.get() && !AetherConfig.CLIENT.disable_menu_music.get()) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.screen instanceof AetherTitleScreen) {
                cir.setReturnValue(AetherTitleScreen.MENU);
            }
        }
    }

    @Inject(at=@At(value = "HEAD"), method = "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V")
    public void clearLevel(Screen pScreen, CallbackInfo info) {
        if (!AetherWorldDisplayHelper.loadingLevel) {
            AetherWorldDisplayHelper.loadingLevel = false;
            AetherWorldDisplayHelper.loadedLevel = null;
            AetherWorldDisplayHelper.loadedSummary = null;
        }
    }

    @Inject(at=@At(value = "HEAD"), method = "loadLevel", cancellable = true)
    private void loadLevel(String pLevelName, CallbackInfo info)  {
        if (AetherWorldDisplayHelper.loadedSummary != null)
        if (AetherWorldDisplayHelper.loadedLevel != null && pLevelName.equals(AetherWorldDisplayHelper.loadedSummary.getLevelId())) {
            try {
                AetherWorldDisplayHelper.fixWorld();
                Minecraft minecraft =  Minecraft.getInstance();
                minecraft.getSingleplayerServer().halt(false);
                //minecraft.getSingleplayerServer().stopServer();
                //while (!Minecraft.getInstance().getSingleplayerServer().isStopped()) {

                //}
            } catch (Exception e) {

            }
            Minecraft.getInstance().level = null;
        }
    }

    @Inject(at=@At(value="HEAD"), method="stop")
    public void stop(CallbackInfo info) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null) {
            minecraft.getSingleplayerServer().halt(false);
            minecraft.level = null;
        }
    }
}
