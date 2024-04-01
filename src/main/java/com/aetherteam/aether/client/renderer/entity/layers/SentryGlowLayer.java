package com.aetherteam.aether.client.renderer.entity.layers;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class SentryGlowLayer extends EyesLayer<Sentry, SlimeModel<Sentry>> {
    private static final RenderType SENTRY_EYE = RenderType.eyes(new ResourceLocation(Aether.MODID, "textures/entity/mobs/sentry/eye.png"));

    public SentryGlowLayer(RenderLayerParent<Sentry, SlimeModel<Sentry>> entityRenderer) {
        super(entityRenderer);
    }

    /**
     * Renders the glowing eye layer for the Sentry when it is awake.
     *
     * @param poseStack       The rendering {@link PoseStack}.
     * @param buffer          The rendering {@link MultiBufferSource}.
     * @param packedLight     The {@link Integer} for the packed lighting for rendering.
     * @param sentry          The {@link Sentry} entity.
     * @param limbSwing       The {@link Float} for the limb swing rotation.
     * @param limbSwingAmount The {@link Float} for the limb swing amount.
     * @param partialTicks    The {@link Float} for the game's partial ticks.
     * @param ageInTicks      The {@link Float} for the entity's age in ticks.
     * @param netHeadYaw      The {@link Float} for the head yaw rotation.
     * @param headPitch       The {@link Float} for the head pitch rotation.
     */
    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Sentry sentry, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer consumer = buffer.getBuffer(this.renderType());
        if (sentry.isAwake()) {
            this.getParentModel().renderToBuffer(poseStack, consumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public RenderType renderType() {
        return SENTRY_EYE;
    }
}
