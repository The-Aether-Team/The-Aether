package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.monster.AechorPlantEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AechorPlantModel<T extends Entity> extends EntityModel<AechorPlantEntity> {
    public ModelPart[] Petal = new ModelPart[10];
    public ModelPart[] Leaf = new ModelPart[10];
    public ModelPart[] StamenStem = new ModelPart[3];
    public ModelPart[] StamenTip = new ModelPart[3];
    public ModelPart[] Thorn = new ModelPart[4];
    public ModelPart Stem;
    public ModelPart Head;

    public float sinage;
    public float sinage2;
    public float size;

    public AechorPlantModel(ModelPart root) {
        Stem = root.getChild("stem");
        Head = root.getChild("head");

        for (int i = 0; i < 10; i++) {
            Petal[i] = root.getChild("petal_" + i);
            Leaf[i] = root.getChild("leaf_" + i);
        }

        for (int i = 0; i < 3; i++) {
            StamenStem[i] = root.getChild("stamen_stem_" + i);
            StamenTip[i] = root.getChild("stamen_tip_" + i);
        }

        for (int i = 0; i < 4; i++) {
            Thorn[i] = root.getChild("thorn_" + i);
        }
    }

    public static LayerDefinition createMainLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                partdefinition.addOrReplaceChild("petal_" + i, CubeListBuilder.create().texOffs(28, 2)
                        .addBox(-4.0F, -1.0F, -12.0F, 8, 0.5F, 10, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 1.0F, 0.0F));
            } else {
                partdefinition.addOrReplaceChild("petal_" + i, CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-4.0F, -1.0F, -12.0F, 8, 0.5F, 10, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));
            }

            partdefinition.addOrReplaceChild("leaf_" + i, CubeListBuilder.create().texOffs(38, 13)
                    .addBox(-2.0F, -1.0F, -9.5F, 4, 1, 8, new CubeDeformation(-0.15F)), PartPose.offset(0.0F, 1.0F, 0.0F));
        }

        for (int i = 0; i < 3; i++) {
            partdefinition.addOrReplaceChild("stamen_stem_" + i, CubeListBuilder.create().texOffs(36, 13)
                    .addBox(0.0F, -9.0F, -1.5F, 1, 6, 1, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 1.0F, 0.0F));

            partdefinition.addOrReplaceChild("stamen_tip_" + i, CubeListBuilder.create().texOffs(32, 15)
                    .addBox(0.0F, -10.0F, -1.5F, 1, 1, 1, new CubeDeformation(0.125F)), PartPose.offset(0.0F, 1.0F, 0.0F));
        }

        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 12)
                .addBox(-3.0F, -3.0F, -3.0F, 6, 2, 6, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        partdefinition.addOrReplaceChild("stem", CubeListBuilder.create().texOffs(24, 13)
                .addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2), PartPose.offset(0.0F, 1.0F, 0.0F));

        partdefinition.addOrReplaceChild("thorn_0", CubeListBuilder.create().texOffs(24, 13)
                .addBox(-1.75F, 1.25F, -1F, 1, 1, 1, new CubeDeformation(-0.25f)), PartPose.offset(0.0F, 1.0F, 0.0F));

        partdefinition.addOrReplaceChild("thorn_1", CubeListBuilder.create().texOffs(24, 13)
                .addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, new CubeDeformation(-0.25f)), PartPose.offset(0.0F, 1.0F, 0.0F));

        partdefinition.addOrReplaceChild("thorn_2", CubeListBuilder.create().texOffs(24, 13)
                .addBox(0.75F, 1.25F, 0F, 1, 1, 1, new CubeDeformation(-0.25f)), PartPose.offset(0.0F, 1.0F, 0.0F));

        partdefinition.addOrReplaceChild("thorn_3", CubeListBuilder.create().texOffs(24, 13)
                .addBox(0F, 2.25F, -1.75F, 1, 1, 1, new CubeDeformation(-0.25f)), PartPose.offset(0.0F, 1.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        for (int i = 0; i < 10; i++) {
            Petal[i].render(matrixStack, buffer, packedLight, packedOverlay);
            Leaf[i].render(matrixStack, buffer, packedLight, packedOverlay);
        }
        for (int i = 0; i < 3; i++) {
            StamenStem[i].render(matrixStack, buffer, packedLight, packedOverlay);
            StamenTip[i].render(matrixStack, buffer, packedLight, packedOverlay);
        }
        for (int i = 0; i < 4; i++) {
            Thorn[i].render(matrixStack, buffer, packedLight, packedOverlay);
        }

        Head.render(matrixStack, buffer, packedLight, packedOverlay);
        Stem.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    //     maybe build an immutable list?
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.<ModelPart>builder().addAll(ImmutableList.copyOf(this.Petal)).addAll(ImmutableList.copyOf(this.Leaf)).build();
    }

    @Override
    public void setupAnim(AechorPlantEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        Head.xRot = 0.0F;
        Head.yRot = headPitch / 57.29578F;

        float boff = this.sinage2;

        Stem.yRot = Head.yRot;
        Stem.y = boff * 0.5F;

        for (int i = 0; i < 10; i++) {
            if (i < 3) {
                StamenStem[i].xRot = 0.2F + ((float) i / 15F);
                StamenStem[i].yRot = Head.yRot + 0.1F;
                StamenStem[i].yRot += ((Math.PI * 2) / (float) 3) * (float) i;
                StamenStem[i].xRot += sinage * 0.4F;

                StamenTip[i].xRot = 0.2F + ((float) i / 15F);
                StamenTip[i].yRot = Head.yRot + 0.1F;
                StamenTip[i].yRot += ((Math.PI * 2) / (float) 3) * (float) i;
                StamenTip[i].xRot += sinage * 0.4F;

                StamenStem[i].y = boff + ((sinage) * 2F);
                StamenTip[i].y = boff + ((sinage) * 2F);
            }

            if (i < 4) {
                Thorn[i].yRot = Head.yRot;
                Thorn[i].y = boff * 0.5F;
            }

            Petal[i].xRot = ((i % 2 == 0) ? -0.25F : -0.4125F);
            Petal[i].xRot += sinage;
            Petal[i].yRot = Head.yRot;
            Petal[i].yRot += ((Math.PI * 2) / (float) 10) * (float) i;

            Leaf[i].xRot = ((i % 2 == 0) ? 0.1F : 0.2F);
            Leaf[i].xRot += sinage * 0.75F;
            Leaf[i].yRot = (float) (Head.yRot + ((Math.PI * 2) / (float) 10 / 2F));
            Leaf[i].yRot += ((Math.PI * 2) / (float) 10) * (float) i;

            Petal[i].y = boff;
            Leaf[i].y = boff;
        }

        Head.y = boff + ((sinage) * 2F);
    }

}
