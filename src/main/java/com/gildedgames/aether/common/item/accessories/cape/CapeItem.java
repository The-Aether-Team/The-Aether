package com.gildedgames.aether.common.item.accessories.cape;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.client.renderer.accessory.model.CapeModel;
import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import com.gildedgames.aether.core.capability.interfaces.ICapeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import com.mojang.math.Vector3f;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.caelus.mixin.util.ClientMixinHooks;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;

public class CapeItem extends AccessoryItem
{
    protected ResourceLocation CAPE_LOCATION;

    public CapeItem(String capeLocation, Properties properties) {
        super(properties);
        this.setRenderTexture(Aether.MODID, capeLocation);
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_CAPE.get(), 1.0f, 1.0f);
    }

    /*@Override  TODO: Is this necessary?
    public boolean canRender(String identifier, int index, LivingEntity living, ItemStack stack) {
        return true;
    }*/

    @Override
    public void renderModel(HumanoidModel<?> model, String identifier, int index, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        ICapeEntity.get(livingEntity).ifPresent((capeEntity) -> {
            CapeModel cape = new CapeModel();
            if (!livingEntity.isInvisible()) {
                ItemStack itemstack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
                boolean hasCape = livingEntity instanceof AbstractClientPlayer
                        && ((AbstractClientPlayer) livingEntity).isCapeLoaded()
                        && ((AbstractClientPlayer) livingEntity).getCloakTextureLocation() != null
                        && ((AbstractClientPlayer) livingEntity).isModelPartShown(PlayerModelPart.CAPE);
                boolean isCapeDisabled = ModList.get().isLoaded("caelus") && livingEntity instanceof Player && !ClientMixinHooks.canRenderCape((Player) livingEntity);

                if (!(itemstack.getItem() instanceof ElytraItem) && !hasCape && !isCapeDisabled) {
                    cape.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                    matrixStack.pushPose();
                    matrixStack.translate(0.0D, 0.0D, 0.125D);
                    double d0 = Mth.lerp(partialTicks, capeEntity.getxCloakO(), capeEntity.getxCloak()) - Mth.lerp(partialTicks, livingEntity.xo, livingEntity.getX());
                    double d1 = Mth.lerp(partialTicks, capeEntity.getyCloakO(), capeEntity.getyCloak()) - Mth.lerp(partialTicks, livingEntity.yo, livingEntity.getY());
                    double d2 = Mth.lerp(partialTicks, capeEntity.getzCloakO(), capeEntity.getzCloak()) - Mth.lerp(partialTicks, livingEntity.zo, livingEntity.getZ());
                    float f = livingEntity.yBodyRotO + (livingEntity.yBodyRot - livingEntity.yBodyRotO);
                    double d3 = Mth.sin(f * ((float)Math.PI / 180F));
                    double d4 = -Mth.cos(f * ((float)Math.PI / 180F));
                    float f1 = (float)d1 * 10.0F;
                    f1 = Mth.clamp(f1, -6.0F, 32.0F);
                    float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
                    f2 = Mth.clamp(f2, 0.0F, 150.0F);
                    float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
                    f3 = Mth.clamp(f3, -20.0F, 20.0F);
                    if (f2 < 0.0F) {
                        f2 = 0.0F;
                    }

                    float f4 = Mth.lerp(partialTicks, capeEntity.getoBob(), capeEntity.getBob());
                    f1 = f1 + Mth.sin(Mth.lerp(partialTicks, livingEntity.walkDistO, livingEntity.walkDist) * 6.0F) * 32.0F * f4;
                    if (livingEntity.isCrouching()) {
                        f1 += 25.0F;
                    }

                    matrixStack.mulPose(Vector3f.XP.rotationDegrees(6.0F + f2 / 2.0F + f1));
                    matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f3 / 2.0F));
                    matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - f3 / 2.0F));
                    VertexConsumer ivertexbuilder = renderTypeBuffer.getBuffer(RenderType.entitySolid(this.CAPE_LOCATION));
                    cape.renderToBuffer(matrixStack, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                    matrixStack.popPose();
                }
            }
        });
    }

    public void setRenderTexture(String modId, String registryName) {
        this.CAPE_LOCATION = new ResourceLocation(modId, "textures/models/accessory/capes/" + registryName + "_accessory.png");
    }

    public ResourceLocation getCapeTexture() {
        return this.CAPE_LOCATION;
    }
}
