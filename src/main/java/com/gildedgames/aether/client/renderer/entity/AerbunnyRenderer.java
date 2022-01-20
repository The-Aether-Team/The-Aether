package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.AerbunnyModel;
import com.gildedgames.aether.common.entity.passive.Aerbunny;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;

import javax.annotation.Nonnull;

public class AerbunnyRenderer extends MobRenderer<Aerbunny, AerbunnyModel>
{
    private static final ResourceLocation AERBUNNY_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/aerbunny/aerbunny.png");

    public AerbunnyRenderer(EntityRendererProvider.Context renderer) {
        super(renderer, new AerbunnyModel(renderer.bakeLayer(AetherModelLayers.AERBUNNY)), 0.3F);
    }

    @Override
    protected void scale(Aerbunny aerbunny, PoseStack matrixStack, float scale) {
        matrixStack.translate(0, 0.2D, 0);
        if (aerbunny.isBaby()) {
            matrixStack.scale(0.5F, 0.5F, 0.5F);
        }
    }

    @Override
    protected void setupRotations(Aerbunny aerbunny, @Nonnull PoseStack poseStack, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
        if (!aerbunny.isOnGround()) {
            if (aerbunny.getDeltaMovement().y > 0.5D) {
                poseStack.mulPose(Vector3f.XN.rotationDegrees(15.0F));
            }
            else if (aerbunny.getDeltaMovement().y < -0.5D) {
                poseStack.mulPose(Vector3f.XN.rotationDegrees(-15.0F));
            }
            else {
                poseStack.mulPose(Vector3f.XN.rotationDegrees((float)(aerbunny.getDeltaMovement().y * 30.0D)));
            }
        }
        super.setupRotations(aerbunny, poseStack, p_225621_3_, p_225621_4_, p_225621_5_);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull Aerbunny entity) {
        return AERBUNNY_TEXTURE;
    }
}
