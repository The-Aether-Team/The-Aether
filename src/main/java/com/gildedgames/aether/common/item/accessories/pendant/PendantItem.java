package com.gildedgames.aether.common.item.accessories.pendant;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.accessory.model.PendantModel;
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
import net.minecraft.util.SoundEvent;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

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

    @Override
    public boolean canRender(String identifier, int index, LivingEntity living, ItemStack stack) {
        return true;
    }

    @Override
    public void renderModel(BipedModel<?> model, String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        PendantModel pendant = new PendantModel();

        ICurio.RenderHelper.followBodyRotations(livingEntity, pendant);

        IVertexBuilder vertexBuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(this.getPendantTexture()), false, stack.isEnchanted());
        pendant.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void setRenderTexture(String modId, String registryName) {
        this.PENDANT_LOCATION = new ResourceLocation(modId, "textures/models/accessory/pendant/" + registryName + "_accessory.png");
    }

    public ResourceLocation getPendantTexture() {
        return this.PENDANT_LOCATION;
    }
}
