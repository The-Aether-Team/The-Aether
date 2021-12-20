package com.gildedgames.aether.client.renderer.accessory.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;

public class PendantModel extends HumanoidModel<LivingEntity>
{
    public PendantModel() {
        super(1.0F);
        this.texWidth = 24;
        this.texHeight = 16;
        this.body = new ModelPart(this, 0, 0);
        this.body.addBox(-4.0F, 0.5F, -2.0F, 8.0F, 12.5F, 4.0F, 0.6F);
        this.body.setPos(0.0F, 0.0F, 0.0F);
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack matrixStack, @Nonnull VertexConsumer vertexBuilder, int light, int overlay, float red, float green, float blue, float alpha) {
        this.body.render(matrixStack, vertexBuilder, light, overlay, red, green, blue, alpha);
    }
}
