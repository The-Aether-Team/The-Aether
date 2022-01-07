package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.renderer.accessory.GlovesRenderer;
import com.gildedgames.aether.common.item.accessories.gloves.GlovesItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class GloveRenderListener
{
    @SubscribeEvent
    public static void onRenderArm(RenderArmEvent event) {
        AbstractClientPlayer player = event.getPlayer();
        if (player != null) {
            CuriosApi.getCuriosHelper().findEquippedCurio((item) -> item.getItem() instanceof GlovesItem, player).ifPresent((triple) -> {
                String identifier = triple.getLeft();
                int id = triple.getMiddle();
                ItemStack itemStack = triple.getRight();
                CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> handler.getStacksHandler(identifier).ifPresent(stacksHandler -> {
                    if (stacksHandler.getRenders().get(id)) {
                        CuriosRendererRegistry.getRenderer(itemStack.getItem()).ifPresent((renderer) -> {
                            if (renderer instanceof GlovesRenderer glovesRenderer) {
                                glovesRenderer.renderFirstPerson(itemStack, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), player, event.getArm());
                            }
                        });
                    }
                }));
            });
        }
    }
}
