package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.renderer.accessory.model.GlovesModel;
import com.gildedgames.aether.common.item.accessories.gloves.GlovesItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class GloveRenderListener
{
    //TODO: This entire listener is going to be redone at some point.
//    @SubscribeEvent
//    public static void onRenderHand(RenderHandEvent event) {
//        event.getPoseStack().pushPose();
//        AbstractClientPlayer player = Minecraft.getInstance().player;
//        if (player != null) {
//            CuriosApi.getCuriosHelper().findEquippedCurio((item) -> item.getItem() instanceof GlovesItem, player).ifPresent((triple) -> {
//                String identifier = triple.getLeft();
//                int id = triple.getMiddle();
//                CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> handler.getStacksHandler(identifier).ifPresent(stacksHandler -> {
//                    if (stacksHandler.getRenders().get(id)) {
//                        boolean isMainHand = event.getHand() == InteractionHand.MAIN_HAND;
//                        HumanoidArm handside = isMainHand ? player.getMainArm() : player.getMainArm().getOpposite();
//                        if (isMainHand && event.getItemStack().isEmpty()) {
//                            renderGloveEmpty(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getEquipProgress(), event.getSwingProgress(), player, triple.getRight(), handside);
//                        } else if (event.getItemStack().getItem() == Items.FILLED_MAP) {
//                            if (isMainHand && player.getOffhandItem().isEmpty()) {
//                                renderTwoHandedMapGloves(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getInterpolatedPitch(), event.getEquipProgress(), event.getSwingProgress(), player, triple.getRight());
//                            } else {
//                                renderOneHandedMapGlove(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getEquipProgress(), event.getSwingProgress(), player, triple.getRight(), handside);
//                            }
//                        }
//                    }
//                }));
//            });
//        }
//        event.getPoseStack().popPose();
//    }
//
//    private static void renderGloveEmpty(PoseStack matrixStack, MultiBufferSource buffer, int light, float equipProgress, float swingProgress, AbstractClientPlayer player, ItemStack glovesStack, HumanoidArm handSide) {
//        boolean hand = handSide != HumanoidArm.LEFT;
//        float f = hand ? 1.0F : -1.0F;
//        float f1 = Mth.sqrt(swingProgress);
//        float f2 = -0.3F * Mth.sin(f1 * (float) Math.PI);
//        float f3 = 0.4F * Mth.sin(f1 * ((float) Math.PI * 2F));
//        float f4 = -0.4F * Mth.sin(swingProgress * (float) Math.PI);
//        matrixStack.translate(f * (f2 + 0.64000005F), f3 + -0.6F + equipProgress * -0.6F, f4 + -0.71999997F);
//        matrixStack.mulPose(Vector3f.YP.rotationDegrees(f * 45.0F));
//        float f5 = Mth.sin(swingProgress * swingProgress * (float) Math.PI);
//        float f6 = Mth.sin(f1 * (float) Math.PI);
//        matrixStack.mulPose(Vector3f.YP.rotationDegrees(f * f6 * 70.0F));
//        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f * f5 * -20.0F));
//        matrixStack.translate(f * -1.0F, 3.6F, 3.5D);
//        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f * 120.0F));
//        matrixStack.mulPose(Vector3f.XP.rotationDegrees(200.0F));
//        matrixStack.mulPose(Vector3f.YP.rotationDegrees(f * -135.0F));
//        matrixStack.translate(f * 5.6F, 0.0D, 0.0D);
//
//        GlovesItem glovesItem = (GlovesItem) glovesStack.getItem();
//        boolean isSlim = player.getModelName().equals("slim");
//        GlovesModel.WornGlovesModel glovesModel = new GlovesModel.WornGlovesModel(isSlim);
//        ResourceLocation glovesTexture = !isSlim ? glovesItem.getGlovesTexture() : glovesItem.getGlovesSlimTexture();
//
//        glovesModel.renderGloves(matrixStack, buffer, light, player, glovesTexture, glovesStack, handSide);
//    }
//
//    private static void renderTwoHandedMapGloves(PoseStack matrixStack, MultiBufferSource buffer, int light, float interpolatedPitch, float equipProgress, float swingProgress, AbstractClientPlayer player, ItemStack glovesStack) {
//        float f = Mth.sqrt(swingProgress);
//        float f1 = -0.2F * Mth.sin(swingProgress * (float) Math.PI);
//        float f2 = -0.4F * Mth.sin(f * (float) Math.PI);
//        matrixStack.translate(0.0D, -f1 / 2.0F, f2);
//        float f3 = mapTilt(interpolatedPitch);
//        matrixStack.translate(0.0D, 0.04F + equipProgress * -1.2F + f3 * -0.5F, -0.72F);
//        matrixStack.mulPose(Vector3f.XP.rotationDegrees(f3 * -85.0F));
//        matrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
//
//        renderMapGlove(matrixStack, buffer, light, player, glovesStack, HumanoidArm.RIGHT);
//        renderMapGlove(matrixStack, buffer, light, player, glovesStack, HumanoidArm.LEFT);
//    }
//
//    private static float mapTilt(float equipProgress) {
//        float f = 1.0F - equipProgress / 45.0F + 0.1F;
//        f = Mth.clamp(f, 0.0F, 1.0F);
//        return -Mth.cos(f * (float) Math.PI) * 0.5F + 0.5F;
//    }
//
//    private static void renderMapGlove(PoseStack matrixStack, MultiBufferSource buffer, int light, AbstractClientPlayer player, ItemStack glovesStack, HumanoidArm handSide) {
//        matrixStack.pushPose();
//        float f = handSide == HumanoidArm.RIGHT ? 1.0F : -1.0F;
//        matrixStack.mulPose(Vector3f.YP.rotationDegrees(92.0F));
//        matrixStack.mulPose(Vector3f.XP.rotationDegrees(45.0F));
//        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f * -41.0F));
//        matrixStack.translate(f * 0.3F, -1.1F, 0.45F);
//
//        GlovesItem glovesItem = (GlovesItem) glovesStack.getItem();
//        boolean isSlim = player.getModelName().equals("slim");
//        GlovesModel.WornGlovesModel glovesModel = new GlovesModel.WornGlovesModel(isSlim);
//        ResourceLocation glovesTexture = !isSlim ? glovesItem.getGlovesTexture() : glovesItem.getGlovesSlimTexture();
//
//        glovesModel.renderGloves(matrixStack, buffer, light, player, glovesTexture, glovesStack, handSide);
//
//        matrixStack.popPose();
//    }
//
//    private static void renderOneHandedMapGlove(PoseStack matrixStack, MultiBufferSource buffer, int light, float equipProgress, float swingProgress, AbstractClientPlayer player, ItemStack glovesStack, HumanoidArm handSide) {
//        float f = handSide == HumanoidArm.RIGHT ? 1.0F : -1.0F;
//        matrixStack.translate(f * 0.125F, -0.125D, 0.0D);
//        matrixStack.pushPose();
//        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f * 10.0F));
//        renderGloveEmpty(matrixStack, buffer, light, equipProgress, swingProgress, player, glovesStack, handSide);
//        matrixStack.popPose();
//    }
}
