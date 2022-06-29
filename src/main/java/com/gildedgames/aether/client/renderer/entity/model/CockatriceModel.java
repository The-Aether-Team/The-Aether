package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.entity.monster.Cockatrice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;

import javax.annotation.Nonnull;

public class CockatriceModel extends BipedBirdModel<Cockatrice> {
    public CockatriceModel(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(@Nonnull Cockatrice cockatrice, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(cockatrice, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.jaw.xRot = 0.35F;
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightLeg.render(poseStack, consumer, packedLight, packedOverlay);
        this.leftLeg.render(poseStack, consumer, packedLight, packedOverlay);
    }
}
