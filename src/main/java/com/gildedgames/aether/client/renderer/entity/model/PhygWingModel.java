package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.passive.PhygEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PhygWingModel extends EntityModel<PhygEntity>
{
    public ModelPart leftWingInner;
    public ModelPart leftWingOuter;
    public ModelPart rightWingInner;
    public ModelPart rightWingOuter;

    public PhygWingModel(ModelPart root) {
        leftWingInner = root.getChild("left_wing_inner");
        leftWingOuter = leftWingInner.getChild("left_wing_outer");
        rightWingInner = root.getChild("right_wing_inner");
        rightWingOuter = rightWingInner.getChild("right_wing_outer");
    }

    public static LayerDefinition createMainLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        PartDefinition leftWingInnerDef = root.addOrReplaceChild("left_wing_inner", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F), PartPose.offset(-4.0F, 12.0F, -4.0F));
        leftWingInnerDef.addOrReplaceChild("left_wing_outer", CubeListBuilder.create().texOffs(20, 0).mirror().addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F), PartPose.offset(0.0F, -16.0F, 0.0F));
        PartDefinition rightWingInnerDef = root.addOrReplaceChild("right_wing_inner", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F), PartPose.offset(4.0F, 12.0F, -4.0F));
        rightWingInnerDef.addOrReplaceChild("right_wing_outer", CubeListBuilder.create().texOffs(20, 0).addBox(-1.0F, -16.0F, 0.0F, 2.0F, 16.0F, 8.0F), PartPose.offset(0.0F, -16.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(PhygEntity phyg, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float aimingForFold;
        if (phyg.isOnGround()) {
            aimingForFold = 0.1F;
        } else {
            aimingForFold = 1.0F;
        }
        phyg.wingAngle = phyg.wingFold * Mth.sin(ageInTicks / 31.83098862F);
        phyg.wingFold += (aimingForFold - phyg.wingFold) / 15.0F;
        float wingBend = -((float) Math.acos(phyg.wingFold));

        this.leftWingInner.zRot = -(phyg.wingAngle + wingBend + ((float) Math.PI / 2.0F));
        this.leftWingOuter.zRot = -(phyg.wingAngle - wingBend + ((float) Math.PI / 2.0F)) - this.leftWingInner.zRot;
        this.rightWingInner.zRot = -this.leftWingInner.zRot;
        this.rightWingOuter.zRot = -this.leftWingOuter.zRot;
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        leftWingInner.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        rightWingInner.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
