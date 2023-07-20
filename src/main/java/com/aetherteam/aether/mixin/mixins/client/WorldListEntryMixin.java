package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.WorldDisplayHelper;
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
        if (Minecraft.getInstance().screen != null && WorldDisplayHelper.loadedLevel != null) {
            WorldDisplayHelper.disableWorldPreview(null);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "loadWorld")
    private void doLoadLevel(CallbackInfo info) {
        if (WorldDisplayHelper.loadedLevel != null && WorldDisplayHelper.loadedSummary != null) {
            WorldDisplayHelper.stopWorld(Minecraft.getInstance(), new GenericDirtMessageScreen(Component.literal("")));
        }
    }
}
