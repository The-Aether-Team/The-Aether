package com.aether.client.renderer.entity.model;

import com.aether.entity.monster.ZephyrEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class OldZephyrModel extends EntityModel<ZephyrEntity> {

    public ModelRenderer zephyr;

    public OldZephyrModel() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.zephyr = new ModelRenderer(this, 0, 0);
        this.zephyr.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.zephyr.addBox(-10.0F, 0.0F, -10.0F, 20, 14, 24, 0.0F);
    }

    @Override
    public void setRotationAngles(ZephyrEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.zephyr.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
