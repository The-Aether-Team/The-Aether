package com.gildedgames.aether.core.mixin.mixins.client;

import com.gildedgames.aether.client.gui.screen.menu.AetherWorldDisplayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldSelectionList.WorldListEntry.class)
public class WorldListEntryMixin {

    @Shadow
    private LevelSummary summary;

    @Inject(at=@At(value = "HEAD"), method="doDeleteWorld", cancellable = true)
    public void doDeleteWorld(CallbackInfo info) {
        if (AetherWorldDisplayHelper.loadedLevel != null) {
            AetherWorldDisplayHelper.fixWorld();
            var server = Minecraft.getInstance().getSingleplayerServer();
            if (server != null) {
                server.halt(false);
            }
            Minecraft.getInstance().clearLevel(Minecraft.getInstance().screen);
        }
    }
}
