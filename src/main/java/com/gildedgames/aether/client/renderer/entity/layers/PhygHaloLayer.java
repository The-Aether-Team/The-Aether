package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.model.HaloModel;
import com.gildedgames.aether.entity.passive.Phyg;
import com.gildedgames.aether.mixin.mixins.client.accessor.QuadrupedModelAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class PhygHaloLayer extends RenderLayer<Phyg, PigModel<Phyg>> {
    private static final ResourceLocation HALO_LOCATION = new ResourceLocation(Aether.MODID, "textures/models/perks/halo.png");
    private final HaloModel<Phyg> phygHalo;

    public PhygHaloLayer(RenderLayerParent<Phyg, PigModel<Phyg>> entityRenderer, HaloModel<Phyg> haloModel) {
        super(entityRenderer);
        this.phygHalo = haloModel;
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, Phyg phyg, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (phyg.hasCustomName() && phyg.getCustomName() != null && phyg.getCustomName().getContents().equals("KingPhygieBoo")) {
            QuadrupedModelAccessor quadrupedModelAccessor = (QuadrupedModelAccessor) this.getParentModel();
            this.phygHalo.halo.yRot = quadrupedModelAccessor.getHead().yRot;
            this.phygHalo.halo.xRot = quadrupedModelAccessor.getHead().xRot;
            this.phygHalo.setupAnim(phyg, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer consumer = buffer.getBuffer(RenderType.eyes(HALO_LOCATION));
            this.phygHalo.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.25F);
        }
    }
}
