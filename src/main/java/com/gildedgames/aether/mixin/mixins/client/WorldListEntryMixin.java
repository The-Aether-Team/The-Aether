package com.gildedgames.aether.mixin.mixins.client;

import com.gildedgames.aether.client.gui.screen.menu.AetherWorldDisplayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldSelectionList.WorldListEntry.class)
public class WorldListEntryMixin {
    @Inject(at = @At(value = "HEAD"), method = "doDeleteWorld")
    public void doDeleteWorld(CallbackInfo info) {
        if (Minecraft.getInstance().screen != null && AetherWorldDisplayHelper.loadedLevel != null) {
            AetherWorldDisplayHelper.disableWorldPreview(null);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "loadWorld")
    private void doLoadLevel(CallbackInfo info) {
        if (AetherWorldDisplayHelper.loadedLevel != null && AetherWorldDisplayHelper.loadedSummary != null) {
            AetherWorldDisplayHelper.stopWorld(Minecraft.getInstance(), new GenericDirtMessageScreen(Component.literal("")));
        }
    }
}
