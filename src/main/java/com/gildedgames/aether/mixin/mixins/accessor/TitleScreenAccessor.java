package com.gildedgames.aether.mixin.mixins.accessor;

import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TitleScreen.class)
public interface TitleScreenAccessor {
    @Accessor
    String getSplash();

    @Accessor
    void setSplash(String splash);

    @Accessor
    boolean getMinceraftEasterEgg();

    @Mutable
    @Accessor
    void setFading(boolean fading);

    @Accessor
    void setFadeInStart(long fadeInStart);
}