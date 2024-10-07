package com.aetherteam.aether.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

public class FireMinionModel<T extends Entity> extends SunSpiritModel<T> {
    public FireMinionModel(ModelPart root) {
        super(root);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, int color) {
        this.base.render(poseStack, consumer, packedLight, packedOverlay, color);
    }
}
