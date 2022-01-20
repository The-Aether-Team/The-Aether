package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.perks.model.HaloModel;
import com.gildedgames.aether.common.entity.passive.Phyg;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class PhygHaloLayer extends RenderLayer<Phyg, PigModel<Phyg>>
{
    private static final ResourceLocation HALO_LOCATION = new ResourceLocation(Aether.MODID, "textures/models/perks/halo.png");
    private final HaloModel<Phyg> halo;

    public PhygHaloLayer(RenderLayerParent<Phyg, PigModel<Phyg>> entityRendererIn, EntityModelSet modelSet) {
        super(entityRendererIn);
        this.halo = new HaloModel<>(modelSet.bakeLayer(AetherModelLayers.PHYG_HALO));
    }

    @Override
    public void render(@Nonnull PoseStack pMatrixStack, @Nonnull MultiBufferSource pBuffer, int pPackedLight, Phyg phyg, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (phyg.hasCustomName() && phyg.getCustomName() != null && phyg.getCustomName().getContents().equals("KingPhygieBoo")) {
            this.halo.main.yRot = this.getParentModel().head.yRot;
            this.halo.main.xRot = this.getParentModel().head.xRot;
            this.halo.setupAnim(phyg, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
            VertexConsumer vertexConsumer = pBuffer.getBuffer(RenderType.eyes(HALO_LOCATION));
            this.halo.renderToBuffer(pMatrixStack, vertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.25F);
        }
    }
}
