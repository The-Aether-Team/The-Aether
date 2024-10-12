package com.aetherteam.aether.event.hooks;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aether.block.portal.AetherPortalForcer;
import com.aetherteam.aether.block.portal.AetherPortalShape;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.mixin.mixins.common.accessor.ServerGamePacketListenerImplAccessor;
import com.aetherteam.aether.mixin.mixins.common.accessor.ServerLevelAccessor;
import com.aetherteam.aether.network.packet.clientbound.AetherTravelPacket;
import com.aetherteam.aether.network.packet.clientbound.LeavingAetherPacket;
import com.aetherteam.aether.world.AetherLevelData;
import com.aetherteam.aether.world.LevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.SleepFinishedTimeEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Optional;

public class DimensionHooks {
    public static boolean playerLeavingAether;
    public static boolean displayAetherTravel;
    public static int teleportationTimer;

    /**
     * Spawns the player in the Aether dimension if the {@link AetherConfig.Server#spawn_in_aether} config is enabled.
     *
     * @param player The {@link Player}.
     * @see com.aetherteam.aether.event.listeners.DimensionListener#onPlayerLogin(PlayerEvent.PlayerLoggedInEvent)
     */
    public static void startInAether(Player player) { //todo
//        var aetherPlayer = player.getData(AetherDataAttachments.AETHER_PLAYER);
//        if (AetherConfig.SERVER.spawn_in_aether.get()) {
//            if (aetherPlayer.canSpawnInAether()) { // Checks if the player has been set to spawn in the Aether.
//                if (player instanceof ServerPlayer serverPlayer) {
//                    MinecraftServer server = serverPlayer.level().getServer();
//                    if (server != null) {
//                        ServerLevel aetherLevel = server.getLevel(AetherDimensions.AETHER_LEVEL);
//                        if (aetherLevel != null && serverPlayer.level().dimension() == Level.OVERWORLD) {
//                            if (player.changeDimension(aetherLevel, new AetherPortalForcer(aetherLevel, false, true)) != null) {
//                                serverPlayer.setRespawnPosition(AetherDimensions.AETHER_LEVEL, serverPlayer.blockPosition(), serverPlayer.getYRot(), true, false);
//                                aetherPlayer.setCanSpawnInAether(false); // Sets that the player has already spawned in the Aether.
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            aetherPlayer.setCanSpawnInAether(false);
//        }
    }

    /**
     * Used to handle creating an Aether portal from a glowstone frame if the correct activation item is used.
     *
     * @param player    The {@link Player} creating the portal.
     * @param level     The {@link Level} to create the portal in.
     * @param pos       The {@link BlockPos} to create the portal at.
     * @param direction The {@link Direction} of where the portal is interacted at.
     * @param stack     The {@link ItemStack} used to attempt to activate the portal.
     * @param hand      The {@link InteractionHand} that the item is in.
     * @return Whether the portal should be created, as a {@link Boolean}.
     * @see com.aetherteam.aether.event.listeners.DimensionListener#onInteractWithPortalFrame(PlayerInteractEvent.RightClickBlock)
     */
    public static boolean createPortal(Player player, Level level, BlockPos pos, @Nullable Direction direction, ItemStack stack, InteractionHand hand) {
        if (direction != null) {
            BlockPos relativePos = pos.relative(direction);
            if (stack.is(AetherTags.Items.AETHER_PORTAL_ACTIVATION_ITEMS)) { // Checks if the item can activate the portal.
                // Checks whether the dimension can have a portal created in it, and that the portal isn't disabled.
                if ((level.dimension() == LevelUtil.returnDimension() || level.dimension() == LevelUtil.destinationDimension())) {
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
                                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
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

    /**
     * Detects whether water is found in a glowstone frame.
     *
     * @param levelAccessor The {@link Level} to create the portal in.
     * @param pos           The {@link BlockPos} to create the portal at.
     * @param blockState    The water {@link BlockState}.
     * @param fluidState    The water {@link FluidState}.
     * @return Whether the portal should be created, as a {@link Boolean}.
     * @see com.aetherteam.aether.event.listeners.DimensionListener#onWaterExistsInsidePortalFrame(BlockEvent.NeighborNotifyEvent)
     */
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
     *
     * @param level The {@link Level}
     * @see com.aetherteam.aether.event.listeners.DimensionListener#onWorldTick(TickEvent.LevelTickEvent)
     */
    public static void tickTime(Level level) {
        if (level.dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location()) && level instanceof ServerLevel serverLevel) {
            ServerLevelAccessor serverLevelAccessor = (ServerLevelAccessor) serverLevel;
            com.aetherteam.aether.mixin.mixins.common.accessor.LevelAccessor levelAccessor = (com.aetherteam.aether.mixin.mixins.common.accessor.LevelAccessor) level;
            long i = levelAccessor.aether$getLevelData().getGameTime() + 1L;
            serverLevelAccessor.aether$getServerLevelData().setGameTime(i);
            if (serverLevelAccessor.aether$getServerLevelData().getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                serverLevel.setDayTime(serverLevel.getData(AetherDataAttachments.AETHER_TIME).tickTime(level));
            }
        }
    }

    /**
     * Checks whether eternal day is configured to be disabled, and disables it in the {@link AetherPlayerAttachment}.
     *
     * @param level The {@link Level}
     * @see com.aetherteam.aether.event.listeners.DimensionListener#onWorldTick(TickEvent.LevelTickEvent)
     */
    public static void checkEternalDayConfig(Level level) {
        if (!level.isClientSide() && level.hasData(AetherDataAttachments.AETHER_TIME)) {
            var aetherTime = level.getData(AetherDataAttachments.AETHER_TIME);
            boolean eternalDay = aetherTime.isEternalDay();
            if (AetherConfig.SERVER.disable_eternal_day.get() && eternalDay) {
                aetherTime.setEternalDay(false);
                aetherTime.updateEternalDay(level);
            }
        }
    }

    /**
     * @param entity    The {@link Entity} travelling between dimensions.
     * @param dimension The {@link ResourceKey} of the dimension ({@link Level}) being teleported to.
     * @see com.aetherteam.aether.event.listeners.DimensionListener#onEntityTravelToDimension(EntityTravelToDimensionEvent)
     */
    public static void dimensionTravel(Entity entity, ResourceKey<Level> dimension) {
        if (entity instanceof Player player) {
            if (!player.level().isClientSide()) {
                var aetherPlayer = player.getData(AetherDataAttachments.AETHER_PLAYER);
                if (!AetherConfig.SERVER.spawn_in_aether.get() || !aetherPlayer.canSpawnInAether()) {
                    if (entity.level().getBiome(entity.blockPosition()).is(AetherTags.Biomes.DISPLAY_TRAVEL_TEXT)) {
                        if (entity.level().dimension() == LevelUtil.destinationDimension() && dimension == LevelUtil.returnDimension()) { // We display the Descending GUI text to the player if they're about to return to the Overworld.
                            displayAetherTravel = true;
                            playerLeavingAether = true;
                            PacketDistributor.sendToAllPlayers(new AetherTravelPacket(true));
                            PacketDistributor.sendToAllPlayers(new LeavingAetherPacket(true));
                        } else if (entity.level().dimension() == LevelUtil.returnDimension() && dimension == LevelUtil.destinationDimension()) { // We display the Ascending GUI text to the player if they're about to enter the Aether.
                            displayAetherTravel = true;
                            playerLeavingAether = false;
                            PacketDistributor.sendToAllPlayers(new AetherTravelPacket(true));
                            PacketDistributor.sendToAllPlayers(new LeavingAetherPacket(false));
                        } else { // Don't display any text if not travelling between the Aether and Overworld or vice-versa.
                            displayAetherTravel = false;
                            PacketDistributor.sendToAllPlayers(new AetherTravelPacket(false));
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if the player was falling out of the Aether, and prevents server fly-hack checks during this.
     *
     * @param player The {@link Player}.
     * @see com.aetherteam.aether.event.listeners.DimensionListener#onPlayerTraveling(TickEvent.PlayerTickEvent)
     */
    public static void travelling(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (teleportationTimer > 0) { // Prevents the player from being kicked for flying.
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
     *
     * @param level The {@link LevelAccessor}.
     * @see com.aetherteam.aether.event.listeners.DimensionListener#onPlayerTraveling(TickEvent.PlayerTickEvent)
     */
    public static void initializeLevelData(LevelAccessor level) {
        if (level instanceof ServerLevel serverLevel && serverLevel.dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
            AetherLevelData levelData = new AetherLevelData(serverLevel.getServer().getWorldData(), serverLevel.getServer().getWorldData().overworldData(), serverLevel.getData(AetherDataAttachments.AETHER_TIME).getDayTime());
            ServerLevelAccessor serverLevelAccessor = (ServerLevelAccessor) serverLevel;
            com.aetherteam.aether.mixin.mixins.common.accessor.LevelAccessor levelAccessor = (com.aetherteam.aether.mixin.mixins.common.accessor.LevelAccessor) level;
            serverLevelAccessor.aether$setServerLevelData(levelData);
            levelAccessor.aether$setLevelData(levelData);
        }
    }

    /**
     * Resets the weather cycle if players finish sleeping in an Aether dimension.<br>
     * Sets the time in the Aether according to the Aether's day/night cycle.
     *
     * @param level The {@link LevelAccessor}.
     * @see com.aetherteam.aether.event.listeners.DimensionListener#onSleepFinish(SleepFinishedTimeEvent)
     */
    @Nullable
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

    /**
     * Checks whether it is eternal day in the Aether.
     *
     * @param player The {@link Player}.
     * @return Whether it is eternal day, as a {@link Boolean}.
     * @see com.aetherteam.aether.event.listeners.DimensionListener#onTriedToSleep(SleepingTimeCheckEvent)
     */
    public static boolean isEternalDay(Player player) {
        if (player.level().dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
            return player.getData(AetherDataAttachments.AETHER_TIME).isEternalDay();
        }
        return false;
    }
}
