package com.gildedgames.aether.client.renderer.perks.layer;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.perks.model.PlayerHaloModel;
import com.gildedgames.aether.core.capability.interfaces.IAetherRankings;
import com.gildedgames.aether.core.registry.AetherPlayerRankings;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

public class PlayerHaloLayer<T extends Player, M extends PlayerModel<T>> extends RenderLayer<T, M>
{
    private static final ResourceLocation PLAYER_HALO_LOCATION = new ResourceLocation(Aether.MODID, "textures/models/perks/halo.png");
    private final PlayerHaloModel playerHaloModel;

    public PlayerHaloLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
        this.playerHaloModel = new PlayerHaloModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.PLAYER_HALO));
    }

    @Override
    public void render(@Nonnull PoseStack pMatrixStack, @Nonnull MultiBufferSource pBuffer, int pPackedLight, @Nonnull T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (pLivingEntity instanceof  AbstractClientPlayer abstractClientPlayer) {
            IAetherRankings.get(abstractClientPlayer).ifPresent(aetherRankings -> {
                if (AetherPlayerRankings.hasHalo(abstractClientPlayer.getUUID()) && aetherRankings.shouldRenderHalo()) {
                    this.playerHaloModel.main.yRot = this.getParentModel().head.yRot;
                    this.playerHaloModel.main.xRot = this.getParentModel().head.xRot;
                    this.playerHaloModel.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                    VertexConsumer vertexConsumer = pBuffer.getBuffer(RenderType.entityTranslucent(PLAYER_HALO_LOCATION));
                    this.playerHaloModel.renderToBuffer(pMatrixStack, vertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                }
            });
        }
    }
}
