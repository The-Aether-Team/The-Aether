package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.Aether;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Mob;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SunSpiritModel extends EntityModel<Mob>
{

    public ModelPart head;
    public ModelPart jaw;
    public ModelPart chest;
    public ModelPart torso;
    public ModelPart rightBase;
    public ModelPart leftBase;
    public ModelPart rightShoulder;
    public ModelPart rightArm;
    public ModelPart rightBracelet;
    public ModelPart leftShoulder;
    public ModelPart leftArm;
    public ModelPart leftBracelet;

    public SunSpiritModel(ModelPart root) {
        this.head = root.getChild("head");
        this.jaw = root.getChild("jaw");
        this.chest = root.getChild("chest");
        this.torso = root.getChild("torso");
        this.rightBase = root.getChild("right_base");
        this.leftBase = root.getChild("left_base");
        this.rightShoulder = root.getChild("right_shoulder");
        this.rightArm = root.getChild("right_arm");
        this.rightBracelet = root.getChild("right_bracelet");
        this.leftShoulder = root.getChild("left_shoulder");
        this.leftArm = root.getChild("left_arm");
        this.leftBracelet = root.getChild("left_bracelet");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -3.0F, 8.0F, 5.0F, 7.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 3.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 12).addBox(-5.0F, 0.0F, -2.5F, 10.0F, 6.0F, 5.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 23).addBox(-4.5F, 6.0F, -2.0F, 9.0F, 5.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_base", CubeListBuilder.create().texOffs(30, 27).addBox(-4.5F, 11.0F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_base", CubeListBuilder.create().texOffs(30, 27).addBox(-0.5F, 11.0F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(30, 11).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offset(-8.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(30, 11).addBox(-2.5F, 2.5F, -2.5F, 5.0F, 10.0F, 5.0F), PartPose.offset(-8.0F, 2.0F + 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_bracelet", CubeListBuilder.create().texOffs(30, 26).addBox(-2.5F, 7.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(-8.0F, 2.0F, 0.0F));

        partdefinition.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(30, 11).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.5F)).mirror(true), PartPose.offset(8.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(30, 11).addBox(-2.5F, 2.5F, -2.5F, 5.0F, 10.0F, 5.0F).mirror(true), PartPose.offset(8.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_bracelet", CubeListBuilder.create().texOffs(30, 11).addBox(-2.5F, 7.5F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.25F)).mirror(true), PartPose.offset(8.0F, 2.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(Mob entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.xRot = jaw.xRot = headPitch * 0.017453292F;
        head.yRot = jaw.yRot = netHeadYaw * 0.017453292F;

        leftShoulder.xRot = -(rightShoulder.xRot = Mth.sin(ageInTicks * 0.067F) * 0.05F);
        leftShoulder.yRot = rightShoulder.yRot = 0.0F;
        leftShoulder.zRot = -(rightShoulder.zRot = Mth.cos(ageInTicks * 0.09F) * 0.05F - 0.05F);

        leftBase.xRot = rightBase.xRot = torso.xRot = chest.xRot;
        leftBase.yRot = rightBase.yRot = torso.yRot = chest.yRot;

        leftBracelet.xRot = leftArm.xRot = -leftShoulder.xRot;
        leftBracelet.yRot = leftArm.yRot = -leftShoulder.yRot;
        leftBracelet.zRot = leftArm.zRot = -leftShoulder.zRot;

        rightBracelet.xRot = rightArm.xRot = -rightShoulder.xRot;
        rightBracelet.yRot = rightArm.yRot = -rightShoulder.yRot;
        rightBracelet.zRot = rightArm.zRot = -rightShoulder.zRot;
    }

    @Override
    public void renderToBuffer(PoseStack poseStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        head.render(poseStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        jaw.render(poseStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        chest.render(poseStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        torso.render(poseStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        rightBase.render(poseStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        leftBase.render(poseStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        rightShoulder.render(poseStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        rightArm.render(poseStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        rightBracelet.render(poseStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        leftShoulder.render(poseStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        leftArm.render(poseStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        leftBracelet.render(poseStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
