package com.aetherteam.aether.mixin.mixins.client.accessor;

import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TitleScreen.class)
public interface TitleScreenAccessor {
    @Accessor("splash")
    SplashRenderer aether$getSplash();

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

    @Accessor
    TitleScreen.WarningLabel getWarningLabel();

    @Invoker
    Component callGetMultiplayerDisabledReason();
}