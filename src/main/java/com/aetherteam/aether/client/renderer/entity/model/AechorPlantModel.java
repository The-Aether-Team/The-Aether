package com.aetherteam.aether.client.renderer.entity.model;

import com.aetherteam.aether.entity.monster.AechorPlant;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class AechorPlantModel extends EntityModel<AechorPlant> {
    public final ModelPart stem;
    public final ModelPart head;
    public final ModelPart thorn1;
    public final ModelPart thorn2;
    public final ModelPart thorn3;
    public final ModelPart thorn4;
    public final ModelPart stamenStem1;
    public final ModelPart stamenStem2;
    public final ModelPart stamenStem3;
    public final ModelPart stamenTip1;
    public final ModelPart stamenTip2;
    public final ModelPart stamenTip3;
    public final ModelPart leaf1;
    public final ModelPart leaf2;
    public final ModelPart leaf3;
    public final ModelPart leaf4;
    public final ModelPart leaf5;
    public final ModelPart leaf6;
    public final ModelPart leaf7;
    public final ModelPart leaf8;
    public final ModelPart leaf9;
    public final ModelPart leaf10;
    public final ModelPart upperPetal1;
    public final ModelPart upperPetal2;
    public final ModelPart upperPetal3;
    public final ModelPart upperPetal4;
    public final ModelPart upperPetal5;
    public final ModelPart lowerPetal1;
    public final ModelPart lowerPetal2;
    public final ModelPart lowerPetal3;
    public final ModelPart lowerPetal4;
    public final ModelPart lowerPetal5;

    public AechorPlantModel(ModelPart root) {
        this.stem = root.getChild("stem");
        this.head = root.getChild("head");
        this.thorn1 = this.stem.getChild("thorn_1");
        this.thorn2 = this.stem.getChild("thorn_2");
        this.thorn3 = this.stem.getChild("thorn_3");
        this.thorn4 = this.stem.getChild("thorn_4");
        this.stamenStem1 = this.stem.getChild("stamen_stem_1");
        this.stamenStem2 = this.stem.getChild("stamen_stem_2");
        this.stamenStem3 = this.stem.getChild("stamen_stem_3");
        this.stamenTip1 = this.stamenStem1.getChild("stamen_tip_1");
        this.stamenTip2 = this.stamenStem2.getChild("stamen_tip_2");
        this.stamenTip3 = this.stamenStem3.getChild("stamen_tip_3");
        this.leaf1 = this.stem.getChild("leaf_1");
        this.leaf2 = this.stem.getChild("leaf_2");
        this.leaf3 = this.stem.getChild("leaf_3");
        this.leaf4 = this.stem.getChild("leaf_4");
        this.leaf5 = this.stem.getChild("leaf_5");
        this.leaf6 = this.stem.getChild("leaf_6");
        this.leaf7 = this.stem.getChild("leaf_7");
        this.leaf8 = this.stem.getChild("leaf_8");
        this.leaf9 = this.stem.getChild("leaf_9");
        this.leaf10 = this.stem.getChild("leaf_10");
        this.upperPetal1 = this.stem.getChild("upper_petal_1");
        this.upperPetal2 = this.stem.getChild("upper_petal_2");
        this.upperPetal3 = this.stem.getChild("upper_petal_3");
        this.upperPetal4 = this.stem.getChild("upper_petal_4");
        this.upperPetal5 = this.stem.getChild("upper_petal_5");
        this.lowerPetal1 = this.stem.getChild("lower_petal_1");
        this.lowerPetal2 = this.stem.getChild("lower_petal_2");
        this.lowerPetal3 = this.stem.getChild("lower_petal_3");
        this.lowerPetal4 = this.stem.getChild("lower_petal_4");
        this.lowerPetal5 = this.stem.getChild("lower_petal_5");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition stem = partDefinition.addOrReplaceChild("stem", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2), PartPose.offset(0.0F, 1.0F, 0.0F));
        partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 12).addBox(-3.0F, -3.0F, -3.0F, 6, 2, 6, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 1.0F, 0.0F));
        stem.addOrReplaceChild("thorn_1", CubeListBuilder.create().texOffs(32, 13).addBox(-1.75F, 1.25F, -1.0F, 1, 1, 1, new CubeDeformation(-0.25F)), PartPose.ZERO);
        stem.addOrReplaceChild("thorn_2", CubeListBuilder.create().texOffs(32, 13).addBox(-1.0F, 2.25F, 0.75F, 1, 1, 1, new CubeDeformation(-0.25F)), PartPose.ZERO);
        stem.addOrReplaceChild("thorn_3", CubeListBuilder.create().texOffs(32, 13).addBox(0.75F, 1.25F, 0.0F, 1, 1, 1, new CubeDeformation(-0.25F)), PartPose.ZERO);
        stem.addOrReplaceChild("thorn_4", CubeListBuilder.create().texOffs(32, 13).addBox(0.0F, 2.25F, -1.75F, 1, 1, 1, new CubeDeformation(-0.25F)), PartPose.ZERO);
        PartDefinition stamenStem1 = stem.addOrReplaceChild("stamen_stem_1", CubeListBuilder.create().texOffs(36, 13).addBox(0.0F, -9.0F, -1.5F, 1, 6, 1, new CubeDeformation(-0.25F)), PartPose.ZERO);
        PartDefinition stamenStem2 = stem.addOrReplaceChild("stamen_stem_2", CubeListBuilder.create().texOffs(36, 13).addBox(0.0F, -9.0F, -1.5F, 1, 6, 1, new CubeDeformation(-0.25F)), PartPose.ZERO);
        PartDefinition stamenStem3 = stem.addOrReplaceChild("stamen_stem_3", CubeListBuilder.create().texOffs(36, 13).addBox(0.0F, -9.0F, -1.5F, 1, 6, 1, new CubeDeformation(-0.25F)), PartPose.ZERO);
        stamenStem1.addOrReplaceChild("stamen_tip_1", CubeListBuilder.create().texOffs(32, 15).addBox(0.0F, -9.0F, -1.5F, 1, 1, 1, new CubeDeformation(0.125F)), PartPose.ZERO);
        stamenStem2.addOrReplaceChild("stamen_tip_2", CubeListBuilder.create().texOffs(32, 15).addBox(0.0F, -9.0F, -1.5F, 1, 1, 1, new CubeDeformation(0.125F)), PartPose.ZERO);
        stamenStem3.addOrReplaceChild("stamen_tip_3", CubeListBuilder.create().texOffs(32, 15).addBox(0.0F, -9.0F, -1.5F, 1, 1, 1, new CubeDeformation(0.125F)), PartPose.ZERO);
        for (int i = 1; i <= 10; i++) {
            stem.addOrReplaceChild("leaf_" + i, CubeListBuilder.create().texOffs(38, 13).addBox(-2.0F, -1.0F, -9.5F, 4, 1, 8, new CubeDeformation(-0.15F)), PartPose.ZERO);
        }
        for (int i = 1; i <= 5; i++) {
            stem.addOrReplaceChild("upper_petal_" + i, CubeListBuilder.create().texOffs(28, 2).addBox(-4.0F, -1.0F, -12.0F, 8, 1, 10, new CubeDeformation(-0.25F)), PartPose.ZERO);
        }
        for (int i = 1; i <= 5; i++) {
            stem.addOrReplaceChild("lower_petal_" + i, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.0F, -12.0F, 8, 1, 10, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, -1.0F, 0.0F));
        }
        return LayerDefinition.create(meshDefinition, 64, 32);
    }


    public Iterable<ModelPart> stamenStemParts() {
        return ImmutableList.of(this.stamenStem1, this.stamenStem2, this.stamenStem3);
    }


    public Iterable<ModelPart> leafParts() {
        return ImmutableList.of(this.leaf1, this.leaf2, this.leaf3, this.leaf4, this.leaf5, this.leaf6, this.leaf7, this.leaf8, this.leaf9, this.leaf10);
    }


    public Iterable<ModelPart> petalParts() {
        return ImmutableList.of(this.upperPetal1, this.lowerPetal1, this.upperPetal2, this.lowerPetal2, this.upperPetal3, this.lowerPetal3, this.upperPetal4, this.lowerPetal4, this.upperPetal5, this.lowerPetal5);
    }

    @Override
    public void setupAnim(AechorPlant aechorPlant, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float sinage1 = Mth.sin(ageInTicks);
        float sinage2;

        if (aechorPlant.hurtTime > 0) {
            sinage1 *= 0.45F;
            sinage1 -= 0.125F;
            sinage2 = 1.75F + Mth.sin(ageInTicks + 2.0F) * 1.5F;
        } else if (aechorPlant.getTargetingEntity()) {
            sinage1 *= 0.25F;
            sinage2 = 1.75F + Mth.sin(ageInTicks + 2.0F) * 1.5F;
        } else {
            sinage1 *= 0.125F;
            sinage2 = 1.75F;
        }

        this.head.yRot = headPitch / 57.29578F;
        this.stem.yRot = this.head.yRot;
        this.stem.y = sinage2 * 0.5F;

        int i = 0;
        for (ModelPart modelPart : this.stamenStemParts()) {
            modelPart.xRot = 0.2F + (i / 15.0F);
            modelPart.yRot = this.head.yRot + 0.1F;
            modelPart.yRot += (Mth.TWO_PI / 3.0F) * i;
            modelPart.xRot += sinage1 * 0.4F;
            modelPart.y = sinage2 + (sinage1 * 2.0F);
            i++;
        }

        i = 0;
        for (ModelPart modelPart : this.leafParts()) {
            modelPart.xRot = ((i % 2 == 0) ? 0.1F : 0.2F);
            modelPart.xRot += sinage1 * 0.75F;
            modelPart.yRot = this.head.yRot + (Mth.TWO_PI / 10.0F / 2.0F);
            modelPart.yRot += (Mth.TWO_PI / 10.0F) * i;
            modelPart.y = sinage2;
            i++;
        }

        i = 0;
        for (ModelPart modelPart : this.petalParts()) {
            modelPart.xRot = ((i % 2 == 0) ? -0.25F : -0.4125F);
            modelPart.xRot += sinage1;
            modelPart.yRot = this.head.yRot;
            modelPart.yRot += (Mth.TWO_PI / 10.0F) * i;
            modelPart.y = sinage2;
            i++;
        }

        this.head.y = sinage2 + (sinage1 * 2.0F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.stem.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.head.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
