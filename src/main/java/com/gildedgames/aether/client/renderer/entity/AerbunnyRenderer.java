package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.AerbunnyModel;
import com.gildedgames.aether.entity.passive.Aerbunny;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;

public class AerbunnyRenderer extends MobRenderer<Aerbunny, AerbunnyModel> {
    private static final ResourceLocation AERBUNNY_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/aerbunny/aerbunny.png");

    public AerbunnyRenderer(EntityRendererProvider.Context context) {
        super(context, new AerbunnyModel(context.bakeLayer(AetherModelLayers.AERBUNNY)), 0.3F);
    }

    @Override
    protected void scale(Aerbunny aerbunny, PoseStack poseStack, float partialTickTime) {
        poseStack.translate(0.0, 0.2, 0.0);
        if (aerbunny.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
    }

    @Override
    protected void setupRotations(@Nonnull Aerbunny aerbunny, @Nonnull PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(aerbunny, poseStack, ageInTicks, rotationYaw, partialTicks);
        if (!aerbunny.isOnGround()) {
            if (aerbunny.getDeltaMovement().y > 0.5) {
                poseStack.mulPose(Vector3f.XN.rotationDegrees(Mth.rotLerp(partialTicks, 0.0F, 15.0F)));
            }
            else if (aerbunny.getDeltaMovement().y < -0.5) {
                poseStack.mulPose(Vector3f.XN.rotationDegrees(Mth.rotLerp(partialTicks, 0.0F, -15.0F)));
            }
            else {
                poseStack.mulPose(Vector3f.XN.rotationDegrees((float)(aerbunny.getDeltaMovement().y * 30.0)));
            }
        }
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull Aerbunny aerbunny) {
        return AERBUNNY_TEXTURE;
    }
}
