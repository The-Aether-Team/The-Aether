package com.aetherteam.aether.mixin.mixins.client.accessor;

import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TitleScreen.class)
public interface TitleScreenAccessor {
    @Accessor("splash")
    String aether$getSplash();

    @Accessor("splash")
    void aether$setSplash(String splash);

    @Accessor("logoRenderer")
    LogoRenderer aether$getLogoRenderer();

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
}