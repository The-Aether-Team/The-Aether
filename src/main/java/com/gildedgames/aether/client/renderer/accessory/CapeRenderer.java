package com.gildedgames.aether.client.renderer.accessory;

import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.accessory.model.CapeModel;
import com.gildedgames.aether.common.item.accessories.cape.CapeItem;
import com.gildedgames.aether.core.capability.interfaces.ICapeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.caelus.mixin.util.ClientMixinHooks;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CapeRenderer implements ICurioRenderer
{
    private final CapeModel capeModel;

    public CapeRenderer() {
        this.capeModel = new CapeModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.CAPE));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        LivingEntity livingEntity = slotContext.entity();
        CapeItem capeItem = (CapeItem) stack.getItem();
        ICapeEntity.get(livingEntity).ifPresent((capeEntity) -> {
            if (!livingEntity.isInvisible()) {
                ItemStack itemstack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
                boolean hasCape = livingEntity instanceof AbstractClientPlayer abstractClientPlayer
                        && abstractClientPlayer.isCapeLoaded()
                        && abstractClientPlayer.getCloakTextureLocation() != null
                        && abstractClientPlayer.isModelPartShown(PlayerModelPart.CAPE);
                boolean isCapeDisabled = ModList.get().isLoaded("caelus") && livingEntity instanceof Player player && !ClientMixinHooks.canRenderCape(player);

                if (!(itemstack.getItem() instanceof ElytraItem) && !hasCape && !isCapeDisabled) {
                    this.capeModel.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                    poseStack.pushPose();
                    poseStack.translate(0.0D, 0.0D, 0.125D);
                    double d0 = Mth.lerp(partialTicks, capeEntity.getxCloakO(), capeEntity.getxCloak()) - Mth.lerp(partialTicks, livingEntity.xo, livingEntity.getX());
                    double d1 = Mth.lerp(partialTicks, capeEntity.getyCloakO(), capeEntity.getyCloak()) - Mth.lerp(partialTicks, livingEntity.yo, livingEntity.getY());
                    double d2 = Mth.lerp(partialTicks, capeEntity.getzCloakO(), capeEntity.getzCloak()) - Mth.lerp(partialTicks, livingEntity.zo, livingEntity.getZ());
                    float f = livingEntity.yBodyRotO + (livingEntity.yBodyRot - livingEntity.yBodyRotO);
                    double d3 = Mth.sin(f * ((float) Math.PI / 180F));
                    double d4 = -Mth.cos(f * ((float) Math.PI / 180F));
                    float f1 = (float) d1 * 10.0F;
                    f1 = Mth.clamp(f1, -6.0F, 32.0F);
                    float f2 = (float) (d0 * d3 + d2 * d4) * 100.0F;
                    f2 = Mth.clamp(f2, 0.0F, 150.0F);
                    float f3 = (float) (d0 * d4 - d2 * d3) * 100.0F;
                    f3 = Mth.clamp(f3, -20.0F, 20.0F);
                    if (f2 < 0.0F) {
                        f2 = 0.0F;
                    }

                    float f4 = Mth.lerp(partialTicks, capeEntity.getoBob(), capeEntity.getBob());
                    f1 += Mth.sin(Mth.lerp(partialTicks, livingEntity.walkDistO, livingEntity.walkDist) * 6.0F) * 32.0F * f4;
                    if (livingEntity.isCrouching()) {
                        f1 += 25.0F;
                    }

                    poseStack.mulPose(Vector3f.XP.rotationDegrees(6.0F + f2 / 2.0F + f1));
                    poseStack.mulPose(Vector3f.ZP.rotationDegrees(f3 / 2.0F));
                    poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - f3 / 2.0F));
                    VertexConsumer vertexconsumer = renderTypeBuffer.getBuffer(RenderType.entitySolid(capeItem.getCapeTexture()));
                    this.capeModel.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                    poseStack.popPose();
                }
            }
        });
    }
}
