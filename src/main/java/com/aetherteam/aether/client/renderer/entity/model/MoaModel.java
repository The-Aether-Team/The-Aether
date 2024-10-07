package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;

public class MoaModel extends BipedBirdModel<Moa> {
    public boolean renderLegs;

    public MoaModel(ModelPart root) {
        super(root);
    }

    @Override
    public void prepareMobModel(Moa moa, float limbSwing, float limbSwingAmount, float partialTicks) {
        super.prepareMobModel(moa, limbSwing, limbSwingAmount, partialTicks);
        this.renderLegs = !moa.isSitting() || (!moa.isEntityOnGround() && moa.isSitting());
    }

    @Override
    public void setupAnim(Moa moa, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (moa.isSitting()) {
            this.jaw.xRot = 0.0F;
        } else {
            this.jaw.xRot = 0.35F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, int color) {
        super.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, color);
        if (this.renderLegs) {
            this.rightLeg.render(poseStack, consumer, packedLight, packedOverlay, color);
            this.leftLeg.render(poseStack, consumer, packedLight, packedOverlay, color);
        }
    }
}
