package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.passive.FlyingCowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlyingCowWingModel extends EntityModel<FlyingCowEntity>
{
    public ModelPart leftWingInner;
    public ModelPart leftWingOuter;
    public ModelPart rightWingInner;
    public ModelPart rightWingOuter;

    public FlyingCowWingModel() {
        this.leftWingInner = new ModelPart(this, 0, 0);
        this.leftWingInner.addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F, 0.0F, true);
        this.leftWingInner.setPos(-4.0F, 2.0F, -4.0F);

        this.leftWingOuter = new ModelPart(this, 20, 0);
        this.leftWingOuter.addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F, 0.0F, true);
        this.leftWingOuter.setPos(0.0F, -16.0F, 0.0F);
        this.leftWingInner.addChild(this.leftWingOuter);

        this.rightWingInner = new ModelPart(this, 0, 0);
        this.rightWingInner.addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F, 0.0F, false);
        this.rightWingInner.setPos(4.0F, 2.0F, -4.0F);

        this.rightWingOuter = new ModelPart(this, 20, 0);
        this.rightWingOuter.addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F, 0.0F, false);
        this.rightWingOuter.setPos(0.0F, -16.0F, 0.0F);
        this.rightWingInner.addChild(this.rightWingOuter);
    }

    @Override
    public void setupAnim(FlyingCowEntity flyingCow, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float aimingForFold;
        if (flyingCow.isOnGround()) {
            flyingCow.wingAngle *= 0.8F;
            aimingForFold = 0.1F;
        } else {
            aimingForFold = 1.0F;
        }
        flyingCow.wingAngle = flyingCow.wingFold * Mth.sin(ageInTicks / 31.83098862F);
        flyingCow.wingFold += (aimingForFold - flyingCow.wingFold) / 15.0F;
        float wingBend = -((float) Math.acos(flyingCow.wingFold));

        this.leftWingInner.zRot = -(flyingCow.wingAngle + wingBend + ((float) Math.PI / 2.0F));
        this.leftWingOuter.zRot = -(flyingCow.wingAngle - wingBend + ((float) Math.PI / 2.0F)) - this.leftWingInner.zRot;
        this.rightWingInner.zRot = -this.leftWingInner.zRot;
        this.rightWingOuter.zRot = -this.leftWingOuter.zRot;
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.leftWingInner.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.rightWingInner.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
