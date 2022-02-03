package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.monster.CockatriceEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;

public class CockatriceModel extends BipedBirdModel<CockatriceEntity>
{
    public CockatriceModel(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(@Nonnull CockatriceEntity cockatrice, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(cockatrice, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (!cockatrice.isEntityOnGround()) {
            this.rightWing.setPos(-3.001F, 0.0F, 4.0F);
            this.leftWing.setPos(3.001F, 0.0F, 4.0F);
            this.rightWing.xRot = (float) -(Math.PI / 2.0F);
            this.leftWing.xRot = this.rightWing.xRot;
            this.rightLeg.xRot = 0.6F;
            this.leftLeg.xRot = this.rightLeg.xRot;
        } else {
            this.rightWing.setPos(-3.001F, -3.0F, 3.0F);
            this.leftWing.setPos(3.001F, -3.0F, 3.0F);
            this.rightWing.xRot = 0.0F;
            this.leftWing.xRot = 0.0F;
            this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leftLeg.xRot = Mth.cos((float) (limbSwing * 0.6662F + Math.PI)) * 1.4F * limbSwingAmount;
        }

        float rotVal = cockatrice.prevWingRotation + (cockatrice.wingRotation - cockatrice.prevWingRotation);
        float destVal = cockatrice.prevDestPos + (cockatrice.destPos - cockatrice.prevDestPos);

        this.rightWing.yRot = (Mth.sin(rotVal * 0.225F) + 1.0F) * destVal;
        this.leftWing.yRot = -this.rightWing.yRot;

        this.jaw.xRot = 0.35F;
    }

    public void setupWingsAnimation(CockatriceEntity cockatrice) {
        cockatrice.prevWingRotation = cockatrice.wingRotation;
        cockatrice.prevDestPos = cockatrice.destPos;
        if (!cockatrice.isEntityOnGround()) {
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
