package com.gildedgames.aether.common.event.listeners;

import com.gildedgames.aether.common.event.AetherBannedItemEvent;
import com.gildedgames.aether.common.event.hooks.AetherEventHooks;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.common.registry.AetherDimensions;
import com.gildedgames.aether.common.world.AetherTeleporter;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.SmokeParticlePacket;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
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
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber
public class DimensionListener
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
    public static void onNeighborNotified(BlockEvent.NeighborNotifyEvent event) {
        if (event.getWorld() instanceof World) {
            World world = (World) event.getWorld();
            BlockPos pos = event.getPos();
            FluidState fluidstate = world.getFluidState(pos);
            if (world.dimension() == AetherDimensions.AETHER_WORLD && fluidstate.getType().is(AetherTags.Fluids.FREEZABLE_TO_AEROGEL)) {
                world.setBlockAndUpdate(pos, AetherBlocks.AEROGEL.get().defaultBlockState());
                AetherPacketHandler.sendToAll(new SmokeParticlePacket(pos));
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onSleepFinishedTime(SleepFinishedTimeEvent event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) event.getWorld();
            MinecraftServer server = world.getServer();
            for (ServerWorld serverworld : server.getAllLevels()) {
                serverworld.setDayTime(0);
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if(event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            if(event.world.dimension() == AetherDimensions.AETHER_WORLD) {
                List<Entity> loadedEntities = ((ServerWorld)event.world).getEntities(null, Objects::nonNull);
                for(Entity entity : loadedEntities) {
                    if(entity.getY() <= 0 && !entity.isPassenger()) {
                        fallFromAether(entity);
                    }
                }
            }
        }
    }

    /**
     * Code to handle falling out of the Aether with all of the passengers intact.
     */
    @Nullable
    private static Entity fallFromAether(Entity entity) {
        World serverLevel = entity.level;
        MinecraftServer minecraftserver = serverLevel.getServer();
        if (minecraftserver != null) {
            ServerWorld destination = minecraftserver.getLevel(World.OVERWORLD);
            if (destination != null) {
                List<Entity> passengers = entity.getPassengers();
                entity.level.getProfiler().push("aether_fall");
                entity.setPortalCooldown();
                Entity target = entity.changeDimension(destination, new AetherTeleporter(destination, false));
                entity.level.getProfiler().pop();
                //Check for passengers
                if(target != null) {
                    for (Entity passenger : passengers) {
                        fallFromAether(passenger);
                        passenger.startRiding(target);
                    }
                }
                return target;
            }
        }
        return null;
    }
}
