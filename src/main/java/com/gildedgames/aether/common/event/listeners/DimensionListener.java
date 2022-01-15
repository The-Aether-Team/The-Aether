package com.gildedgames.aether.common.event.listeners;

import com.gildedgames.aether.common.event.events.AetherBannedItemEvent;
import com.gildedgames.aether.common.event.hooks.AetherEventHooks;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.common.registry.AetherDimensions;
import com.gildedgames.aether.common.world.AetherTeleporter;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.SetVehiclePacket;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
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

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;

@Mod.EventBusSubscriber
public class DimensionListener
{
    public static boolean leavingAether;

    @SubscribeEvent
    public static void checkBlockBanned(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getPlayer();
        Level world = event.getWorld();
        BlockPos pos = event.getPos();
        Direction face = event.getFace();
        ItemStack stack = event.getItemStack();
        BlockState state = world.getBlockState(pos);

        if (player.getCommandSenderWorld().dimension() == AetherDimensions.AETHER_WORLD) {
            if (stack.is(AetherTags.Items.BANNED_IN_AETHER)) {
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
                    world.explode(null, DamageSource.badRespawnPointExplosion(), null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 5.0F, true, Explosion.BlockInteraction.DESTROY);
                }
                player.swing(InteractionHand.MAIN_HAND);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockBanned(AetherBannedItemEvent.SpawnParticles event) {
        LevelAccessor world = event.getWorld();
        double x = event.getPos().getX() + 0.5;
        double y = event.getPos().getY() + 1;
        double z = event.getPos().getZ() + 0.5;
        for (int i = 0; i < 10; i++) {
            world.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, 0.0, 0.0, 0.0);
        }
        world.playSound(null, event.getPos(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    @SubscribeEvent
    public static void onNeighborNotified(BlockEvent.NeighborNotifyEvent event) {
        if (event.getWorld() instanceof Level) {
            Level world = (Level) event.getWorld();
            BlockPos pos = event.getPos();
            FluidState fluidstate = world.getFluidState(pos);
            if (world.dimension() == AetherDimensions.AETHER_WORLD && fluidstate.getType().is(AetherTags.Fluids.FREEZABLE_TO_AEROGEL)) {
                world.setBlockAndUpdate(pos, AetherBlocks.AEROGEL.get().defaultBlockState());
                if (world instanceof ServerLevel) {
                    ServerLevel serverWorld = (ServerLevel) world;
                    double x = pos.getX() + 0.5;
                    double y = pos.getY() + 1;
                    double z = pos.getZ() + 0.5;
                    for (int i = 0; i < 10; i++) {
                        serverWorld.sendParticles(ParticleTypes.LARGE_SMOKE, x, y, z, 1, 0.0D,0.0D, 0.0D, 0.0F);
                    }
                }
                world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
        leavingAether = event.getEntity().level.dimension() == AetherDimensions.AETHER_WORLD && event.getDimension() == Level.OVERWORLD;
    }

    @SubscribeEvent
    public static void onSleepFinishedTime(SleepFinishedTimeEvent event) {
        if (event.getWorld() instanceof ServerLevel level) {
            MinecraftServer server = level.getServer();
            for (ServerLevel serverLevel : server.getAllLevels()) {
                serverLevel.setDayTime(event.getNewTime());
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            ServerLevel world = (ServerLevel) event.world;
            if (world.dimension() == AetherDimensions.AETHER_WORLD) {
                if (event.phase == TickEvent.Phase.END) {
                    if (!AetherConfig.COMMON.disable_falling_to_overworld.get()) {
                        for (Entity entity : world.getEntities(EntityTypeTest.forClass(Entity.class), Objects::nonNull)) {
                            if (entity.getY() <= world.getMinBuildHeight() && !entity.isPassenger()) {
                                if (!(entity instanceof Player player && player.getAbilities().flying)) {
                                    fallFromAether(entity);
                                }
                            }
                        }
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
        Level serverLevel = entity.level;
        MinecraftServer minecraftserver = serverLevel.getServer();
        if (minecraftserver != null) {
            ServerLevel destination = minecraftserver.getLevel(Level.OVERWORLD);
            if (destination != null) {
                List<Entity> passengers = entity.getPassengers();
                entity.level.getProfiler().push("aether_fall");
                entity.setPortalCooldown();
                Entity target = entity.changeDimension(destination, new AetherTeleporter(destination, false));
                entity.level.getProfiler().pop();
                // Check for passengers
                if (target != null) {
                    for (Entity passenger : passengers) {
                        passenger.stopRiding();
                        Entity nextPassenger = fallFromAether(passenger);
                        if (nextPassenger != null) {
                            nextPassenger.startRiding(target);
                            if (target instanceof ServerPlayer) { // Fixes a desync between the server and client
                                AetherPacketHandler.sendToPlayer(new SetVehiclePacket(nextPassenger.getId(), target.getId()), (ServerPlayer) target);
                            }
                        }
                    }
                }
                return target;
            }
        }
        return null;
    }
}
