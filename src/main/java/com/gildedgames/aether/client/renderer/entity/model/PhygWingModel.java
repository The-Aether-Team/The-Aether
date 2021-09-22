package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.passive.PhygEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PhygWingModel extends EntityModel<PhygEntity>
{
    public ModelRenderer leftWingInner;
    public ModelRenderer leftWingOuter;
    public ModelRenderer rightWingInner;
    public ModelRenderer rightWingOuter;

    public PhygWingModel() {
        leftWingInner = new ModelRenderer(this, 0, 0);
        leftWingInner.addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F, 0.0F, true); //USED FOR ROTATION AND SIZE
        leftWingInner.setPos(-4.0F, 12.0F, -4.0F); //USED FOR POSITION

        leftWingOuter = new ModelRenderer(this, 20, 0);
        leftWingOuter.addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F, 0.0F, true);
        leftWingOuter.setPos(0.0F, -16.0F, 0.0F);
        leftWingInner.addChild(this.leftWingOuter);

        rightWingInner = new ModelRenderer(this, 0, 0);
        rightWingInner.addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F, 0.0F, false);
        rightWingInner.setPos(4.0F, 12.0F, -4.0F);

        rightWingOuter = new ModelRenderer(this, 20, 0);
        rightWingOuter.addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F, 0.0F, false);
        rightWingOuter.setPos(0.0F, -16.0F, 0.0F);
        rightWingInner.addChild(this.rightWingOuter);
    }

    @Override
    public void setupAnim(PhygEntity phyg, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float aimingForFold;
        if (phyg.isOnGround()) {
            phyg.wingAngle *= 0.8F;
            aimingForFold = 0.1F;
        } else {
            aimingForFold = 1.0F;
        }
        phyg.wingAngle = phyg.wingFold * MathHelper.sin(ageInTicks / 31.83098862F);
        phyg.wingFold += (aimingForFold - phyg.wingFold) / 15.0F;
        float wingBend = -((float) Math.acos(phyg.wingFold));

        leftWingInner.zRot = -(rightWingInner.zRot = phyg.wingAngle + wingBend + ((float) Math.PI / 2.0F));
        leftWingOuter.zRot = -(rightWingOuter.zRot = phyg.wingAngle - wingBend + ((float) Math.PI / 2.0F)) - this.leftWingInner.zRot;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        leftWingInner.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        rightWingInner.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
