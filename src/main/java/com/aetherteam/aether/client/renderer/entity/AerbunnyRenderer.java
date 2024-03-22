package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.AerbunnyModel;
import com.aetherteam.aether.entity.passive.Aerbunny;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AerbunnyRenderer extends MobRenderer<Aerbunny, AerbunnyModel> {
    private static final ResourceLocation AERBUNNY_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/aerbunny/aerbunny.png");

    public AerbunnyRenderer(EntityRendererProvider.Context context) {
        super(context, new AerbunnyModel(context.bakeLayer(AetherModelLayers.AERBUNNY)), 0.3F);
    }

    /**
     * Scales the Aerbunny if it is a baby.
     *
     * @param aerbunny     The {@link Aerbunny} entity.
     * @param poseStack    The rendering {@link PoseStack}.
     * @param partialTicks The {@link Float} for the game's partial ticks.
     */
    @Override
    protected void scale(Aerbunny aerbunny, PoseStack poseStack, float partialTicks) {
        if (aerbunny.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
        poseStack.translate(0.0, 0.2, 0.0);
    }

    /**
     * Rotates the Aerbunny back and forth when it is jumping.
     *
     * @param aerbunny     The {@link Aerbunny} entity.
     * @param poseStack    The rendering {@link PoseStack}.
     * @param ageInTicks   The {@link Float} for the entity's age in ticks.
     * @param rotationYaw  The {@link Float} for the rotation yaw.
     * @param partialTicks The {@link Float} for the game's partial ticks.
     */
    @Override
    protected void setupRotations(Aerbunny aerbunny, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(aerbunny, poseStack, ageInTicks, rotationYaw, partialTicks);
        if (!aerbunny.onGround()) {
            if (aerbunny.getDeltaMovement().y() > 0.5) {
                poseStack.mulPose(Axis.XN.rotationDegrees(Mth.rotLerp(partialTicks, 0.0F, 15.0F)));
            } else if (aerbunny.getDeltaMovement().y() < -0.5) {
                poseStack.mulPose(Axis.XN.rotationDegrees(Mth.rotLerp(partialTicks, 0.0F, -15.0F)));
            } else {
                poseStack.mulPose(Axis.XN.rotationDegrees((float) (aerbunny.getDeltaMovement().y() * 30.0)));
            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Aerbunny aerbunny) {
        return AERBUNNY_TEXTURE;
    }
}
