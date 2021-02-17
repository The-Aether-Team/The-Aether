package com.aether.event.handlers;

import com.aether.event.AetherBannedItemEvent;
import com.aether.event.hooks.AetherEventHooks;
import com.aether.registry.AetherTags;
import com.aether.world.dimension.AetherDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AetherDimensionHandler
{
    @SubscribeEvent
    public static void checkBlockBanned(PlayerInteractEvent.RightClickBlock event) {
        PlayerEntity player = event.getPlayer();
        if (player.world.getDimensionKey() == AetherDimensions.AETHER_WORLD) {
            if (event.getItemStack().getItem().isIn(AetherTags.Items.BANNED_IN_AETHER)) {
                if (AetherEventHooks.isItemBanned(event.getItemStack())) {
                    AetherEventHooks.onItemBanned(event.getWorld(), event.getPos(), event.getFace(), event.getItemStack());
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockBanned(AetherBannedItemEvent.SpawnParticles event) {
        IWorld world = event.getWorld();
        double x, y, z;
        x = event.getPos().getX() + 0.5;
        y = event.getPos().getY() + 1;
        z = event.getPos().getZ() + 0.5;
        for (int i = 0; i < 10; i++) {
            world.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, 0.0, 0.0, 0.0);
        }
        world.playSound(null, event.getPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }
}
