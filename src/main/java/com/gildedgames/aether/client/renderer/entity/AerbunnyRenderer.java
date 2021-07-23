package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.model.AerbunnyModel;
import com.gildedgames.aether.common.entity.passive.AerbunnyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class AerbunnyRenderer extends MobRenderer<AerbunnyEntity, AerbunnyModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/aerbunny/aerbunny.png");

    public AerbunnyRenderer(EntityRendererManager manager) {
        super(manager, new AerbunnyModel(), 0.3F);
    }

    @Override
    protected void scale(AerbunnyEntity aerbunny, MatrixStack matrixStack, float scale) {
        if (!aerbunny.isPassenger()) {
            matrixStack.translate(0, 0.2D, 0);
        }
        else {
            if (aerbunny.getVehicle() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) aerbunny.getVehicle();

                if (player.isFallFlying()) {
                    matrixStack.translate(0.0D, 0.7D, -1.4D);
                }
            }
        }

        if (!aerbunny.isOnGround()) {
            if (aerbunny.getDeltaMovement().y > 0.5D) {
                matrixStack.mulPose(Vector3f.XN.rotationDegrees(15.0F));
            }
            else if (aerbunny.getDeltaMovement().y < -0.5D) {
                matrixStack.mulPose(Vector3f.XN.rotationDegrees(-15.0F));
            }
            else {
                matrixStack.mulPose(Vector3f.XN.rotationDegrees((float)(aerbunny.getDeltaMovement().y * 30.0D)));
            }
        }

        this.model.puffiness = (float)(aerbunny.getVehicle() != null ? aerbunny.getPuffinessClient() : aerbunny.getPuffiness()) / 10.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(AerbunnyEntity p_110775_1_) {
        return TEXTURE;
    }
}
