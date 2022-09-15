package com.gildedgames.aether.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nonnull;

public class FireMinionModel<T extends Entity> extends SunSpiritModel<T> {
    public FireMinionModel(ModelPart root) {
        super(root);
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.base.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
