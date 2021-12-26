package com.gildedgames.aether.common.item.accessories.gloves;

import com.gildedgames.aether.client.renderer.accessory.model.GlovesModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvents;
import top.theillusivec4.curios.api.type.capability.ICurio;

import net.minecraft.world.item.Item.Properties;

public class LeatherGlovesItem extends GlovesItem implements DyeableLeatherItem
{
    public LeatherGlovesItem(double punchDamage, Properties properties) {
        super(punchDamage, "leather_gloves", () -> SoundEvents.ARMOR_EQUIP_LEATHER, properties);
    }

//    @Override
//    public void renderModel(HumanoidModel<?> model, String identifier, int index, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
//        GlovesModel gloves;
//        VertexConsumer vertexBuilder;
//
//        if (!(model instanceof PlayerModel<?>)) {
//            gloves = new GlovesModel(false);
//            vertexBuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(this.getGlovesTexture()), false, stack.isEnchanted());
//        }
//        else {
//            PlayerModel<?> playerModel = (PlayerModel<?>) model;
//            gloves = new GlovesModel(playerModel.slim);
//            vertexBuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(playerModel.slim ? this.getGlovesSlimTexture() : this.getGlovesTexture()), false, stack.isEnchanted());
//        }
//
//        gloves.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
//        gloves.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
//        ICurio.RenderHelper.followBodyRotations(livingEntity, gloves);
//
//        int i = ((LeatherGlovesItem) stack.getItem()).getColor(stack);
//        float red = (float) (i >> 16 & 255) / 255.0F;
//        float green = (float) (i >> 8 & 255) / 255.0F;
//        float blue = (float) (i & 255) / 255.0F;
//
//        gloves.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
//    }
}
