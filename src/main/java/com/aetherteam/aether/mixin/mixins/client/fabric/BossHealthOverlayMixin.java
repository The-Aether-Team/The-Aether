package com.aetherteam.aether.mixin.mixins.client.fabric;

import com.aetherteam.aether.client.event.hooks.GuiHooks;
import com.aetherteam.aether.client.event.listeners.GuiListener;
import com.aetherteam.aether.entity.AetherBossMob;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BossHealthOverlay.class)
public class BossHealthOverlayMixin {
    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/BossHealthOverlay;drawBar(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/world/BossEvent;)V"))
    private boolean renderCustomBossbar(BossHealthOverlay instance, GuiGraphics guiGraphics, int x, int y, BossEvent bossEvent, @Share("cancel") LocalBooleanRef cancel, @Share("increment") LocalIntRef increment) {
        increment.set(GuiListener.onRenderBossBar(guiGraphics, x, y, bossEvent, Minecraft.getInstance().font.lineHeight));
        cancel.set(Minecraft.getInstance().level != null &&
                GuiHooks.isAetherBossBar(bossEvent.getId()) &&
                Minecraft.getInstance().level.getEntity(GuiHooks.BOSS_EVENTS.get(bossEvent.getId())) instanceof AetherBossMob<?>);
        return !cancel.get();
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)I"))
    private boolean renderCustomBossbar(GuiGraphics instance, Font font, Component text, int x, int y, int color, @Share("cancel") LocalBooleanRef cancel) {
        return !cancel.get();
    }

    @ModifyExpressionValue(method = "render", at = @At(value = "CONSTANT", args = "intValue=9", ordinal = 1))
    private int modifyIncrement(int original, @Share("cancel") LocalBooleanRef cancel, @Share("increment") LocalIntRef increment) {
        return cancel.get() ? increment.get() : original;
    }
}
