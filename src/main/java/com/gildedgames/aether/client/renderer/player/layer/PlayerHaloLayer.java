package com.gildedgames.aether.client.renderer.player.layer;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.HaloModel;
import com.gildedgames.aether.capability.rankings.AetherRankings;
import com.gildedgames.aether.api.AetherPlayerRankings;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nonnull;

public class PlayerHaloLayer<T extends Player, M extends PlayerModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation PLAYER_HALO_LOCATION = new ResourceLocation(Aether.MODID, "textures/models/perks/halo.png");
    private static final ResourceLocation PLAYER_HALO_GRAYSCALE_LOCATION = new ResourceLocation(Aether.MODID, "textures/models/perks/halo_grayscale.png");
    private final HaloModel<Player> playerHalo;

    public PlayerHaloLayer(RenderLayerParent<T, M> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.playerHalo = new HaloModel<>(modelSet.bakeLayer(AetherModelLayers.PLAYER_HALO));
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, @Nonnull T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof AbstractClientPlayer abstractClientPlayer) {
            AetherRankings.get(abstractClientPlayer).ifPresent(aetherRankings -> {
                if (AetherPlayerRankings.hasHalo(abstractClientPlayer.getUUID()) && aetherRankings.isHaloEnabled() && !abstractClientPlayer.isInvisible()) {
                    this.playerHalo.halo.yRot = this.getParentModel().head.yRot;
                    this.playerHalo.halo.xRot = this.getParentModel().head.xRot;
                    this.playerHalo.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                    Triple<Float, Float, Float> color = aetherRankings.getHaloColor();
                    if (color != null) {
                        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(PLAYER_HALO_GRAYSCALE_LOCATION));
                        this.playerHalo.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, color.getLeft(), color.getMiddle(), color.getRight(), 1.0F);
                    } else {
                        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(PLAYER_HALO_LOCATION));
                        this.playerHalo.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                    }
                }
            });
        }
    }
}
