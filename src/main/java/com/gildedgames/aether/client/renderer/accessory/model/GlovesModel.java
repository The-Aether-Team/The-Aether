package com.gildedgames.aether.client.renderer.accessory.model;

import com.gildedgames.aether.common.item.accessories.gloves.LeatherGlovesItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class GlovesModel //extends HumanoidModel<LivingEntity>
{
//    public GlovesModel(boolean isSlim) {
//        super(1.0F);
//        this.texWidth = 16;
//        this.texHeight = 16;
//        this.rightArm = new ModelPart(this, 0, 0);
//        this.leftArm = new ModelPart(this, 0, 0);
//        this.leftArm.mirror = true;
//        if (!isSlim) {
//            this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
//            this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.6F);
//            this.leftArm.setPos(5.0F, 2.0F, 0.0F);
//            this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.6F);
//        } else {
//            this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
//            this.rightArm.addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.6F);
//            this.leftArm.setPos(5.0F, 2.0F, 0.0F);
//            this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.6F);
//        }
//    }
//
//    @Override
//    public void renderToBuffer(@Nonnull PoseStack matrixStack, @Nonnull VertexConsumer vertexBuilder, int light, int overlay, float red, float green, float blue, float alpha) {
//        this.rightArm.render(matrixStack, vertexBuilder, light, overlay, red, green, blue, alpha);
//        this.leftArm.render(matrixStack, vertexBuilder, light, overlay, red, green, blue, alpha);
//    }
//
//    public static class WornGlovesModel extends PlayerModel<LivingEntity>
//    {
//        public WornGlovesModel(boolean isSlim) {
//            super(0.0F, isSlim);
//            this.texWidth = 16;
//            this.texHeight = 16;
//            this.rightArm = new ModelPart(this, 0, 0);
//            this.leftArm = new ModelPart(this, 0, 0);
//            this.rightSleeve = new ModelPart(this, 0, 0);
//            this.leftSleeve = new ModelPart(this, 0, 0);
//            this.leftArm.mirror = true;
//            this.leftSleeve.mirror = true;
//            if (!isSlim) {
//                this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F);
//                this.rightArm.setPos(-5.0F, 2.5F, 0.0F);
//                this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F);
//                this.leftArm.setPos(5.0F, 2.0F, 0.0F);
//                this.rightSleeve.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F + 0.25F);
//                this.rightSleeve.setPos(-5.0F, 2.0F, 10.0F);
//                this.leftSleeve.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F + 0.25F);
//                this.leftSleeve.setPos(5.0F, 2.0F, 0.0F);
//            } else {
//                this.rightArm.addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F);
//                this.rightArm.setPos(-5.0F, 2.5F, 0.0F);
//                this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F);
//                this.leftArm.setPos(5.0F, 2.5F, 0.0F);
//                this.rightSleeve.addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F + 0.25F);
//                this.rightSleeve.setPos(-5.0F, 2.5F, 10.0F);
//                this.leftSleeve.addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F + 0.25F);
//                this.leftSleeve.setPos(5.0F, 2.5F, 0.0F);
//            }
//        }
//
//        public void renderGloves(PoseStack matrixStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, ResourceLocation glovesTexture, ItemStack glovesStack, HumanoidArm handSide) {
//            ModelPart armModel = handSide != HumanoidArm.LEFT ? this.rightArm : this.leftArm;
//            ModelPart sleeveModel = handSide != HumanoidArm.LEFT ? this.rightSleeve : this.leftSleeve;
//
//            this.setAllVisible(false);
//            armModel.visible = true;
//            sleeveModel.visible = true;
//
//            this.attackTime  = 0.0F;
//            this.crouching = false;
//            this.swimAmount = 0.0F;
//            this.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
//
//            //TODO: toggle.
//            boolean isSleeve = false;
//
//            float red = 1.0F;
//            float green = 1.0F;
//            float blue = 1.0F;
//
//            if (glovesStack.getItem() instanceof LeatherGlovesItem) {
//                int i = ((LeatherGlovesItem) glovesStack.getItem()).getColor(glovesStack);
//                red = (float) (i >> 16 & 255) / 255.0F;
//                green = (float) (i >> 8 & 255) / 255.0F;
//                blue = (float) (i & 255) / 255.0F;
//            }
//
//            if (!isSleeve) {
//                armModel.xRot = 0.0F;
//                armModel.render(matrixStack, ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(glovesTexture), false, glovesStack.isEnchanted()), light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
//            } else {
//                sleeveModel.xRot = 0.0F;
//                sleeveModel.render(matrixStack, ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(glovesTexture), false, glovesStack.isEnchanted()), light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
//            }
//        }
//    }
}
