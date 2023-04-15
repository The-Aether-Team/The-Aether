package com.gildedgames.aether.client.renderer.accessory;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.AetherModelLayers;
import com.gildedgames.aether.client.renderer.accessory.model.CapeModel;
import com.gildedgames.aether.item.accessories.cape.CapeItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
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

public class CapeRenderer implements ICurioRenderer {
    private final CapeModel cape;

    public CapeRenderer() {
        this.cape = new CapeModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.CAPE));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        LivingEntity livingEntity = slotContext.entity();
        CapeItem capeItem = (CapeItem) stack.getItem();
        ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
        if (!livingEntity.isInvisible()) {
            if (livingEntity instanceof Player player) {
                boolean hasCape = player instanceof AbstractClientPlayer abstractClientPlayer
                        && abstractClientPlayer.isCapeLoaded()
                        && abstractClientPlayer.getCloakTextureLocation() != null
                        && abstractClientPlayer.isModelPartShown(PlayerModelPart.CAPE);
                boolean isCapeDisabled = ModList.get().isLoaded("caelus") && !ClientMixinHooks.canRenderCape(player);
                if (!(itemStack.getItem() instanceof ElytraItem) && !hasCape && !isCapeDisabled) {
                    this.cape.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                    poseStack.pushPose();
                    poseStack.translate(0.0D, 0.0D, 0.125D);
                    double d0 = Mth.lerp(partialTicks, player.xCloakO, player.xCloak) - Mth.lerp(partialTicks, livingEntity.xo, livingEntity.getX());
                    double d1 = Mth.lerp(partialTicks, player.yCloakO, player.yCloak) - Mth.lerp(partialTicks, livingEntity.yo, livingEntity.getY());
                    double d2 = Mth.lerp(partialTicks, player.zCloakO, player.zCloak) - Mth.lerp(partialTicks, livingEntity.zo, livingEntity.getZ());
                    float f = Mth.rotLerp(partialTicks, player.yBodyRotO, player.yBodyRot);
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

                    float f4 = Mth.lerp(partialTicks, player.oBob, player.bob);
                    f1 += Mth.sin(Mth.lerp(partialTicks, livingEntity.walkDistO, livingEntity.walkDist) * 6.0F) * 32.0F * f4;
                    if (livingEntity.isCrouching()) {
                        f1 += 25.0F;
                    }

                    poseStack.mulPose(Axis.XP.rotationDegrees(6.0F + f2 / 2.0F + f1));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(f3 / 2.0F));
                    poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - f3 / 2.0F));
                    this.renderCape(poseStack, buffer, stack, capeItem, light);
                    poseStack.popPose();
                }
            } else {
                this.cape.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                poseStack.pushPose();
                poseStack.translate(0.0D, 0.0D, 0.125D);
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                this.renderCape(poseStack, buffer, stack, capeItem, light);
                poseStack.popPose();
            }
        }
    }

    private void renderCape(PoseStack poseStack, MultiBufferSource buffer, ItemStack stack, CapeItem capeItem, int light) {
        VertexConsumer consumer;
        if (stack.getHoverName().getString().equalsIgnoreCase("swuff_'s cape")) {
            consumer = buffer.getBuffer(RenderType.entitySolid(new ResourceLocation(Aether.MODID, "textures/models/accessory/capes/swuff_accessory.png")));
        } else {
            consumer = buffer.getBuffer(RenderType.entitySolid(capeItem.getCapeTexture()));
        }
        this.cape.renderToBuffer(poseStack, consumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
