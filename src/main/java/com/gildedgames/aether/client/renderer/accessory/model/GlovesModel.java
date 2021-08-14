package com.gildedgames.aether.client.renderer.accessory.model;

import com.gildedgames.aether.common.item.accessories.gloves.LeatherGlovesItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class GlovesModel extends BipedModel<LivingEntity>
{
    public GlovesModel(boolean isSlim) {
        super(1.0F);
        this.texWidth = 16;
        this.texHeight = 16;
        this.rightArm = new ModelRenderer(this, 0, 0);
        this.leftArm = new ModelRenderer(this, 0, 0);
        this.leftArm.mirror = true;
        if (!isSlim) {
            this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
            this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.6F);
            this.leftArm.setPos(5.0F, 2.0F, 0.0F);
            this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.6F);
        } else {
            this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
            this.rightArm.addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.6F);
            this.leftArm.setPos(5.0F, 2.0F, 0.0F);
            this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.6F);
        }
    }

    @Override
    public void renderToBuffer(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder vertexBuilder, int light, int overlay, float red, float green, float blue, float alpha) {
        this.rightArm.render(matrixStack, vertexBuilder, light, overlay, red, green, blue, alpha);
        this.leftArm.render(matrixStack, vertexBuilder, light, overlay, red, green, blue, alpha);
    }

    public static class WornGlovesModel extends PlayerModel<LivingEntity>
    {
        public WornGlovesModel(boolean isSlim) {
            super(0.0F, isSlim);
            this.texWidth = 16;
            this.texHeight = 16;
            this.rightArm = new ModelRenderer(this, 0, 0);
            this.leftArm = new ModelRenderer(this, 0, 0);
            this.rightSleeve = new ModelRenderer(this, 0, 0);
            this.leftSleeve = new ModelRenderer(this, 0, 0);
            this.leftArm.mirror = true;
            this.leftSleeve.mirror = true;
            if (!isSlim) {
                this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F);
                this.rightArm.setPos(-5.0F, 2.5F, 0.0F);
                this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F);
                this.leftArm.setPos(5.0F, 2.0F, 0.0F);
                this.rightSleeve.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F + 0.25F);
                this.rightSleeve.setPos(-5.0F, 2.0F, 10.0F);
                this.leftSleeve.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F + 0.25F);
                this.leftSleeve.setPos(5.0F, 2.0F, 0.0F);
            } else {
                this.rightArm.addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F);
                this.rightArm.setPos(-5.0F, 2.5F, 0.0F);
                this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F);
                this.leftArm.setPos(5.0F, 2.5F, 0.0F);
                this.rightSleeve.addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F + 0.25F);
                this.rightSleeve.setPos(-5.0F, 2.5F, 10.0F);
                this.leftSleeve.addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F + 0.25F);
                this.leftSleeve.setPos(5.0F, 2.5F, 0.0F);
            }
        }

        public void renderGloves(MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, AbstractClientPlayerEntity player, ResourceLocation glovesTexture, ItemStack glovesStack, HandSide handSide) {
            ModelRenderer armModel = handSide != HandSide.LEFT ? this.rightArm : this.leftArm;
            ModelRenderer sleeveModel = handSide != HandSide.LEFT ? this.rightSleeve : this.leftSleeve;

            this.setAllVisible(false);
            armModel.visible = true;
            sleeveModel.visible = true;

            this.attackTime  = 0.0F;
            this.crouching = false;
            this.swimAmount = 0.0F;
            this.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

            //TODO: toggle.
            boolean isSleeve = false;

            float red = 1.0F;
            float green = 1.0F;
            float blue = 1.0F;

            if (glovesStack.getItem() instanceof LeatherGlovesItem) {
                int i = ((LeatherGlovesItem) glovesStack.getItem()).getColor(glovesStack);
                red = (float) (i >> 16 & 255) / 255.0F;
                green = (float) (i >> 8 & 255) / 255.0F;
                blue = (float) (i & 255) / 255.0F;
            }

            if (!isSleeve) {
                armModel.xRot = 0.0F;
                armModel.render(matrixStack, ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(glovesTexture), false, glovesStack.isEnchanted()), light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
            } else {
                sleeveModel.xRot = 0.0F;
                sleeveModel.render(matrixStack, ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(glovesTexture), false, glovesStack.isEnchanted()), light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
            }
        }
    }
}
