package com.gildedgames.aether.common.item.accessories.misc;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.accessory.model.GlovesModel;
import com.gildedgames.aether.client.renderer.accessory.model.RepulsionShieldModel;
import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class RepulsionShieldItem extends AccessoryItem
{
    private static final ResourceLocation REPULSION_SHIELD = new ResourceLocation(Aether.MODID, "textures/models/accessory/repulsion_shield_accessory.png");
    private static final ResourceLocation REPULSION_SHIELD_INACTIVE = new ResourceLocation(Aether.MODID, "textures/models/accessory/repulsion_shield_inactive_accessory.png");

    public RepulsionShieldItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity living, ItemStack stack) {
        return true;
    }

    @Override
    public void renderModel(BipedModel<?> model, String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        RepulsionShieldModel shield = new RepulsionShieldModel();
        ResourceLocation texture;
        Vector3d motion = livingEntity.getDeltaMovement();

        if (motion.x() == 0.0 && (motion.y() == -0.0784000015258789 || motion.y() == 0.0) && motion.z() == 0.0) {
            texture = this.getRepulsionShieldTexture();
        } else {
            texture = this.getRepulsionShieldInactiveTexture();
        }

        IVertexBuilder vertexBuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.entityTranslucent(texture), false, stack.isEnchanted());
        shield.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
        shield.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        ICurio.RenderHelper.followBodyRotations(livingEntity, shield);
        shield.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public ResourceLocation getRepulsionShieldTexture() {
        return REPULSION_SHIELD;
    }

    public ResourceLocation getRepulsionShieldInactiveTexture() {
        return REPULSION_SHIELD_INACTIVE;
    }
}
