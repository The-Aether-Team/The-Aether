package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.monster.CockatriceEntity;
import com.gildedgames.aether.common.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class CockatriceModel extends BipedBirdModel<CockatriceEntity>
{
    public CockatriceModel(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(@Nonnull CockatriceEntity cockatrice, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(cockatrice, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        float rotVal = cockatrice.prevWingRotation + (cockatrice.wingRotation - cockatrice.prevWingRotation);
        float destVal = cockatrice.prevDestPos + (cockatrice.destPos - cockatrice.prevDestPos);

        this.rightWing.yRot = (Mth.sin(rotVal * 0.225F) + 1.0F) * destVal;
        this.leftWing.yRot = -this.rightWing.yRot;

        this.jaw.xRot = 0.35F;
    }

    public void setupWingsAnimation(CockatriceEntity cockatrice) {
        cockatrice.prevWingRotation = cockatrice.wingRotation;
        cockatrice.prevDestPos = cockatrice.destPos;
        if (!cockatrice.isOnGround() && cockatrice.hasImpulse) {
            cockatrice.destPos += 0.2F;
            cockatrice.destPos = Math.min(1.0F, Math.max(0.01F, cockatrice.destPos));
        } else {
            cockatrice.destPos = 0.0F;
            cockatrice.wingRotation = 0.0F;
        }
        cockatrice.wingRotation += 1.233F;
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightLeg.render(poseStack, consumer, packedLight, packedOverlay);
        this.leftLeg.render(poseStack, consumer, packedLight, packedOverlay);
    }
}
