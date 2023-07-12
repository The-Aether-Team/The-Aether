package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.event.listeners.GuiListener;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BossHealthOverlay.class)
public class BossHealthOverlayMixin {
    /**
     * Ultimately cancels the {@link CustomizeGuiOverlayEvent.BossEventProgress} GUI event after all event listeners
     * have had a turn with it. Made as a workaround for Jade's boss bar pushdown.
     */
    @ModifyVariable(at = @At(value = "STORE"), method = "render", index = 7)
    private CustomizeGuiOverlayEvent.BossEventProgress render(CustomizeGuiOverlayEvent.BossEventProgress event) {
        event.setCanceled(GuiListener.BOSS_EVENTS.contains(event.getBossEvent().getId()));
        return event;
    }
}
