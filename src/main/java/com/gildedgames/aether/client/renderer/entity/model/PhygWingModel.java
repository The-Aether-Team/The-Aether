package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.passive.PhygEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class PhygWingModel extends EntityModel<PhygEntity>
{
    private final ModelRenderer leftWingInner;
    private final ModelRenderer leftWingOuter;
    private final ModelRenderer rightWingInner;
    private final ModelRenderer rightWingOuter;
    private float wingFold;
    private float wingAngle;

    public PhygWingModel() {
        this.leftWingInner = new ModelRenderer(this, 0, 0);
        this.leftWingInner.addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F, 0.0F, true); //USED FOR ROTATION AND SIZE
        this.leftWingInner.setPos(-4.0F, 12.0F, -4.0F); //USED FOR POSITION
        this.leftWingOuter = new ModelRenderer(this, 20, 0);
        this.leftWingOuter.addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F, 0.0F, true);
        this.leftWingOuter.setPos(0.0F, -16.0F, 0.0F);
        this.leftWingInner.addChild(this.leftWingOuter);
        this.rightWingInner = new ModelRenderer(this, 0, 0);
        this.rightWingInner.addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F, 0.0F, false);
        this.rightWingInner.setPos(4.0F, 12.0F, -4.0F);
        this.rightWingOuter = new ModelRenderer(this, 20, 0);
        this.rightWingOuter.addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F, 0.0F, false);
        this.rightWingOuter.setPos(0.0F, -16.0F, 0.0F);
        this.rightWingInner.addChild(this.rightWingOuter);
    }

    @Override
    public void setupAnim(PhygEntity phyg, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float aimingForFold;
        if (phyg.isOnGround()) {
            this.wingAngle *= 0.8F;
            aimingForFold = 0.1F;
        } else {
            aimingForFold = 1.0F;
        }
        this.wingAngle = this.wingFold * (float) Math.sin(ageInTicks / 31.83098862F);
        this.wingFold += (aimingForFold - this.wingFold) / 15.0F;
        float wingBend = -((float) Math.acos(this.wingFold));

        this.leftWingInner.zRot = -(this.wingAngle + wingBend + ((float) Math.PI / 2.0F));
        this.leftWingOuter.zRot = -(this.wingAngle - wingBend + ((float) Math.PI / 2.0F)) - this.leftWingInner.zRot;
        this.rightWingInner.zRot = -this.leftWingInner.zRot;
        this.rightWingOuter.zRot = -this.leftWingOuter.zRot;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.leftWingInner.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.rightWingInner.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
