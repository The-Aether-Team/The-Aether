package com.aetherteam.aether.mixin.mixins.client.optifine;

import com.aetherteam.aether.client.event.hooks.GuiHooks;
import com.aetherteam.aether.entity.AetherBossMob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BossHealthOverlay.class)
public class BossHealthOverlayMixin {
    /**
     * Cancels the {@link CustomizeGuiOverlayEvent.BossEventProgress} GUI event after the event hook has been called for it.
     * Made as a workaround for Jade's boss bar pushdown.<br>
     * This modifies the assignment of the {@link CustomizeGuiOverlayEvent.BossEventProgress} event variable.
     * @param event The original {@link CustomizeGuiOverlayEvent.BossEventProgress} parameter value.
     * @return The modified {@link CustomizeGuiOverlayEvent.BossEventProgress} parameter value.
     */
    @ModifyVariable(at = @At(value = "STORE"), method = "render(Lnet/minecraft/client/gui/GuiGraphics;)V", index = 9)
    @SuppressWarnings({"MixinAnnotationTarget", "InvalidInjectorMethodSignature"})
    private Object event(Object event) {
        CustomizeGuiOverlayEvent.BossEventProgress e = (CustomizeGuiOverlayEvent.BossEventProgress) event;
        if (Minecraft.getInstance().level != null &&
                GuiHooks.BOSS_EVENTS.containsKey(e.getBossEvent().getId()) &&
                Minecraft.getInstance().level.getEntity(GuiHooks.BOSS_EVENTS.get(e.getBossEvent().getId())) instanceof AetherBossMob<?>) {
            e.setCanceled(true);
        }
        return event;
    }
}
