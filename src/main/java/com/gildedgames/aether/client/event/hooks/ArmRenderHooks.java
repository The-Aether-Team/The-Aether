package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.client.renderer.accessory.GlovesRenderer;
import com.gildedgames.aether.client.renderer.accessory.RepulsionShieldRenderer;
import com.gildedgames.aether.common.item.accessories.gloves.GlovesItem;
import com.gildedgames.aether.common.item.accessories.miscellaneous.ShieldOfRepulsionItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class ArmRenderHooks {
    public static void renderGloveArmOverlay(AbstractClientPlayer player, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, HumanoidArm arm) {
        if (player != null) {
            CuriosApi.getCuriosHelper().findFirstCurio(player, (item) -> item.getItem() instanceof GlovesItem).ifPresent((slotResult) -> {
                String identifier = slotResult.slotContext().identifier();
                int id = slotResult.slotContext().index();
                ItemStack itemStack = slotResult.stack();
                CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> handler.getStacksHandler(identifier).ifPresent(stacksHandler -> {
                    if (stacksHandler.getRenders().get(id)) {
                        CuriosRendererRegistry.getRenderer(itemStack.getItem()).ifPresent((renderer) -> {
                            if (renderer instanceof GlovesRenderer glovesRenderer) {
                                glovesRenderer.renderFirstPerson(itemStack, poseStack, buffer, combinedLight, player, arm);
                            }
                        });
                    }
                }));
            });
        }
    }

    public static void renderRepulsionShieldArmOverlay(AbstractClientPlayer player, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, HumanoidArm arm) {
        if (player != null) {
            CuriosApi.getCuriosHelper().findFirstCurio(player, (item) -> item.getItem() instanceof ShieldOfRepulsionItem).ifPresent((slotResult) -> {
                String identifier = slotResult.slotContext().identifier();
                int id = slotResult.slotContext().index();
                ItemStack itemStack = slotResult.stack();
                CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> handler.getStacksHandler(identifier).ifPresent(stacksHandler -> {
                    if (stacksHandler.getRenders().get(id)) {
                        CuriosRendererRegistry.getRenderer(itemStack.getItem()).ifPresent((renderer) -> {
                            if (renderer instanceof RepulsionShieldRenderer shieldRenderer) {
                                shieldRenderer.renderFirstPerson(itemStack, poseStack, buffer, combinedLight, player, arm);
                            }
                        });
                    }
                }));
            });
        }
    }
}
