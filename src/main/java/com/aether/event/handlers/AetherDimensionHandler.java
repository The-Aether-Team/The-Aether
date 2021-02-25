package com.aether.event.handlers;

import com.aether.event.AetherBannedItemEvent;
import com.aether.event.hooks.AetherEventHooks;
import com.aether.registry.AetherBlocks;
import com.aether.registry.AetherTags;
import com.aether.registry.AetherDimensions;
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

        if (player.getEntityWorld().getDimensionKey() == AetherDimensions.AETHER_WORLD) {
            if (stack.getItem().isIn(AetherTags.Items.BANNED_IN_AETHER)) {
                if (AetherEventHooks.isItemBanned(stack)) {
                    AetherEventHooks.onItemBanned(world, pos, face, stack);
                    event.setCanceled(true);
                }
            }

            if (state.isIn(BlockTags.BEDS) && state.getBlock() != AetherBlocks.SKYROOT_BED.get()) {
                if (!world.isRemote()) {
                    if (state.get(BedBlock.PART) != BedPart.HEAD) {
                        pos = pos.offset(state.get(BedBlock.HORIZONTAL_FACING));
                        state = world.getBlockState(pos);
                    }
                    BlockPos blockpos = pos.offset(state.get(BedBlock.HORIZONTAL_FACING).getOpposite());
                    if (world.getBlockState(blockpos).isIn(BlockTags.BEDS) && world.getBlockState(blockpos).getBlock() != AetherBlocks.SKYROOT_BED.get()) {
                        world.removeBlock(blockpos, false);
                    }
                    world.createExplosion(null, DamageSource.func_233546_a_(), null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 5.0F, true, Explosion.Mode.DESTROY);
                }
                player.swingArm(Hand.MAIN_HAND);
                event.setCanceled(true);
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

    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        MinecraftServer server = event.getPlayer().getServer();
        if (server != null) {
            for(ServerWorld serverworld : server.getWorlds()) {
                serverworld.setDayTime(0);
            }
        }
    }
}
