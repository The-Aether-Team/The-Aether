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

    /**
     * Renders Valkyrie wings if the entity is not invisibility.
     *
     * @param poseStack       The rendering {@link PoseStack}.
     * @param buffer          The rendering {@link MultiBufferSource}.
     * @param packedLight     The {@link Integer} for the packed lighting for rendering.
     * @param valkyrie        The entity.
     * @param limbSwing       The {@link Float} for the limb swing rotation.
     * @param limbSwingAmount The {@link Float} for the limb swing amount.
     * @param partialTicks    The {@link Float} for the game's partial ticks.
     * @param ageInTicks      The {@link Float} for the entity's age in ticks.
     * @param netHeadYaw      The {@link Float} for the head yaw rotation.
     * @param headPitch       The {@link Float} for the head pitch rotation.
     */
    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T valkyrie, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        this.setupWingRotation(valkyrie, ageInTicks);
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(this.wingsLocation));
        if (!valkyrie.isInvisible()) {
            this.wings.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    /**
     * Sets the model rotation of the wings.
     *
     * @param entity The entity.
     * @param ticks  The {@link Float} for the entity's age in ticks.
     */
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
     * Handles the rotation value for the wings' rotation.
     *
     * @param entity The entity.
     * @param sinage The {@link Float} for the entity's age in ticks.
     * @return The modified {@link Float} value.
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
