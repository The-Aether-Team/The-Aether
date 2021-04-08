package com.gildedgames.aether.common.item.accessories.cape;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.accessory.model.CapeModel;
import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import com.gildedgames.aether.core.capability.capabilities.cape.CapeEntity;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.capability.interfaces.ICapeEntity;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.JumpPacket;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CapeItem extends AccessoryItem
{
    protected ResourceLocation CAPE_LOCATION;

    public CapeItem(String capeLocation, Properties properties) {
        super(properties);
        this.setRenderTexture(Aether.MODID, capeLocation);
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity living, ItemStack stack) {
        return true;
    }

    @Override
    public void renderModel(BipedModel<?> model, String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        ICapeEntity.get(livingEntity).ifPresent((capeEntity) -> {
            CapeModel cape = new CapeModel();
            if (!livingEntity.isInvisible()) {
                ItemStack itemstack = livingEntity.getItemBySlot(EquipmentSlotType.CHEST);
                if (itemstack.getItem() != Items.ELYTRA) {
                    ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
                    
                    matrixStack.pushPose();
                    matrixStack.translate(0.0D, 0.0D, 0.125D);
                    double d0 = MathHelper.lerp(partialTicks, capeEntity.getxCloakO(), capeEntity.getxCloak()) - MathHelper.lerp(partialTicks, livingEntity.xo, livingEntity.getX());
                    double d1 = MathHelper.lerp(partialTicks, capeEntity.getyCloakO(), capeEntity.getyCloak()) - MathHelper.lerp(partialTicks, livingEntity.yo, livingEntity.getY());
                    double d2 = MathHelper.lerp(partialTicks, capeEntity.getzCloakO(), capeEntity.getzCloak()) - MathHelper.lerp(partialTicks, livingEntity.zo, livingEntity.getZ());
                    float f = livingEntity.yBodyRotO + (livingEntity.yBodyRot - livingEntity.yBodyRotO);
                    double d3 = MathHelper.sin(f * ((float)Math.PI / 180F));
                    double d4 = -MathHelper.cos(f * ((float)Math.PI / 180F));
                    float f1 = (float)d1 * 10.0F;
                    f1 = MathHelper.clamp(f1, -6.0F, 32.0F);
                    float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
                    f2 = MathHelper.clamp(f2, 0.0F, 150.0F);
                    float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
                    f3 = MathHelper.clamp(f3, -20.0F, 20.0F);
                    if (f2 < 0.0F) {
                        f2 = 0.0F;
                    }

                    float f4 = MathHelper.lerp(partialTicks, capeEntity.getoBob(), capeEntity.getBob());
                    f1 = f1 + MathHelper.sin(MathHelper.lerp(partialTicks, livingEntity.walkDistO, livingEntity.walkDist) * 6.0F) * 32.0F * f4;
                    if (livingEntity.isCrouching()) {
                        f1 += 25.0F;
                    }

                    matrixStack.mulPose(Vector3f.XP.rotationDegrees(6.0F + f2 / 2.0F + f1));
                    matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f3 / 2.0F));
                    matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - f3 / 2.0F));
                    IVertexBuilder ivertexbuilder = renderTypeBuffer.getBuffer(RenderType.entitySolid(this.CAPE_LOCATION));
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
