package com.gildedgames.aether.common.item.accessories.gloves;

import com.gildedgames.aether.client.renderer.accessory.model.GlovesModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class LeatherGlovesItem extends GlovesItem implements IDyeableArmorItem
{
    public LeatherGlovesItem(double punchDamage, Properties properties) {
        super(punchDamage, "leather_gloves", properties);
    }

    @Override
    public void renderModel(BipedModel<?> model, String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        GlovesModel gloves;
        IVertexBuilder vertexBuilder;

        if (!(model instanceof PlayerModel<?>)) {
            gloves = new GlovesModel(false);
            vertexBuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(this.GLOVES_TEXTURE), false, stack.isEnchanted());
        }
        else {
            PlayerModel<?> playerModel = (PlayerModel<?>) model;
            gloves = new GlovesModel(playerModel.slim);
            vertexBuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(this.GLOVES_SLIM_TEXTURE), false, stack.isEnchanted());
        }

        gloves.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
        gloves.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        ICurio.RenderHelper.followBodyRotations(livingEntity, gloves);

        int i = ((LeatherGlovesItem) stack.getItem()).getColor(stack);
        float red = (float) (i >> 16 & 255) / 255.0F;
        float green = (float) (i >> 8 & 255) / 255.0F;
        float blue = (float) (i & 255) / 255.0F;

        gloves.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }
}
