package com.aetherteam.aether.event.hooks;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.FreezingBlock;
import com.aetherteam.aether.block.portal.AetherPortalForcer;
import com.aetherteam.aether.block.portal.AetherPortalShape;
import com.aetherteam.aether.capability.item.DroppedItem;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.AetherGameEvents;
import com.aetherteam.aether.mixin.mixins.common.accessor.ServerGamePacketListenerImplAccessor;
import com.aetherteam.aether.mixin.mixins.common.accessor.ServerLevelAccessor;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.recipes.ban.BlockBanRecipe;
import com.aetherteam.aether.recipe.recipes.ban.ItemBanRecipe;
import com.aetherteam.aether.recipe.recipes.block.PlacementConversionRecipe;
import com.aetherteam.aether.world.LevelUtil;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.capability.time.AetherTime;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.clientbound.AetherTravelPacket;
import com.aetherteam.aether.network.packet.clientbound.LeavingAetherPacket;
import com.aetherteam.aether.network.packet.clientbound.SetVehiclePacket;
import com.aetherteam.nitrogen.network.PacketRelay;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
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

    public static boolean checkInteractionBanned(Player player, Level level, BlockPos pos, Direction face, ItemStack stack, BlockState state, boolean spawnParticles) {
        if (isItemPlacementBanned(level, pos, face, stack, spawnParticles)) {
            return true;
        }
        if (level.getBiome(pos).is(AetherTags.Biomes.ULTRACOLD) && AetherConfig.SERVER.enable_bed_explosions.get()) {
            if (state.is(BlockTags.BEDS) && state.getBlock() != AetherBlocks.SKYROOT_BED.get()) {
                if (!level.isClientSide()) {
                    if (state.getValue(BedBlock.PART) != BedPart.HEAD) {
                        pos = pos.relative(state.getValue(BedBlock.FACING));
                        state = level.getBlockState(pos);
                    }
                    BlockPos blockpos = pos.relative(state.getValue(BedBlock.FACING).getOpposite());
                    if (level.getBlockState(blockpos).is(BlockTags.BEDS) && level.getBlockState(blockpos).getBlock() != AetherBlocks.SKYROOT_BED.get()) {
                        level.removeBlock(blockpos, false);
                    }
                    Vec3 vec3 = pos.getCenter();
                    level.explode(null, level.damageSources().badRespawnPointExplosion(vec3), null, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, 5.0F, true, Level.ExplosionInteraction.BLOCK);
                }
                player.swing(InteractionHand.MAIN_HAND);
                return true;
            }
        }
        return false;
    }

    public static boolean isItemPlacementBanned(Level level, BlockPos pos, Direction face, ItemStack stack, boolean spawnParticles) {
        for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(AetherRecipeTypes.ITEM_PLACEMENT_BAN.get())) {
            if (recipe instanceof ItemBanRecipe banRecipe) {
                if (banRecipe.banItem(level, pos, face, stack, spawnParticles)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void checkExistenceBanned(LevelAccessor levelAccessor, BlockPos pos) {
        if (levelAccessor instanceof Level level) {
            BlockState state = levelAccessor.getBlockState(pos);
            if (DimensionHooks.isBlockPlacementBanned(level, pos, state)) {
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                if (state.getBlock().asItem() != Items.AIR) {
                    Block.dropResources(state, level, pos);
                }
            } else {
                DimensionHooks.isBlockPlacementConvertable(level, pos, state);
            }
        }
    }

    private static boolean isBlockPlacementBanned(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide()) {
            for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(AetherRecipeTypes.BLOCK_PLACEMENT_BAN.get())) {
                if (recipe instanceof BlockBanRecipe banRecipe) {
                    if (banRecipe.banBlock(level, pos, state)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void isBlockPlacementConvertable(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide()) {
            for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(AetherRecipeTypes.PLACEMENT_CONVERSION.get())) {
                if (recipe instanceof PlacementConversionRecipe conversionRecipe) {
                    if (conversionRecipe.convert(level, pos, state)) {
                        return;
                    }
                }
            }
        }
    }

    public static void banOrConvert(LevelAccessor accessor, BlockPos pos) {
        if (accessor instanceof ServerLevel serverLevel) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + 0.5;
            for (int i = 0; i < 10; i++) {
                serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, x, y, z, 1, 0.0,0.0, 0.0, 0.0F);
            }
            serverLevel.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
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
        Level serverLevel = entity.level;
        MinecraftServer minecraftserver = serverLevel.getServer();
        if (minecraftserver != null) {
            ServerLevel destination = minecraftserver.getLevel(LevelUtil.returnDimension());
            if (destination != null && LevelUtil.returnDimension() != LevelUtil.destinationDimension()) {
                List<Entity> passengers = entity.getPassengers();
                entity.level.getProfiler().push("aether_fall");
                entity.setPortalCooldown();
                Entity target = entity.changeDimension(destination, new AetherPortalForcer(destination, false));
                entity.level.getProfiler().pop();
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

    public static void sendIcestoneFreezableUpdateEvent(LevelAccessor accessor, BlockPos pos) {
        if (accessor instanceof Level level && !level.isClientSide())  {
            BlockState oldBlockState = level.getBlockState(pos);
            FreezingBlock.cacheRecipes(level);
            if (FreezingBlock.matchesCache(oldBlockState.getBlock(), oldBlockState) != null) {
                level.gameEvent(null, AetherGameEvents.ICESTONE_FREEZABLE_UPDATE.get(), pos);
            }
        }
    }
}
