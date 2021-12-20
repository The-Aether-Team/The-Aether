package com.gildedgames.aether.client.renderer.entity.model;

import com.gildedgames.aether.common.entity.monster.CockatriceEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CockatriceModel extends EntityModel<CockatriceEntity>
{
    public ModelPart head, body;
    public ModelPart legs, legs2;
    public ModelPart wings, wings2;
    public ModelPart jaw, neck;
    public ModelPart feather1, feather2, feather3;

    public CockatriceModel() {
        this.head = new ModelPart(this, 0, 13);
        this.head.addBox(-2.0F, -4.0F, -6.0F, 4, 4, 8, 0.0F);
        this.head.setPos(0.0F, -8 + 16, -4.0F);

        this.jaw = new ModelPart(this, 24, 13);
        this.jaw.addBox(-2.0F, -1.0F, -6.0F, 4, 1, 8, -0.1F);
        this.jaw.setPos(0.0F, -8 + 16, -4.0F);

        this.body = new ModelPart(this, 0, 0);
        this.body.addBox(-3.0F, -3.0F, 0.0F, 6, 8, 5, 0.0F);
        this.body.setPos(0.0F, 16, 0.0F);

        this.legs = new ModelPart(this, 22, 0);
        this.legs.addBox(-1.0F, -1.0F, -1.0F, 2, 9, 2);
        this.legs.setPos(-2.0F, 16, 1.0F);

        this.legs2 = new ModelPart(this, 22, 0);
        this.legs2.addBox(-1.0F, -1.0F, -1.0F, 2, 9, 2);
        this.legs2.setPos(2.0F, 16, 1.0F);

        this.wings = new ModelPart(this, 52, 0);
        this.wings.addBox(-1.0F, -0.0F, -1.0F, 1, 8, 4);
        this.wings.setPos(-3.0F, (16), 2.0F);

        this.wings2 = new ModelPart(this, 52, 0);
        this.wings2.addBox(0.0F, -0.0F, -1.0F, 1, 8, 4);
        this.wings2.setPos(3.0F, -4 + 16, 0.0F);

        this.neck = new ModelPart(this, 44, 0);
        this.neck.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2);
        this.neck.setPos(0.0F, -2 + 16, -4.0F);

        this.feather1 = new ModelPart(this, 30, 0);
        this.feather1.addBox(-1.0F, -5.0F, 5.0F, 2, 1, 5, -0.3F);
        this.feather1.setPos(0.0F, 1 + 16, 1.0F);
        this.feather2 = new ModelPart(this, 30, 0);
        this.feather2.addBox(-1.0F, -5.0F, 5.0F, 2, 1, 5, -0.3F);
        this.feather2.setPos(0.0F, 1 + 16, 1.0F);
        this.feather3 = new ModelPart(this, 30, 0);
        this.feather3.addBox(-1.0F, -5.0F, 5.0F, 2, 1, 5, -0.3F);
        this.feather3.setPos(0.0F, 1 + 16, 1.0F);
        this.feather1.y += 0.5F;
        this.feather2.y += 0.5F;
        this.feather3.y += 0.5F;
    }

    @Override
    public void setupAnim(CockatriceEntity cockatrice, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch / 57.29578F;
        this.head.yRot = netHeadYaw / 57.29578F;

        this.jaw.xRot = head.xRot;
        this.jaw.yRot = head.yRot;

        this.body.xRot = 1.570796F;

        this.legs.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legs2.xRot = Mth.cos((float)(limbSwing * 0.6662F + Math.PI)) * 1.4F * limbSwingAmount;

        if (ageInTicks > 0.001F) {
            this.wings.z = -1F;
            this.wings2.z = -1F;
            this.wings.y = 12F;
            this.wings2.y = 12F;
            this.wings.xRot = 0.0F;
            this.wings2.xRot = 0.0F;
            this.wings.zRot = ageInTicks;
            this.wings2.zRot = -ageInTicks;
            this.legs.xRot = 0.6F;
            this.legs2.xRot = 0.6F;
        }
        else {
            this.wings.z = -3F;
            this.wings2.z = -3F;
            this.wings.y = 14F;
            this.wings2.y = 14F;
            this.wings.xRot = (float) (Math.PI / 2.0F);
            this.wings2.xRot = (float) (Math.PI / 2.0F);
            this.wings.zRot = 0.0F;
            this.wings2.zRot = 0.0F;
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
        this.legs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.legs2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);

        this.head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.jaw.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wings.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wings2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.neck.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.feather1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.feather2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.feather3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }

}
