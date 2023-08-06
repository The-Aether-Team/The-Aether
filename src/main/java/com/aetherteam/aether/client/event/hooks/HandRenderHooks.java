package com.aetherteam.aether.client.event.hooks;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.client.renderer.accessory.GlovesRenderer;
import com.aetherteam.aether.client.renderer.accessory.ShieldOfRepulsionRenderer;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import com.aetherteam.aether.item.accessories.miscellaneous.ShieldOfRepulsionItem;
import com.aetherteam.aether.mixin.mixins.client.accessor.ItemInHandRendererAccessor;
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
import net.minecraftforge.client.event.RenderHandEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import javax.annotation.Nullable;

public class HandRenderHooks {
    /**
     * Handles rendering gloves on the player's hands in first person, if they aren't wearing an Invisibility Cloak and if the gloves are set to be visible.
     * @param itemInHandRenderer The {@link ItemInHandRenderer} for rendering items in the player's hand.
     * @param player The {@link net.minecraft.world.entity.player.Player} to render the gloves on.
     * @param hand The {@link InteractionHand} to render a glove on.
     * @param pitch The interpolated pitch for the hand, as a {@link Float}.
     * @param swingProgress The swing progress for the hand, as a {@link Float}.
     * @param equippedProgress The equipping progress for the hand, as a {@link Float}.
     * @param poseStack The rendering {@link PoseStack}.
     * @param buffer The rendering {@link MultiBufferSource}.
     * @param packedLight The {@link Integer} for the packed lighting for rendering.
     * @see com.aetherteam.aether.client.event.listeners.HandRenderListener#onRenderHand(RenderHandEvent)
     */
    public static void renderGloveHandOverlay(ItemInHandRenderer itemInHandRenderer, @Nullable AbstractClientPlayer player, InteractionHand hand, float pitch, float swingProgress, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (player != null) {
            AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                if (!aetherPlayer.isWearingInvisibilityCloak()) { // Check for Invisibility Cloak.
                    CuriosApi.getCuriosHelper().findFirstCurio(player, (item) -> item.getItem() instanceof GlovesItem).ifPresent((slotResult) -> {
                        String identifier = slotResult.slotContext().identifier();
                        int id = slotResult.slotContext().index();
                        ItemStack itemStack = slotResult.stack();
                        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> handler.getStacksHandler(identifier).ifPresent(stacksHandler -> {
                            if (stacksHandler.getRenders().get(id)) { // Check if Gloves are visible.
                                CuriosRendererRegistry.getRenderer(itemStack.getItem()).ifPresent((renderer) -> {
                                    if (renderer instanceof GlovesRenderer glovesRenderer) {
                                        ItemStack heldItem = hand == InteractionHand.MAIN_HAND ? ((ItemInHandRendererAccessor) itemInHandRenderer).aether$getMainHandItem() : ((ItemInHandRendererAccessor) itemInHandRenderer).aether$getOffHandItem();
                                        renderArmWithItem(itemInHandRenderer, glovesRenderer, itemStack, player, heldItem, hand, pitch, swingProgress, equippedProgress, poseStack, buffer, packedLight, HandRenderType.GLOVES);
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
     * Handles rendering the Shield of Repulsion overlay on the player's hands in first person, if they aren't wearing an Invisibility Cloak and if the shield is set to be visible.
     * @param itemInHandRenderer The {@link ItemInHandRenderer} for rendering items in the player's hand.
     * @param player The {@link net.minecraft.world.entity.player.Player} to render the gloves on.
     * @param hand The {@link InteractionHand} to render a glove on.
     * @param pitch The interpolated pitch for the hand, as a {@link Float}.
     * @param swingProgress The swing progress for the hand, as a {@link Float}.
     * @param equippedProgress The equipping progress for the hand, as a {@link Float}.
     * @param poseStack The rendering {@link PoseStack}.
     * @param buffer The rendering {@link MultiBufferSource}.
     * @param packedLight The {@link Integer} for the packed lighting for rendering.
     * @see com.aetherteam.aether.client.event.listeners.HandRenderListener#onRenderHand(RenderHandEvent)
     */
    public static void renderShieldOfRepulsionHandOverlay(ItemInHandRenderer itemInHandRenderer, @Nullable AbstractClientPlayer player, InteractionHand hand, float pitch, float swingProgress, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (player != null) {
            AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                if (aetherPlayer.isWearingInvisibilityCloak()) { // Check for Invisibility Cloak.
                    CuriosApi.getCuriosHelper().findFirstCurio(player, (item) -> item.getItem() instanceof ShieldOfRepulsionItem).ifPresent((slotResult) -> {
                        String identifier = slotResult.slotContext().identifier();
                        int id = slotResult.slotContext().index();
                        ItemStack itemStack = slotResult.stack();
                        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> handler.getStacksHandler(identifier).ifPresent(stacksHandler -> {
                            if (stacksHandler.getRenders().get(id)) { // Check if Shield of Repulsion is visible.
                                CuriosRendererRegistry.getRenderer(itemStack.getItem()).ifPresent((renderer) -> {
                                    if (renderer instanceof ShieldOfRepulsionRenderer shieldRenderer) {
                                        ItemStack heldItem = hand == InteractionHand.MAIN_HAND ? ((ItemInHandRendererAccessor) itemInHandRenderer).aether$getMainHandItem() : ((ItemInHandRendererAccessor) itemInHandRenderer).aether$getOffHandItem();
                                        renderArmWithItem(itemInHandRenderer, shieldRenderer, itemStack, player, heldItem, hand, pitch, swingProgress, equippedProgress, poseStack, buffer, packedLight, HandRenderType.SHIELD_OF_REPULSION);
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
     * [CODE COPY] - {@link ItemInHandRenderer#renderArmWithItem(AbstractClientPlayer, float, float, InteractionHand, float, ItemStack, float, PoseStack, MultiBufferSource, int)}.<br><br>
     * Remove any checks for items that don't display the player's hands.
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
     * [CODE COPY] - {@link ItemInHandRenderer#renderPlayerArm(PoseStack, MultiBufferSource, int, float, float, HumanoidArm)}.<br><br>
     * Checks if the model is slim and also checks for what {@link HandRenderType} to display.
     */
    private static void renderPlayerArm(ICurioRenderer renderer, ItemStack glovesStack, AbstractClientPlayer player, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, float swingProgress, float equippedProgress, HumanoidArm arm, HandRenderType handRenderType) {
        boolean isSlim = player.getModelName().equals("slim");
        boolean flag = arm != HumanoidArm.LEFT;
        float f = flag ? 1.0F : -1.0F;
        float f1 = Mth.sqrt(swingProgress);
        float f2 = -0.3F * Mth.sin(f1 * Mth.PI);
        float f3 = 0.4F * Mth.sin(f1 * Mth.TWO_PI);
        float f4 = -0.4F * Mth.sin(swingProgress * Mth.PI);
        poseStack.translate(f * (f2 + 0.64000005F), f3 - 0.6F + equippedProgress * -0.6F, f4 - 0.71999997F);
        poseStack.mulPose(Axis.YP.rotationDegrees(f * 45.0F));
        float f5 = Mth.sin(swingProgress * swingProgress * Mth.PI);
        float f6 = Mth.sin(f1 * Mth.PI);
        poseStack.mulPose(Axis.YP.rotationDegrees(f * f6 * 70.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(f * f5 * -20.0F));
        poseStack.translate(f * -1.0F, 3.6F, 3.5F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(f * 120.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(200.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(f * -135.0F));
        float offset = 5.6F;
        if (isSlim) {
            offset = 5.65F;
        }
        poseStack.translate(f * offset, 0.0F, 0.0F);
        switch (handRenderType) {
            case GLOVES -> ((GlovesRenderer) renderer).renderFirstPerson(glovesStack, poseStack, buffer, combinedLight, player, arm);
            case SHIELD_OF_REPULSION -> ((ShieldOfRepulsionRenderer) renderer).renderFirstPerson(glovesStack, poseStack, buffer, combinedLight, player, arm);
        }
    }

    /**
     * [CODE COPY] - {@link ItemInHandRenderer#renderTwoHandedMap(PoseStack, MultiBufferSource, int, float, float, float)}.<br><br>
     * Remove check for invisibility, as it is not necessary from {@link net.minecraftforge.client.event.RenderHandEvent} in {@link com.aetherteam.aether.client.event.listeners.HandRenderListener#onRenderHand(RenderHandEvent)}.
     */
    private static void renderTwoHandedMap(ItemInHandRenderer itemInHandRenderer, ICurioRenderer renderer, ItemStack glovesStack, AbstractClientPlayer player, ItemStack heldItem, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, float swingProgress, float equippedProgress, float pitch, HandRenderType handRenderType) {
        float f = Mth.sqrt(swingProgress);
        float f1 = -0.2F * Mth.sin(swingProgress * Mth.PI);
        float f2 = -0.4F * Mth.sin(f * Mth.PI);
        poseStack.translate(0.0F, -f1 / 2.0F, f2);
        float f3 = ((ItemInHandRendererAccessor) itemInHandRenderer).callCalculateMapTilt(pitch);
        poseStack.translate(0.0F, 0.04F + equippedProgress * -1.2F + f3 * -0.5F, -0.72F);
        poseStack.mulPose(Axis.XP.rotationDegrees(f3 * -85.0F));

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        renderMapHand(renderer, glovesStack, player, poseStack, buffer, combinedLight, HumanoidArm.RIGHT, handRenderType);
        renderMapHand(renderer, glovesStack, player, poseStack, buffer, combinedLight, HumanoidArm.LEFT, handRenderType);
        poseStack.popPose();

        float f4 = Mth.sin(f * Mth.PI);
        poseStack.mulPose(Axis.XP.rotationDegrees(f4 * 20.0F));
        poseStack.scale(2.0F, 2.0F, 2.0F);
        ((ItemInHandRendererAccessor) itemInHandRenderer).callRenderMap(poseStack, buffer, combinedLight, heldItem);
    }

    /**
     * [CODE COPY] - {@link ItemInHandRenderer#renderOneHandedMap(PoseStack, MultiBufferSource, int, float, HumanoidArm, float, ItemStack)}.<br><br>
     * Remove check for invisibility, as it is not necessary from {@link net.minecraftforge.client.event.RenderHandEvent} in {@link com.aetherteam.aether.client.event.listeners.HandRenderListener#onRenderHand(RenderHandEvent)}.
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
        float f2 = Mth.sin(f1 * Mth.PI);
        float f3 = -0.5F * f2;
        float f4 = 0.4F * Mth.sin(f1 * Mth.TWO_PI);
        float f5 = -0.3F * Mth.sin(swingProgress * Mth.PI);
        poseStack.translate(f * f3, f4 - 0.3F * f2, f5);
        poseStack.mulPose(Axis.XP.rotationDegrees(f2 * -45.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(f * f2 * -30.0F));
        ((ItemInHandRendererAccessor) itemInHandRenderer).callRenderMap(poseStack, buffer, combinedLight, heldItem);
        poseStack.popPose();
    }

    /**
     * [CODE COPY] - {@link ItemInHandRenderer#renderMapHand(PoseStack, MultiBufferSource, int, HumanoidArm)}.<br><br>
     * Checks for what {@link HandRenderType} to display.
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
