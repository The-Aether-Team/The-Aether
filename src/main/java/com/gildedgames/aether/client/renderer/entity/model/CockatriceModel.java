package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.monster.CockatriceEntity;
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
public class CockatriceModel extends EntityModel<CockatriceEntity>
{

    public ModelPart head;
    public ModelPart jaw;
    public ModelPart neck;
    public ModelPart body;
    public ModelPart rightWing;
    public ModelPart leftWing;
    public ModelPart feather1;
    public ModelPart feather2;
    public ModelPart feather3;
    public ModelPart rightLeg;
    public ModelPart leftLeg;

    public CockatriceModel(ModelPart root) {
        this.head = root.getChild("head");
        this.jaw = root.getChild("jaw");
        this.neck = root.getChild("neck");
        this.body = root.getChild("body");
        this.rightWing = root.getChild("right_wing");
        this.leftWing = root.getChild("left_wing");
        this.feather1 = root.getChild("feather_1");
        this.feather2 = root.getChild("feather_2");
        this.feather3 = root.getChild("feather_3");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0F, -4.0F, -6.0F, 4, 4, 8), PartPose.offset(0.0F, 8, -4.0F));
        partdefinition.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(24, 13).addBox(-2.0F, -1.0F, -6.0F, 4, 1, 8, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 8, -4.0F));
        partdefinition.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(44, 0).addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2), PartPose.offset(0.0F, 14, -4.0F));

        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, 0.0F, 6, 8, 5), PartPose.offset(0.0F, 16, 0.0F));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -1.0F, -1.0F, 2, 9, 2), PartPose.offset(-2.0F, 16, 1.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -1.0F, -1.0F, 2, 9, 2), PartPose.offset(2.0F, 16, 1.0F));

        partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(52, 0).addBox(-1.0F, -0.0F, -1.0F, 1, 8, 4), PartPose.offset(-3.0F, 16, 2.0F));
        partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(52, 0).addBox(0.0F, -0.0F, -1.0F, 1, 8, 4), PartPose.offset(3.0F, 12, 0.0F));
        partdefinition.addOrReplaceChild("feather_1", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -5.0F, 5.0F, 2, 1, 5, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 17, 1.0F));
        partdefinition.addOrReplaceChild("feather_2", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -5.0F, 5.0F, 2, 1, 5, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 17, 1.0F));
        partdefinition.addOrReplaceChild("feather_3", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -5.0F, 5.0F, 2, 1, 5, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 17, 1.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(CockatriceEntity cockatrice, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch / 57.29578F;
        this.head.yRot = netHeadYaw / 57.29578F;

        this.jaw.xRot = head.xRot;
        this.jaw.yRot = head.yRot;

        this.body.xRot = 1.570796F;

        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos((float)(limbSwing * 0.6662F + Math.PI)) * 1.4F * limbSwingAmount;

        if (ageInTicks > 0.001F) {
            this.rightWing.z = -1F;
            this.leftWing.z = -1F;
            this.rightWing.y = 12F;
            this.leftWing.y = 12F;
            this.rightWing.xRot = 0.0F;
            this.leftWing.xRot = 0.0F;
            this.rightWing.zRot = ageInTicks;
            this.leftWing.zRot = -ageInTicks;
            this.rightLeg.xRot = 0.6F;
            this.leftLeg.xRot = 0.6F;
        }
        else {
            this.rightWing.z = -3F;
            this.leftWing.z = -3F;
            this.rightWing.y = 14F;
            this.leftWing.y = 14F;
            this.rightWing.xRot = (float) (Math.PI / 2.0F);
            this.leftWing.xRot = (float) (Math.PI / 2.0F);
            this.rightWing.zRot = 0.0F;
            this.leftWing.zRot = 0.0F;
        }

        this.feather1.yRot = -0.375F;
        this.feather2.yRot = 0.0F;
        this.feather3.yRot = 0.375F;
        this.feather1.xRot = 0.25F;
        this.feather2.xRot = 0.25F;
        this.feather3.xRot = 0.25F;

        this.neck.xRot = 0.0F;
        this.neck.yRot = head.yRot;
        this.jaw.xRot += 0.35F;
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.rightLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.leftLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

        this.head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.jaw.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.rightWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.leftWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.neck.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.feather1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.feather2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.feather3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }

}
