package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.WorldDisplayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldSelectionList.WorldListEntry.class)
public class WorldListEntryMixin {
    @Shadow
    @Final
    private LevelSummary summary;

    @Inject(at = @At(value = "HEAD"), method = "doDeleteWorld")
    public void doDeleteWorld(CallbackInfo ci) {
        if (WorldDisplayHelper.isActive() && WorldDisplayHelper.sameSummaries(this.summary)) {
            WorldDisplayHelper.stopLevel(Minecraft.getInstance(), null);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "joinWorld", cancellable = true)
    public void joinWorld(CallbackInfo ci) {
        if (WorldDisplayHelper.isActive() && WorldDisplayHelper.sameSummaries(this.summary)) {
            WorldDisplayHelper.enterLoadedLevel();
            ci.cancel();
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "loadWorld")
    private void loadWorld(CallbackInfo ci) {
        if (WorldDisplayHelper.isActive() && !WorldDisplayHelper.sameSummaries(this.summary)) {
            //Aether.LOGGER.info("true");
            WorldDisplayHelper.stopLevel(Minecraft.getInstance(), new GenericDirtMessageScreen(Component.literal("")));
        }
    }
}
