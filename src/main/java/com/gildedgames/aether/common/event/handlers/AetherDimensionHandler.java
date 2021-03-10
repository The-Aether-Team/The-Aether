package com.gildedgames.aether.common.event.handlers;

import com.gildedgames.aether.common.event.AetherBannedItemEvent;
import com.gildedgames.aether.common.event.hooks.AetherEventHooks;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.common.registry.AetherDimensions;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AetherDimensionHandler
{
    @SubscribeEvent
    public static void checkBlockBanned(PlayerInteractEvent.RightClickBlock event) {
        PlayerEntity player = event.getPlayer();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        Direction face = event.getFace();
        ItemStack stack = event.getItemStack();
        BlockState state = world.getBlockState(pos);

        if (player.getCommandSenderWorld().dimension() == AetherDimensions.AETHER_WORLD) {
            if (stack.getItem().is(AetherTags.Items.BANNED_IN_AETHER)) {
                if (AetherEventHooks.isItemBanned(stack)) {
                    AetherEventHooks.onItemBanned(world, pos, face, stack);
                    event.setCanceled(true);
                }
            }

            if (state.is(BlockTags.BEDS) && state.getBlock() != AetherBlocks.SKYROOT_BED.get()) {
                if (!world.isClientSide()) {
                    if (state.getValue(BedBlock.PART) != BedPart.HEAD) {
                        pos = pos.relative(state.getValue(BedBlock.FACING));
                        state = world.getBlockState(pos);
                    }
                    BlockPos blockpos = pos.relative(state.getValue(BedBlock.FACING).getOpposite());
                    if (world.getBlockState(blockpos).is(BlockTags.BEDS) && world.getBlockState(blockpos).getBlock() != AetherBlocks.SKYROOT_BED.get()) {
                        world.removeBlock(blockpos, false);
                    }
                    world.explode(null, DamageSource.badRespawnPointExplosion(), null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 5.0F, true, Explosion.Mode.DESTROY);
                }
                player.swing(Hand.MAIN_HAND);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockBanned(AetherBannedItemEvent.SpawnParticles event) {
        IWorld world = event.getWorld();
        double x = event.getPos().getX() + 0.5;
        double y = event.getPos().getY() + 1;
        double z = event.getPos().getZ() + 0.5;
        for (int i = 0; i < 10; i++) {
            world.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, 0.0, 0.0, 0.0);
        }
        world.playSound(null, event.getPos(), SoundEvents.FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        MinecraftServer server = event.getPlayer().getServer();
        if (server != null) {
            for(ServerWorld serverworld : server.getAllLevels()) {
                serverworld.setDayTime(0);
            }
        }
    }
}
