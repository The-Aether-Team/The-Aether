package com.aetherteam.aether.mixin.mixins.client.accessor;

import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.TitleScreenModUpdateIndicator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TitleScreen.class)
public interface TitleScreenAccessor {
    @Accessor("splash")
    SplashRenderer aether$getSplash();

    @Mutable
    @Accessor("panorama")
    void aether$setPanorama(PanoramaRenderer splash);

    @Accessor("splash")
    void aether$setSplash(SplashRenderer splash);

    @Accessor("fading")
    boolean aether$isFading();

    @Mutable
    @Accessor("fading")
    void aether$setFading(boolean fading);

    @Accessor("fadeInStart")
    long aether$getFadeInStart();

    @Accessor("fadeInStart")
    void aether$setFadeInStart(long fadeInStart);

    @Accessor("logoRenderer")
    LogoRenderer aether$getLogoRenderer();

    @Mutable
    @Accessor("logoRenderer")
    void aether$setLogoRenderer(LogoRenderer splash);

    @Accessor(value = "modUpdateNotification", remap = false)
    TitleScreenModUpdateIndicator aether$getModUpdateNotification();

    @Accessor(value = "modUpdateNotification", remap = false)
    void aether$setModUpdateNotification(TitleScreenModUpdateIndicator widget);

    @Accessor
    TitleScreen.WarningLabel getWarningLabel();

    @Invoker
    Component callGetMultiplayerDisabledReason();
}