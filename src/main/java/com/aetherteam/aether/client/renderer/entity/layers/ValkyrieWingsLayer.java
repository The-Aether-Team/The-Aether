package com.aetherteam.aether.client.renderer.entity.layers;

import com.aetherteam.aether.client.renderer.entity.model.ValkyrieModel;
import com.aetherteam.aether.client.renderer.entity.model.ValkyrieWingsModel;
import com.aetherteam.aether.entity.monster.dungeon.AbstractValkyrie;
import com.aetherteam.aether.entity.monster.dungeon.Valkyrie;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ValkyrieWingsLayer<T extends AbstractValkyrie> extends RenderLayer<T, ValkyrieModel<T>> {
    private final ResourceLocation wingsLocation;
    private final ValkyrieWingsModel<Valkyrie> wings;

    public ValkyrieWingsLayer(RenderLayerParent<T, ValkyrieModel<T>> entityRenderer, ResourceLocation wingsLocation, ValkyrieWingsModel<Valkyrie> wingsModel) {
        super(entityRenderer);
        this.wingsLocation = wingsLocation;
        this.wings = wingsModel;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T valkyrie, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        this.setupWingRotation(valkyrie, ageInTicks);
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(this.wingsLocation));
        if (!valkyrie.isInvisible()) {
            this.wings.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public void setupWingRotation(T entity, float ticks) {
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
    private float handleWingSinage(T entity, float sinage) {
        if (!entity.isEntityOnGround()) {
            sinage *= 0.75F;
        } else {
            sinage *= 0.15F;
        }
        return sinage;
    }
}
