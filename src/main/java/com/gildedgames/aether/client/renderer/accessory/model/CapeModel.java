package com.gildedgames.aether.client.renderer.accessory.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;

import javax.annotation.Nonnull;

public class CapeModel extends HumanoidModel<LivingEntity>
{
    private final ModelPart cloak;

    public CapeModel() {
        super(1.0F);
        this.cloak = new ModelPart(this, 0, 0);
        this.cloak.setTexSize(64, 32);
        this.cloak.addBox(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, 0.0F);
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack matrixStack, @Nonnull VertexConsumer vertexBuilder, int light, int overlay, float red, float green, float blue, float alpha) {
        this.cloak.render(matrixStack, vertexBuilder, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(LivingEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        super.setupAnim(p_225597_1_, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
        if (p_225597_1_.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
            if (p_225597_1_.isCrouching()) {
                this.cloak.z = 1.4F;
                this.cloak.y = 1.85F;
            } else {
                this.cloak.z = 0.0F;
                this.cloak.y = 0.0F;
            }
        } else if (p_225597_1_.isCrouching()) {
            this.cloak.z = 0.3F;
            this.cloak.y = 0.8F;
        } else {
            this.cloak.z = -1.1F;
            this.cloak.y = -0.85F;
        }
    }
}
