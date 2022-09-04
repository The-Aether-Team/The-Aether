package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.event.hooks.EntityRenderHooks;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class EntityRenderListener {
    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        PlayerRenderer renderer = event.getRenderer();
        boolean hide = EntityRenderHooks.shouldHidePlayer();
        if (hide) {
            event.setCanceled(true);
        }
        EntityRenderHooks.adjustShadow(renderer, hide);
    }

    @SubscribeEvent
    public static <T extends LivingEntity, M extends EntityModel<T>> void onRenderEntity(RenderLivingEvent.Pre<T, M> event) {
        Entity entity = event.getEntity();
        EntityRenderer<?> renderer = event.getRenderer();
        boolean hide = EntityRenderHooks.shouldHideEntity(entity);
        if (hide) {
            event.setCanceled(true);
        }
        EntityRenderHooks.adjustShadow(renderer, hide);
    }
}
