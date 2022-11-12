package com.gildedgames.aether.mixin.mixins.accessor;

import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TitleScreen.class)
public interface TitleScreenAccessor {
    @Accessor
    String getSplash();

    @Accessor("splash")
    void setSplash(String splash);

    @Accessor
    boolean getMinceraftEasterEgg();

    @Accessor("fading")
    void setFading(boolean fading);

    @Accessor("fadeInStart")
    void setFadeInStart(long fadeInStart);
}
