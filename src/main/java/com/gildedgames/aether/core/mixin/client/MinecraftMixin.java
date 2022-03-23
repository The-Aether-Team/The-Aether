package com.gildedgames.aether.core.mixin.client;

import com.gildedgames.aether.client.gui.screen.menu.AetherMainMenuScreen;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.util.LevelUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin
{
    @Inject(at = @At(value = "RETURN", ordinal = 2), method = "getSituationalMusic", cancellable = true)
    public void getSituationalMusic_Dimension(CallbackInfoReturnable<Music> cir) {
        Minecraft minecraft = (Minecraft) (Object) this;
        if (minecraft.player != null && minecraft.level != null && LevelUtil.shouldPlayAetherMusic(minecraft.player.level)) {
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
    @Inject(at = @At(value = "RETURN", ordinal = 4), method = "getSituationalMusic", cancellable = true)
    public void getSituationalMusic_Menu(CallbackInfoReturnable<Music> cir) {
        if (AetherConfig.CLIENT.enable_aether_menu.get() && !AetherConfig.CLIENT.disable_menu_music.get()) {
            cir.setReturnValue(AetherMainMenuScreen.MENU);
        }
    }
}
