package com.gildedgames.aether.client.renderer.player.layer;

import com.gildedgames.aether.core.registry.AetherPlayerRankings;
import com.gildedgames.aether.core.util.SkinCustomizations;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;
import java.awt.*;

public class DeveloperGlowLayer<T extends Player, M extends PlayerModel<T>> extends RenderLayer<T, M> {
    public DeveloperGlowLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    public void render(@Nonnull PoseStack pMatrixStack, @Nonnull MultiBufferSource pBuffer, int pPackedLight, @Nonnull T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (pLivingEntity instanceof AbstractClientPlayer abstractClientPlayer) {
            if (AetherPlayerRankings.hasDevGlow(abstractClientPlayer.getUUID()) && SkinCustomizations.INSTANCE.isDeveloperGlowEnabled()) {
                VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.eyes(abstractClientPlayer.getSkinTextureLocation()));
                Color color = SkinCustomizations.INSTANCE.getDeveloperGlowColor();
                if (color != null) {
                    this.getParentModel().renderToBuffer(pMatrixStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, 255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue(), 1.0F);
                } else {
                    this.getParentModel().renderToBuffer(pMatrixStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }
}
