package com.gildedgames.aether.event.hooks;

import com.gildedgames.aether.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.util.LevelUtil;
import com.gildedgames.aether.data.resources.AetherDimensions;
import com.gildedgames.aether.world.AetherTeleporter;
import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.capability.time.AetherTime;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.client.AetherTravelPacket;
import com.gildedgames.aether.network.packet.client.LeavingAetherPacket;
import com.gildedgames.aether.network.packet.client.SetVehiclePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.material.FluidState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class DimensionHooks {
    public static boolean playerLeavingAether;
    public static boolean displayAetherTravel;
    public static int teleportationTimer;

    public static boolean checkPlacementBanned(Player player, Level level, BlockPos pos, Direction face, ItemStack stack, BlockState state) {
        if (level.getBiome(pos).is(AetherTags.Biomes.ULTRACOLD)) {
            if (stack.is(AetherTags.Items.BANNED_IN_AETHER)) {
                return bannedItemDispatch(level, pos, face, stack);
            }

            if (AetherConfig.COMMON.enable_bed_explosions.get()) {
                if (state.is(BlockTags.BEDS) && !state.is(AetherBlocks.SKYROOT_BED.get())) {
                    if (!level.isClientSide()) {
                        if (state.getValue(BedBlock.PART) != BedPart.HEAD) {
                            pos = pos.relative(state.getValue(BedBlock.FACING));
                            state = level.getBlockState(pos);
                        }
                        BlockPos blockpos = pos.relative(state.getValue(BedBlock.FACING).getOpposite());
                        if (level.getBlockState(blockpos).is(BlockTags.BEDS) && !level.getBlockState(blockpos).is(AetherBlocks.SKYROOT_BED.get())) {
                            level.removeBlock(blockpos, false);
                        }
                        level.explode(null, DamageSource.badRespawnPointExplosion(), null, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, 5.0F, true, Explosion.BlockInteraction.DESTROY);
                    }
                    player.swing(InteractionHand.MAIN_HAND);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean bannedItemDispatch(Level level, BlockPos pos, Direction face, ItemStack stack) {
        if (AetherEventDispatch.isItemBanned(stack)) {
            AetherEventDispatch.onItemBanned(level, pos, face, stack);
            return true;
        }
        return false;
    }

    public static void onPlacementBanned(LevelAccessor accessor, BlockPos pos) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 1.0;
        double z = pos.getZ() + 0.5;
        for (int i = 0; i < 10; i++) {
            accessor.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, 0.0, 0.0, 0.0);
        }
        accessor.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    public static boolean freezeToAerogel(LevelAccessor accessor, BlockPos pos) {
        if (accessor instanceof Level level) {
            FluidState fluidstate = level.getFluidState(pos);
            if (level.getBiome(pos).is(AetherTags.Biomes.ULTRACOLD) && fluidstate.is(AetherTags.Fluids.FREEZABLE_TO_AEROGEL)) {
                level.setBlockAndUpdate(pos, AetherBlocks.AEROGEL.get().defaultBlockState());
                if (level instanceof ServerLevel serverLevel) {
                    double x = pos.getX() + 0.5;
                    double y = pos.getY() + 1.0;
                    double z = pos.getZ() + 0.5;
                    for (int i = 0; i < 10; i++) {
                        serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, x, y, z, 1, 0.0D,0.0D, 0.0D, 0.0F);
                    }
                }
                level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);
                return true;
            }
        }
        return false;
    }

    /**
     * Ticks time in dimensions with the Aether effects location.
     */
    public static void tickTime(Level level) {
        if (level.dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location()) && level instanceof ServerLevel serverLevel) {
            long i = serverLevel.levelData.getGameTime() + 1L;
            serverLevel.serverLevelData.setGameTime(i);
            if (serverLevel.levelData.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                AetherTime.get(level).ifPresent(cap -> serverLevel.setDayTime(cap.tickTime(level)));
            }
        }
    }

    /**
     * This code is used to handle entities falling out of the Aether. If an entity is not a player or vehicle, it is removed.
     */
    public static void fallFromAether(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            if (!AetherConfig.COMMON.disable_falling_to_overworld.get()) {
                for (Entity entity : serverLevel.getEntities(EntityTypeTest.forClass(Entity.class), Objects::nonNull)) {
                    if (level.getBiome(entity.blockPosition()).is(AetherTags.Biomes.FALL_TO_OVERWORLD)) {
                        if (entity.getY() <= serverLevel.getMinBuildHeight() && !entity.isPassenger()) {
                            if ((entity instanceof Player player && !player.getAbilities().flying) || entity.isVehicle()) {
                                entityFell(entity);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Code to handle falling out of the Aether with all passengers intact.
     */
    @Nullable
    private static Entity entityFell(Entity entity) {
        Level serverLevel = entity.level;
        MinecraftServer minecraftserver = serverLevel.getServer();
        if (minecraftserver != null) {
            ServerLevel destination = minecraftserver.getLevel(LevelUtil.returnDimension());
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
                        Entity nextPassenger = entityFell(passenger);
                        if (nextPassenger != null) {
                            nextPassenger.startRiding(target);
                            if (target instanceof ServerPlayer serverPlayer) { // Fixes a desync between the server and client
                                AetherPacketHandler.sendToPlayer(new SetVehiclePacket(nextPassenger.getId(), target.getId()), serverPlayer);
                            }
                        }
                    }
                    if (target instanceof ServerPlayer) {
                        teleportationTimer = 500;
                    }
                }
                return target;
            }
        }
        return null;
    }

    public static void dimensionTravel(Entity entity, ResourceKey<Level> dimension) {
        // The level passed into shouldReturnPlayerToOverworld() is the dimension the player is leaving
        //  Meaning: We display the Descending GUI text to the player if they're about to leave a dimension that returns them to the OW
        if (entity.level.getBiome(entity.blockPosition()).is(AetherTags.Biomes.DISPLAY_TRAVEL_TEXT)) {
            if (entity.level.dimension() == LevelUtil.destinationDimension() && dimension == LevelUtil.returnDimension()) {
                displayAetherTravel = true;
                playerLeavingAether = true;
                AetherPacketHandler.sendToAll(new AetherTravelPacket(true));
                AetherPacketHandler.sendToAll(new LeavingAetherPacket(true));
            } else if (entity.level.dimension() == LevelUtil.returnDimension() && dimension == LevelUtil.destinationDimension()) {
                displayAetherTravel = true;
                playerLeavingAether = false;
                AetherPacketHandler.sendToAll(new AetherTravelPacket(true));
                AetherPacketHandler.sendToAll(new LeavingAetherPacket(false));
            } else {
                displayAetherTravel = false;
                AetherPacketHandler.sendToAll(new AetherTravelPacket(false));
            }
        }
    }

    public static void travelling(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (teleportationTimer > 0) {
                serverPlayer.connection.aboveGroundTickCount = 0;
                serverPlayer.connection.aboveGroundVehicleTickCount = 0;
                teleportationTimer--;
            }
            if (teleportationTimer < 0 || serverPlayer.verticalCollisionBelow) {
                teleportationTimer = 0;
            }
        }
    }
}
