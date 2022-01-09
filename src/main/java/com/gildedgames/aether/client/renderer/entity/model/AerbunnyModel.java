package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.passive.AerbunnyEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AerbunnyModel extends EntityModel<AerbunnyEntity>
{
    public ModelPart head;
    public ModelPart rightEar;
    public ModelPart leftEar;
    public ModelPart rightWhiskers;
    public ModelPart leftWhiskers;
    public ModelPart body;
    public ModelPart puff;
    public ModelPart tail;
    public ModelPart rightFrontLeg;
    public ModelPart leftFrontLeg;
    public ModelPart rightBackLeg;
    public ModelPart leftBackLeg;
    public float puffiness;

    public AerbunnyModel(ModelPart model) {
        this.head = model.getChild("head");
        this.rightEar = head.getChild("right_ear");
        this.leftEar = head.getChild("left_ear");
        this.rightWhiskers = head.getChild("right_whiskers");
        this.leftWhiskers = head.getChild("left_whiskers");
        this.body = model.getChild("body");
        this.puff = model.getChild("puff");
        this.tail = body.getChild("tail");
        this.rightFrontLeg = body.getChild("right_front_leg");
        this.leftFrontLeg = body.getChild("left_front_leg");
        this.rightBackLeg = body.getChild("right_back_leg");
        this.leftBackLeg = body.getChild("left_back_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.0F, -4.0F, 4.0F, 4.0F, 6.0F), PartPose.offset(0.0F, 15.0F, -4.0F));
        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, -5.0F, -3.0F, 1.0F, 4.0F, 2.0F), PartPose.ZERO);
        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(14, 0).addBox(1.0F, -5.0F, -3.0F, 1.0F, 4.0F, 2.0F), PartPose.ZERO);
        head.addOrReplaceChild("right_whiskers", CubeListBuilder.create().texOffs(20, 0).addBox(-4.0F, 0.0F, -3.0F, 2.0F, 3.0F, 2.0F), PartPose.ZERO);
        head.addOrReplaceChild("left_whiskers", CubeListBuilder.create().texOffs(20, 0).addBox(2.0F, 0.0F, -3.0F, 2.0F, 3.0F, 2.0F), PartPose.ZERO);
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 10).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F), PartPose.offset(0.0F, 16.0F, 0.0F));
        partdefinition.addOrReplaceChild("puff", CubeListBuilder.create().texOffs(29, 0).addBox(-3.5F, -3.5F, -3.5F, 7.0F, 7.0F, 7.0F), PartPose.ZERO);
        body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 24).addBox(-2.0F, 4.0F, -2.0F, 4.0F, 3.0F, 4.0F), PartPose.ZERO);
        body.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(24, 16).addBox(0.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(-3.0F, -3.0F, -3.0F));
        body.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(24, 16).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(3.0F, -3.0F, -3.0F));
        body.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(16, 24).addBox(0.0F, 0.0F, -4.0F, 2.0F, 2.0F, 4.0F), PartPose.offset(-3.0F, 4.0F, -3.0F));
        body.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(16, 24).addBox(-2.0F, 0.0F, -4.0F, 2.0F, 2.0F, 4.0F), PartPose.offset(3.0F, 4.0F, -3.0F));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void prepareMobModel(AerbunnyEntity aerbunny, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
        super.prepareMobModel(aerbunny, p_212843_2_, p_212843_3_, p_212843_4_);
        this.puffiness = (float) aerbunny.getPuffiness() / 10.0F;
    }

    @Override
    public void setupAnim(AerbunnyEntity aerbunny, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.body.xRot = (float) Math.PI / 2F;
        this.puff.xRot = (float) Math.PI / 2F;
        this.rightFrontLeg.xRot = (Mth.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount) - this.body.xRot;
        this.leftFrontLeg.xRot = (Mth.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount) - this.body.xRot;
        this.rightBackLeg.xRot = (Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbSwingAmount) - this.body.xRot;
        this.leftBackLeg.xRot = (Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbSwingAmount) - this.body.xRot;
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        matrixStack.pushPose();
        float a = 1.0F + this.puffiness * 0.5F;
        matrixStack.translate(0.0F, 1.0F, 0.0F);
        matrixStack.scale(a, a, a);
        this.puff.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        matrixStack.popPose();
    }
}
