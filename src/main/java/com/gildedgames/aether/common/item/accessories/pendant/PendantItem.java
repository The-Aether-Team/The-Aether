package com.gildedgames.aether.common.item.accessories.pendant;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.accessory.model.PendantModel;
import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

import net.minecraft.world.item.Item.Properties;

public class PendantItem extends AccessoryItem
{
    protected ResourceLocation PENDANT_LOCATION;
    protected final Supplier<SoundEvent> equipSound;

    public PendantItem(String pendantLocation, Supplier<SoundEvent> pendantSound, Properties properties) {
        super(properties);
        this.setRenderTexture(Aether.MODID, pendantLocation);
        this.equipSound = pendantSound;
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(this.equipSound.get(), 1.0f, 1.0f);
    }

//    @Override
//    public boolean canRender(String identifier, int index, LivingEntity living, ItemStack stack) {
//        return true;
//    }
//
//    @Override
//    public void renderModel(HumanoidModel<?> model, String identifier, int index, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
//        PendantModel pendant = new PendantModel();
//
//        ICurio.RenderHelper.followBodyRotations(livingEntity, pendant);
//
//        VertexConsumer vertexBuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(this.getPendantTexture()), false, stack.isEnchanted());
//        pendant.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
//    }

    public void setRenderTexture(String modId, String registryName) {
        this.PENDANT_LOCATION = new ResourceLocation(modId, "textures/models/accessory/pendant/" + registryName + "_accessory.png");
    }

    public ResourceLocation getPendantTexture() {
        return this.PENDANT_LOCATION;
    }
}
