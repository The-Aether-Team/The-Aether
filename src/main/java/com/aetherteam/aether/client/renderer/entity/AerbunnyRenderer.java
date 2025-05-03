package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.AerbunnyModel;
import com.aetherteam.aether.client.renderer.entity.state.AerbunnyRenderState;
import com.aetherteam.aether.entity.passive.Aerbunny;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AerbunnyRenderer extends MobRenderer<Aerbunny, AerbunnyRenderState, AerbunnyModel> {
    private static final ResourceLocation AERBUNNY_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/aerbunny/aerbunny.png");

    public AerbunnyRenderer(EntityRendererProvider.Context context) {
        super(context, new AerbunnyModel(context.bakeLayer(AetherModelLayers.AERBUNNY)), 0.3F);
    }

    @Override
    public AerbunnyRenderState createRenderState() {
        return new AerbunnyRenderState();
    }

    @Override
    public void extractRenderState(Aerbunny entity, AerbunnyRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.deltaMovement = entity.getDeltaMovement();
        reusedState.puffiness = Mth.lerp(partialTick, entity.getPuffiness(), entity.getPuffiness() - entity.getPuffSubtract()) / 20.0F;
        reusedState.onGround = entity.onGround();
    }

    /**
     * Scales the Aerbunny if it is a baby.
     *
     * @param aerbunny     The {@link Aerbunny} entity.
     * @param poseStack    The rendering {@link PoseStack}.
     */
    @Override
    protected void scale(AerbunnyRenderState aerbunny, PoseStack poseStack) {
        if (aerbunny.isBaby) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
        poseStack.translate(0.0, 0.2, 0.0);
    }

    /**
     * Rotates the Aerbunny back and forth when it is jumping.
     *
     * @param aerbunny     The {@link Aerbunny} entity.
     * @param poseStack    The rendering {@link PoseStack}.
     * @param yBodyRot     The {@link Float} for the rotation yaw.
     * @param scale        The {@link Float} for the render scale.
     */
    @Override
    protected void setupRotations(AerbunnyRenderState aerbunny, PoseStack poseStack, float yBodyRot, float scale) {
        super.setupRotations(aerbunny, poseStack, yBodyRot, scale);
        if (!aerbunny.onGround) {
            if (aerbunny.deltaMovement.y() > 0.5) {
                poseStack.mulPose(Axis.XN.rotationDegrees(Mth.rotLerp(aerbunny.partialTick, 0.0F, 15.0F)));
            } else if (aerbunny.deltaMovement.y() < -0.5) {
                poseStack.mulPose(Axis.XN.rotationDegrees(Mth.rotLerp(aerbunny.partialTick, 0.0F, -15.0F)));
            } else {
                poseStack.mulPose(Axis.XN.rotationDegrees((float) (aerbunny.deltaMovement.y() * 30.0)));
            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(AerbunnyRenderState aerbunny) {
        return AERBUNNY_TEXTURE;
    }
}
