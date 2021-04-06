package com.gildedgames.aether.common.item.accessories.pendant;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class PendantItem extends AccessoryItem
{
    protected final ResourceLocation PENDANT_LOCATION;

    public PendantItem(String pendantLocation, Properties properties) {
        super(properties);
        this.PENDANT_LOCATION = new ResourceLocation(Aether.MODID, "textures/models/accessory/pendant/" + pendantLocation + "_accessory.png");
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity living, ItemStack stack) {
        return true;
    }

    @Override
    public void renderModel(BipedModel<?> model, String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        matrixStack.scale(1.1F, 1.1F, 1.1F);
        if (model == null) {
            model = new BipedModel<>(1.0F);
        }
        IVertexBuilder buffer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(this.PENDANT_LOCATION), false, stack.isEnchanted());
        model.body.render(matrixStack, buffer, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}
