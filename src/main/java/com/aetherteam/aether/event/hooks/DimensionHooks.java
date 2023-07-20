package com.aetherteam.aether.event.hooks;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.portal.AetherPortalForcer;
import com.aetherteam.aether.block.portal.AetherPortalShape;
import com.aetherteam.aether.capability.item.DroppedItem;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.capability.time.AetherTime;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.mixin.mixins.common.accessor.ServerGamePacketListenerImplAccessor;
import com.aetherteam.aether.mixin.mixins.common.accessor.ServerLevelAccessor;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.clientbound.AetherTravelPacket;
import com.aetherteam.aether.network.packet.clientbound.LeavingAetherPacket;
import com.aetherteam.aether.network.packet.clientbound.SetVehiclePacket;
import com.aetherteam.aether.world.AetherLevelData;
import com.aetherteam.aether.world.LevelUtil;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DimensionHooks {
    public static boolean playerLeavingAether;
    public static boolean displayAetherTravel;
    public static int teleportationTimer;

    public static void startInAether(Player player) {
        AetherPlayer.get(player).ifPresent(aetherPlayer -> {
            if (AetherConfig.SERVER.spawn_in_aether.get()) {
                if (aetherPlayer.canSpawnInAether()) {
                    if (aetherPlayer.getPlayer() instanceof ServerPlayer serverPlayer) {
                        MinecraftServer server = serverPlayer.getLevel().getServer();
                        ServerLevel aetherLevel = server.getLevel(AetherDimensions.AETHER_LEVEL);
                        if (aetherLevel != null && serverPlayer.getLevel().dimension() != AetherDimensions.AETHER_LEVEL) {
                            if (aetherPlayer.getPlayer().changeDimension(aetherLevel, new AetherPortalForcer(aetherLevel, false, true)) != null) {
                                serverPlayer.setRespawnPosition(AetherDimensions.AETHER_LEVEL, serverPlayer.blockPosition(), serverPlayer.getYRot(), true, false);
                                aetherPlayer.setCanSpawnInAether(false);
                            }
                        }
                    }
                }
            } else {
                aetherPlayer.setCanSpawnInAether(false);
            }
        });
    }

    public static boolean createPortal(Player player, Level level, BlockPos pos, Direction direction, ItemStack stack, InteractionHand hand) {
        if (direction != null) {
            BlockPos relativePos = pos.relative(direction);
            if (stack.is(AetherTags.Items.AETHER_PORTAL_ACTIVATION_ITEMS)) {
                if ((level.dimension() == LevelUtil.returnDimension() || level.dimension() == LevelUtil.destinationDimension()) && !AetherConfig.SERVER.disable_aether_portal.get()) {
                    Optional<AetherPortalShape> optional = AetherPortalShape.findEmptyAetherPortalShape(level, relativePos, Direction.Axis.X);
                    if (optional.isPresent()) {
                        optional.get().createPortalBlocks();
                        player.playSound(SoundEvents.BUCKET_EMPTY, 1.0F, 1.0F);
                        player.swing(hand);
                        if (!player.isCreative()) {
                            if (stack.getCount() > 1) {
                                stack.shrink(1);
                                player.addItem(stack.hasCraftingRemainingItem() ? stack.getCraftingRemainingItem() : ItemStack.EMPTY);
                            } else if (stack.isDamageableItem()) {
                                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                            } else {
                                player.setItemInHand(hand, stack.hasCraftingRemainingItem() ? stack.getCraftingRemainingItem() : ItemStack.EMPTY);
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean detectWaterInFrame(LevelAccessor levelAccessor, BlockPos pos, BlockState blockState, FluidState fluidState) {
        if (levelAccessor instanceof Level level) {
            if (fluidState.is(Fluids.WATER) && fluidState.createLegacyBlock().getBlock() == blockState.getBlock()) {
                if ((level.dimension() == LevelUtil.returnDimension() || level.dimension() == LevelUtil.destinationDimension()) && !AetherConfig.SERVER.disable_aether_portal.get()) {
                    Optional<AetherPortalShape> optional = AetherPortalShape.findEmptyAetherPortalShape(level, pos, Direction.Axis.X);
                    if (optional.isPresent()) {
                        optional.get().createPortalBlocks();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Ticks time in dimensions with the Aether effects location.
     */
    public static void tickTime(Level level) {
        if (level.dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location()) && level instanceof ServerLevel serverLevel) {
            ServerLevelAccessor serverLevelAccessor = (ServerLevelAccessor) serverLevel;
            com.aetherteam.aether.mixin.mixins.common.accessor.LevelAccessor levelAccessor = (com.aetherteam.aether.mixin.mixins.common.accessor.LevelAccessor) level;
            long i = levelAccessor.aether$getLevelData().getGameTime() + 1L;
            serverLevelAccessor.aether$getServerLevelData().setGameTime(i);
            if (serverLevelAccessor.aether$getServerLevelData().getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                AetherTime.get(level).ifPresent(cap -> serverLevel.setDayTime(cap.tickTime(level)));
            }
        }
    }

    /**
     * This code is used to handle entities falling out of the Aether. If an entity is not a player or vehicle, it is removed.
     */
    public static void fallFromAether(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            if (!AetherConfig.SERVER.disable_falling_to_overworld.get()) {
                for (Entity entity : serverLevel.getEntities(EntityTypeTest.forClass(Entity.class), Objects::nonNull)) {
                    if (level.getBiome(entity.blockPosition()).is(AetherTags.Biomes.FALL_TO_OVERWORLD) && level.dimension() == LevelUtil.destinationDimension()) {
                        if (entity.getY() <= serverLevel.getMinBuildHeight() && !entity.isPassenger()) {
                            if (entity instanceof Player || entity.isVehicle() || (entity instanceof Saddleable) && ((Saddleable) entity).isSaddled()) {
                                entityFell(entity);
                            } else if (entity instanceof ItemEntity itemEntity) {
                                LazyOptional<DroppedItem> droppedItem = DroppedItem.get(itemEntity);
                                if (droppedItem.isPresent() && droppedItem.resolve().isPresent()) {
                                    if (itemEntity.getOwner() instanceof Player || droppedItem.resolve().get().getOwner() instanceof Player) {
                                        entityFell(entity);
                                    }
                                }
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
        Level serverLevel = entity.getLevel();
        MinecraftServer minecraftserver = serverLevel.getServer();
        if (minecraftserver != null) {
            ServerLevel destination = minecraftserver.getLevel(LevelUtil.returnDimension());
            if (destination != null && LevelUtil.returnDimension() != LevelUtil.destinationDimension()) {
                List<Entity> passengers = entity.getPassengers();
                serverLevel.getProfiler().push("aether_fall");
                entity.setPortalCooldown();
                Entity target = entity.changeDimension(destination, new AetherPortalForcer(destination, false));
                serverLevel.getProfiler().pop();
                // Check for passengers
                if (target != null) {
                    for (Entity passenger : passengers) {
                        passenger.stopRiding();
                        Entity nextPassenger = entityFell(passenger);
                        if (nextPassenger != null) {
                            nextPassenger.startRiding(target);
                            if (target instanceof ServerPlayer serverPlayer) { // Fixes a desync between the server and client
                                PacketRelay.sendToPlayer(AetherPacketHandler.INSTANCE, new SetVehiclePacket(nextPassenger.getId(), target.getId()), serverPlayer);
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

    public static void checkEternalDayConfig(Level level) {
        if (!level.isClientSide()) {
            AetherTime.get(level).ifPresent(aetherTime -> {
                boolean eternalDay = aetherTime.getEternalDay();
                if (AetherConfig.SERVER.disable_eternal_day.get() && eternalDay) {
                    aetherTime.setEternalDay(false);
                    aetherTime.updateEternalDay();
                }
            });
        }
    }

    public static void dimensionTravel(Entity entity, ResourceKey<Level> dimension) {
        // The level passed into shouldReturnPlayerToOverworld() is the dimension the player is leaving
        //  Meaning: We display the Descending GUI text to the player if they're about to leave a dimension that returns them to the OW
        if (entity instanceof Player player) {
            AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                if (!AetherConfig.SERVER.spawn_in_aether.get() || !aetherPlayer.canSpawnInAether()) {
                    if (entity.level.getBiome(entity.blockPosition()).is(AetherTags.Biomes.DISPLAY_TRAVEL_TEXT)) {
                        if (entity.level.dimension() == LevelUtil.destinationDimension() && dimension == LevelUtil.returnDimension()) {
                            displayAetherTravel = true;
                            playerLeavingAether = true;
                            PacketRelay.sendToAll(AetherPacketHandler.INSTANCE, new AetherTravelPacket(true));
                            PacketRelay.sendToAll(AetherPacketHandler.INSTANCE, new LeavingAetherPacket(true));
                        } else if (entity.level.dimension() == LevelUtil.returnDimension() && dimension == LevelUtil.destinationDimension()) {
                            displayAetherTravel = true;
                            playerLeavingAether = false;
                            PacketRelay.sendToAll(AetherPacketHandler.INSTANCE, new AetherTravelPacket(true));
                            PacketRelay.sendToAll(AetherPacketHandler.INSTANCE, new LeavingAetherPacket(false));
                        } else {
                            displayAetherTravel = false;
                            PacketRelay.sendToAll(AetherPacketHandler.INSTANCE, new AetherTravelPacket(false));
                        }
                    }
                }
            });
        }
    }

    public static void travelling(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (teleportationTimer > 0) {
                ServerGamePacketListenerImplAccessor serverGamePacketListenerImplAccessor = (ServerGamePacketListenerImplAccessor) serverPlayer.connection;
                serverGamePacketListenerImplAccessor.aether$setAboveGroundTickCount(0);
                serverGamePacketListenerImplAccessor.aether$setAboveGroundVehicleTickCount(0);
                teleportationTimer--;
            }
            if (teleportationTimer < 0 || serverPlayer.verticalCollisionBelow) {
                teleportationTimer = 0;
            }
        }
    }

    /**
     * Initializes the Aether level data for time separate from the overworld.
     * serverLevelData and levelData are access transformed.
     */
    public static void initializeLevelData(LevelAccessor level) {
        if (level instanceof ServerLevel serverLevel && serverLevel.dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
            AetherTime.get(serverLevel).ifPresent(cap -> {
                AetherLevelData levelData = new AetherLevelData(serverLevel.getServer().getWorldData(), serverLevel.getServer().getWorldData().overworldData(), cap.getDayTime());
                ServerLevelAccessor serverLevelAccessor = (ServerLevelAccessor) serverLevel;
                com.aetherteam.aether.mixin.mixins.common.accessor.LevelAccessor levelAccessor = (com.aetherteam.aether.mixin.mixins.common.accessor.LevelAccessor) level;
                serverLevelAccessor.aether$setServerLevelData(levelData);
                levelAccessor.aether$setLevelData(levelData);
            });
        }
    }

    /**
     * Resets the weather cycle if players finish sleeping in an Aether dimension.
     * Sets the time in the Aether according to the Aether's day/night cycle.
     */
    public static Long finishSleep(LevelAccessor level, long newTime) {
        if (level instanceof ServerLevel && level.dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
            ServerLevelAccessor serverLevelAccessor = (ServerLevelAccessor) level;
            serverLevelAccessor.aether$getServerLevelData().setRainTime(0);
            serverLevelAccessor.aether$getServerLevelData().setRaining(false);
            serverLevelAccessor.aether$getServerLevelData().setThunderTime(0);
            serverLevelAccessor.aether$getServerLevelData().setThundering(false);

            long time = newTime + 48000L;
            return time - time % (long) AetherDimensions.AETHER_TICKS_PER_DAY;
        }
        return null;
    }

    public static boolean isEternalDay(Player player) {
        if (player.getLevel().dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
            LazyOptional<AetherTime> aetherTimeLazy = AetherTime.get(player.getLevel());
            if (aetherTimeLazy.isPresent()) {
                Optional<AetherTime> aetherTimeOptional = aetherTimeLazy.resolve();
                if (aetherTimeOptional.isPresent()) {
                    AetherTime aetherTime = aetherTimeOptional.get();
                    return aetherTime.getEternalDay();
                }
            }
        }
        return false;
    }
}
