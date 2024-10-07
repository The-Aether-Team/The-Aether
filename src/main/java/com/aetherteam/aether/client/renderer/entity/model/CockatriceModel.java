package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.entity.monster.Cockatrice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;

public class CockatriceModel extends BipedBirdModel<Cockatrice> {
    public CockatriceModel(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(Cockatrice cockatrice, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(cockatrice, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.jaw.xRot = 0.35F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, int color) {
        super.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, color);
        this.rightLeg.render(poseStack, consumer, packedLight, packedOverlay, color);
        this.leftLeg.render(poseStack, consumer, packedLight, packedOverlay, color);
    }
}
