package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.accessory.model.GlovesModel;
import com.gildedgames.aether.common.item.accessories.gloves.GlovesItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

//TODO: This entire listener is going to be redone at some point.
@Mod.EventBusSubscriber(Dist.CLIENT)
public class GloveRenderListener
{
    @SubscribeEvent
    public static void onRenderArm(RenderArmEvent event) {
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource multiBufferSource = event.getMultiBufferSource();
        int packedLight = event.getPackedLight();
        HumanoidArm arm = event.getArm();
        AbstractClientPlayer player = event.getPlayer();
        if (player != null) {
            CuriosApi.getCuriosHelper().findEquippedCurio((item) -> item.getItem() instanceof GlovesItem, player).ifPresent((triple) -> {
                String identifier = triple.getLeft();
                int id = triple.getMiddle();
                ItemStack itemStack = triple.getRight();
                CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> handler.getStacksHandler(identifier).ifPresent(stacksHandler -> {
                    if (stacksHandler.getRenders().get(id)) {
                        boolean isSlim = player.getModelName().equals("slim");
                        GlovesModel.WornGlovesModel glovesModel = new GlovesModel.WornGlovesModel(!isSlim ? Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES_ARM) : Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.GLOVES_ARM_SLIM), isSlim);
                        if (arm == HumanoidArm.RIGHT) {
                            glovesModel.renderGlove(itemStack, poseStack, multiBufferSource, packedLight, player, glovesModel.rightArm, glovesModel.rightSleeve);
                        } else if (arm == HumanoidArm.LEFT) {
                            glovesModel.renderGlove(itemStack, poseStack, multiBufferSource, packedLight, player, glovesModel.rightArm, glovesModel.rightSleeve);
                        }
                        event.setCanceled(true);
                    }
                }));
            });
        }
    }
}
