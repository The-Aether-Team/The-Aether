package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.WorldDisplayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {
    /**
     * Used by the world preview system.<br>
     * Unloads the currently loaded world preview level if a new level is being created.
     * @param minecraft The {@link Minecraft} instance.
     * @param screen The last {@link Screen}.
     * @param ci The {@link CallbackInfo} for the void method return.
     * @see WorldDisplayHelper#isActive()
     * @see WorldDisplayHelper#stopLevel(Screen)
     */
    @Inject(at = @At(value = "HEAD"), method = "openFresh(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/screens/Screen;)V")
    private static void openFresh(Minecraft minecraft, Screen screen, CallbackInfo ci) {
        if (WorldDisplayHelper.isActive()) {
            WorldDisplayHelper.stopLevel(new GenericDirtMessageScreen(Component.literal("")));
        }
    }
}
