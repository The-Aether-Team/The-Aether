package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.model.ValkyrieModel;
import com.gildedgames.aether.client.renderer.entity.model.ValkyrieWingsModel;
import com.gildedgames.aether.common.entity.monster.dungeon.Valkyrie;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;

public class ValkyrieWingsLayer extends RenderLayer<Valkyrie, ValkyrieModel> {
    private static final ResourceLocation VALKYRIE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/valkyrie/valkyrie.png");
    private final ValkyrieWingsModel<Valkyrie> wings;

    public ValkyrieWingsLayer(RenderLayerParent<Valkyrie, ValkyrieModel> entityRenderer, ValkyrieWingsModel<Valkyrie> wingsModel) {
        super(entityRenderer);
        this.wings = wingsModel;
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, @Nonnull Valkyrie valkyrie, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        this.setupWingRotation(valkyrie, ageInTicks);
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(VALKYRIE_TEXTURE));
        this.wings.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void setupWingRotation(@Nonnull Valkyrie entity, float ticks) {
        float sinage = this.handleWingSinage(entity, ticks);
        float targetYRot = Mth.sin(sinage) / 6.0F - 0.2F;
        float targetZRot = Mth.cos(sinage) / (entity.isEntityOnGround() ? 8.0F : 3.0F) - 0.125F;
        this.wings.leftWing.yRot = targetYRot;
        this.wings.leftWing.zRot = targetZRot;
        this.wings.rightWing.yRot = -targetYRot;
        this.wings.rightWing.zRot = -targetZRot;
    }

    /**
     * Sets the position of the wings for rendering.
     */
    private float handleWingSinage(@Nonnull Valkyrie entity, float sinage) {
        if (!entity.isEntityOnGround()) {
            sinage *= 0.75F;
        } else {
            sinage *= 0.15F;
        }
        return sinage;
    }
}
