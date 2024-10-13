package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ReceivingLevelScreen.class)
public class ReceivingLevelScreenMixin {
    @Unique
    private boolean isInAetherPortal;
    @Unique
    private float portalIntensity;
    @Unique
    private float oPortalIntensity;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(BooleanSupplier levelReceived, ReceivingLevelScreen.Reason reason, CallbackInfo ci) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.portalProcess != null && Minecraft.getInstance().player.portalProcess.isSamePortal(AetherBlocks.AETHER_PORTAL.get())) {
            var data = Minecraft.getInstance().player.getData(AetherDataAttachments.AETHER_PLAYER);
            this.isInAetherPortal = true;
            this.portalIntensity = data.getPortalIntensity();
            this.oPortalIntensity = data.getOldPortalIntensity();
        }
    }

    @Inject(method = "renderBackground(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At("HEAD"), cancellable = true)
    private void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (this.isInAetherPortal) {
            guiGraphics.blit(0, 0, -90, guiGraphics.guiWidth(), guiGraphics.guiHeight(), Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getParticleIcon(AetherBlocks.AETHER_PORTAL.get().defaultBlockState()));
            ci.cancel();
        }
    }

    @Inject(method = "onClose()V", at = @At("HEAD"), cancellable = true)
    private void onClose(CallbackInfo ci) {
        if (Minecraft.getInstance().player != null && this.isInAetherPortal) {
            var data = Minecraft.getInstance().player.getData(AetherDataAttachments.AETHER_PLAYER);
            data.portalIntensity = this.portalIntensity;
            data.oPortalIntensity = this.oPortalIntensity;
        }
    }
}
