package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.event.hooks.GuiHooks;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BossHealthOverlay.class)
public class BossHealthOverlayMixin {
    /**
     * Cancels the {@link CustomizeGuiOverlayEvent.BossEventProgress} GUI event after the event hook has been called for it.
     * Made as a workaround for Jade's boss bar pushdown.<br>
     * This modifies the assignment of the {@link CustomizeGuiOverlayEvent.BossEventProgress} event variable.
     * @param event The original {@link net.minecraftforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress} parameter value.
     * @return The modified {@link net.minecraftforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress} parameter value.
     */
    @ModifyVariable(at = @At(value = "STORE"), method = "render(Lnet/minecraft/client/gui/GuiGraphics;)V", index = 7)
    private CustomizeGuiOverlayEvent.BossEventProgress event(CustomizeGuiOverlayEvent.BossEventProgress event) {
        event.setCanceled(GuiHooks.BOSS_EVENTS.contains(event.getBossEvent().getId()));
        return event;
    }
}
