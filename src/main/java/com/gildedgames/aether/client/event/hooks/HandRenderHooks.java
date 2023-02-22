package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.capability.player.AetherPlayer;
import com.gildedgames.aether.client.renderer.accessory.GlovesRenderer;
import com.gildedgames.aether.client.renderer.accessory.ShieldOfRepulsionRenderer;
import com.gildedgames.aether.item.accessories.gloves.GlovesItem;
import com.gildedgames.aether.item.accessories.miscellaneous.ShieldOfRepulsionItem;
import com.gildedgames.aether.mixin.mixins.client.accessor.ItemInHandRendererAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class HandRenderHooks {
    public static void renderGloveHandOverlay(ItemInHandRenderer itemInHandRenderer, AbstractClientPlayer player, InteractionHand hand, float pitch, float swingProgress, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
        if (player != null) {
            AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                if (!aetherPlayer.isWearingInvisibilityCloak()) {
                    CuriosApi.getCuriosHelper().findFirstCurio(player, (item) -> item.getItem() instanceof GlovesItem).ifPresent((slotResult) -> {
                        String identifier = slotResult.slotContext().identifier();
                        int id = slotResult.slotContext().index();
                        ItemStack itemStack = slotResult.stack();
                        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> handler.getStacksHandler(identifier).ifPresent(stacksHandler -> {
                            if (stacksHandler.getRenders().get(id)) {
                                CuriosRendererRegistry.getRenderer(itemStack.getItem()).ifPresent((renderer) -> {
                                    if (renderer instanceof GlovesRenderer glovesRenderer) {
                                        ItemStack heldItem = hand == InteractionHand.MAIN_HAND ? ((ItemInHandRendererAccessor) itemInHandRenderer).aether$getMainHandItem() : ((ItemInHandRendererAccessor) itemInHandRenderer).aether$getOffHandItem();
                                        renderArmWithItem(itemInHandRenderer, glovesRenderer, itemStack, player, heldItem, hand, pitch, swingProgress, equippedProgress, poseStack, buffer, combinedLight, HandRenderType.GLOVES);
                                    }
                                });
                            }
                        }));
                    });
                }
            });
        }
    }

    public static void renderShieldOfRepulsionHandOverlay(ItemInHandRenderer itemInHandRenderer, AbstractClientPlayer player, InteractionHand hand, float pitch, float swingProgress, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
        if (player != null) {
            AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                if (aetherPlayer.isWearingInvisibilityCloak()) {
                    CuriosApi.getCuriosHelper().findFirstCurio(player, (item) -> item.getItem() instanceof ShieldOfRepulsionItem).ifPresent((slotResult) -> {
                        String identifier = slotResult.slotContext().identifier();
                        int id = slotResult.slotContext().index();
                        ItemStack itemStack = slotResult.stack();
                        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> handler.getStacksHandler(identifier).ifPresent(stacksHandler -> {
                            if (stacksHandler.getRenders().get(id)) {
                                CuriosRendererRegistry.getRenderer(itemStack.getItem()).ifPresent((renderer) -> {
                                    if (renderer instanceof ShieldOfRepulsionRenderer shieldRenderer) {
                                        ItemStack heldItem = hand == InteractionHand.MAIN_HAND ? ((ItemInHandRendererAccessor) itemInHandRenderer).aether$getMainHandItem() : ((ItemInHandRendererAccessor) itemInHandRenderer).aether$getOffHandItem();
                                        renderArmWithItem(itemInHandRenderer, shieldRenderer, itemStack, player, heldItem, hand, pitch, swingProgress, equippedProgress, poseStack, buffer, combinedLight, HandRenderType.SHIELD_OF_REPULSION);
                                    }
                                });
                            }
                        }));
                    });
                }
            });
        }
    }

    /**
     * Based on {@link ItemInHandRenderer#renderArmWithItem(AbstractClientPlayer, float, float, InteractionHand, float, ItemStack, float, PoseStack, MultiBufferSource, int)}.
     */
    private static void renderArmWithItem(ItemInHandRenderer itemInHandRenderer, ICurioRenderer renderer, ItemStack glovesStack, AbstractClientPlayer player, ItemStack heldItem, InteractionHand hand, float pitch, float swingProgress, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, HandRenderType handRenderType) {
        if (!player.isScoping()) {
            boolean isMainHand = hand == InteractionHand.MAIN_HAND;
            HumanoidArm humanoidarm = isMainHand ? player.getMainArm() : player.getMainArm().getOpposite();
            poseStack.pushPose();
            if (heldItem.isEmpty()) {
                if (isMainHand) {
                    renderPlayerArm(renderer, glovesStack, player, poseStack, buffer, combinedLight, swingProgress, equippedProgress, humanoidarm, handRenderType);
                }
            } else if (heldItem.is(Items.FILLED_MAP)) {
                if (isMainHand && ((ItemInHandRendererAccessor) itemInHandRenderer).aether$getOffHandItem().isEmpty()) {
                    renderTwoHandedMap(itemInHandRenderer, renderer, glovesStack, player, heldItem, poseStack, buffer, combinedLight, swingProgress, equippedProgress, pitch, handRenderType);
                } else {
                    renderOneHandedMap(itemInHandRenderer, renderer, glovesStack, player, heldItem, poseStack, buffer, combinedLight, swingProgress, equippedProgress, humanoidarm, handRenderType);
                }
            }
            poseStack.popPose();
        }
    }

    /**
     * Based on {@link ItemInHandRenderer#renderPlayerArm(PoseStack, MultiBufferSource, int, float, float, HumanoidArm)}.
     */
    private static void renderPlayerArm(ICurioRenderer renderer, ItemStack glovesStack, AbstractClientPlayer player, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, float swingProgress, float equippedProgress, HumanoidArm arm, HandRenderType handRenderType) {
        boolean flag = arm != HumanoidArm.LEFT;
        float f = flag ? 1.0F : -1.0F;
        float f1 = Mth.sqrt(swingProgress);
        float f2 = -0.3F * Mth.sin(f1 * (float) Math.PI);
        float f3 = 0.4F * Mth.sin(f1 * ((float) Math.PI * 2.0F));
        float f4 = -0.4F * Mth.sin(swingProgress * (float) Math.PI);
        poseStack.translate(f * (f2 + 0.64000005F), f3 - 0.6F + equippedProgress * -0.6F, f4 - 0.71999997F);
        poseStack.mulPose(Axis.YP.rotationDegrees(f * 45.0F));
        float f5 = Mth.sin(swingProgress * swingProgress * (float) Math.PI);
        float f6 = Mth.sin(f1 * (float) Math.PI);
        poseStack.mulPose(Axis.YP.rotationDegrees(f * f6 * 70.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(f * f5 * -20.0F));
        poseStack.translate(f * -1.0F, 3.6F, 3.5F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(f * 120.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(200.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(f * -135.0F));
        poseStack.translate(f * 5.6F, 0.0F, 0.0F);
        switch (handRenderType) {
            case GLOVES -> ((GlovesRenderer) renderer).renderFirstPerson(glovesStack, poseStack, buffer, combinedLight, player, arm);
            case SHIELD_OF_REPULSION -> ((ShieldOfRepulsionRenderer) renderer).renderFirstPerson(glovesStack, poseStack, buffer, combinedLight, player, arm);
        }
    }

    /**
     * Based on {@link ItemInHandRenderer#renderTwoHandedMap(PoseStack, MultiBufferSource, int, float, float, float)}.
     */
    private static void renderTwoHandedMap(ItemInHandRenderer itemInHandRenderer, ICurioRenderer renderer, ItemStack glovesStack, AbstractClientPlayer player, ItemStack heldItem, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, float swingProgress, float equippedProgress, float pitch, HandRenderType handRenderType) {
        float f = Mth.sqrt(swingProgress);
        float f1 = -0.2F * Mth.sin(swingProgress * (float) Math.PI);
        float f2 = -0.4F * Mth.sin(f * (float) Math.PI);
        poseStack.translate(0.0F, -f1 / 2.0F, f2);
        float f3 = ((ItemInHandRendererAccessor) itemInHandRenderer).callCalculateMapTilt(pitch);
        poseStack.translate(0.0F, 0.04F + equippedProgress * -1.2F + f3 * -0.5F, -0.72F);
        poseStack.mulPose(Axis.XP.rotationDegrees(f3 * -85.0F));

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        renderMapHand(renderer, glovesStack, player, poseStack, buffer, combinedLight, HumanoidArm.RIGHT, handRenderType);
        renderMapHand(renderer, glovesStack, player, poseStack, buffer, combinedLight, HumanoidArm.LEFT, handRenderType);
        poseStack.popPose();

        float f4 = Mth.sin(f * (float)Math.PI);
        poseStack.mulPose(Axis.XP.rotationDegrees(f4 * 20.0F));
        poseStack.scale(2.0F, 2.0F, 2.0F);
        ((ItemInHandRendererAccessor) itemInHandRenderer).callRenderMap(poseStack, buffer, combinedLight, heldItem);
    }

    /**
     * Based on {@link ItemInHandRenderer#renderOneHandedMap(PoseStack, MultiBufferSource, int, float, HumanoidArm, float, ItemStack)}.
     */
    private static void renderOneHandedMap(ItemInHandRenderer itemInHandRenderer, ICurioRenderer renderer, ItemStack glovesStack, AbstractClientPlayer player, ItemStack heldItem, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, float swingProgress, float equippedProgress, HumanoidArm arm, HandRenderType handRenderType) {
        float f = arm == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        poseStack.translate(f * 0.125F, -0.125F, 0.0F);

        poseStack.pushPose();
        poseStack.mulPose(Axis.ZP.rotationDegrees(f * 10.0F));
        renderPlayerArm(renderer, glovesStack, player, poseStack, buffer, combinedLight, swingProgress, equippedProgress, arm, handRenderType);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(f * 0.51F, -0.08F + equippedProgress * -1.2F, -0.75F);
        float f1 = Mth.sqrt(swingProgress);
        float f2 = Mth.sin(f1 * (float) Math.PI);
        float f3 = -0.5F * f2;
        float f4 = 0.4F * Mth.sin(f1 * ((float) Math.PI * 2F));
        float f5 = -0.3F * Mth.sin(swingProgress * (float) Math.PI);
        poseStack.translate(f * f3, f4 - 0.3F * f2, f5);
        poseStack.mulPose(Axis.XP.rotationDegrees(f2 * -45.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(f * f2 * -30.0F));
        ((ItemInHandRendererAccessor) itemInHandRenderer).callRenderMap(poseStack, buffer, combinedLight, heldItem);
        poseStack.popPose();
    }

    /**
     * Based on {@link ItemInHandRenderer#renderMapHand(PoseStack, MultiBufferSource, int, HumanoidArm)}.
     */
    private static void renderMapHand(ICurioRenderer renderer, ItemStack glovesStack, AbstractClientPlayer player, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, HumanoidArm arm, HandRenderType handRenderType) {
        poseStack.pushPose();
        float f = arm == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        poseStack.mulPose(Axis.YP.rotationDegrees(92.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(f * -41.0F));
        poseStack.translate(f * 0.3F, -1.1F, 0.45F);
        switch (handRenderType) {
            case GLOVES -> ((GlovesRenderer) renderer).renderFirstPerson(glovesStack, poseStack, buffer, combinedLight, player, arm);
            case SHIELD_OF_REPULSION -> ((ShieldOfRepulsionRenderer) renderer).renderFirstPerson(glovesStack, poseStack, buffer, combinedLight, player, arm);
        }
        poseStack.popPose();
    }

    enum HandRenderType {
        GLOVES,
        SHIELD_OF_REPULSION
    }
}
