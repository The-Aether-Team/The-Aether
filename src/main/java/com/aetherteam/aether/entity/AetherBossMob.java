package com.aetherteam.aether.entity;

import com.aetherteam.aether.block.dungeon.DoorwayBlock;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.nitrogen.entity.BossMob;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.ForgeEventFactory;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Predicate;

/**
 * Interface for handling boss-related behavior for mobs.
 * @see BossMob
 */
public interface AetherBossMob<T extends Mob & AetherBossMob<T>> extends BossMob<T> {
    /**
     * Handles behavior when closing the boss room, like closing the doors.
     */
    default void closeRoom() {
        this.getDungeon().modifyRoom(state -> {
            if (state.getBlock() instanceof DoorwayBlock) {
                return state.setValue(DoorwayBlock.INVISIBLE, false);
            } else {
                return null;
            }
        });
    }

    /**
     * Handles behavior when opening the boss room, like opening the doors.
     */
    default void openRoom() {
        this.getDungeon().modifyRoom(state -> {
            if (state.getBlock() instanceof DoorwayBlock) {
                return state.setValue(DoorwayBlock.INVISIBLE, true);
            } else {
                return null;
            }
        });
    }

    /**
     * Evaporates a liquid block.
     * @param entity The boss entity.
     * @param min The minimum {@link BlockPos} bounds corner.
     * @param max The maximum {@link BlockPos} bounds corner.
     * @param check An additional check using a {@link BlockState} {@link Predicate}.
     */
    default void evaporate(T entity, BlockPos min, BlockPos max, Predicate<BlockState> check) {
        if (ForgeEventFactory.getMobGriefingEvent(entity.level(), entity)) {
            for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
                if (entity.level().getBlockState(pos).getBlock() instanceof LiquidBlock && check.test(entity.level().getBlockState(pos))) {
                    entity.level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    this.evaporateEffects(entity, pos);
                } else if (!entity.level().getFluidState(pos).isEmpty() && entity.level().getBlockState(pos).hasProperty(BlockStateProperties.WATERLOGGED) && check.test(entity.level().getFluidState(pos).createLegacyBlock())) {
                    entity.level().setBlockAndUpdate(pos, entity.level().getBlockState(pos).setValue(BlockStateProperties.WATERLOGGED, false));
                    this.evaporateEffects(entity, pos);
                }
            }
        }
    }

    /**
     * Spawns particles and plays sounds for evaporation.
     * @param entity The boss entity.
     * @param pos The {@link BlockPos} for effects.
     */
    default void evaporateEffects(T entity, BlockPos pos) {
        EntityUtil.spawnRemovalParticles(entity.level(), pos);
        entity.level().playSound(null, pos, AetherSoundEvents.WATER_EVAPORATE.get(), SoundSource.BLOCKS, 0.5F, 2.6F + (entity.level().getRandom().nextFloat() - entity.level().getRandom().nextFloat()) * 0.8F);
    }

    /**
     * The default minimum and maximum positions for expanded entity bounds.
     * @param entity The boss entity.
     * @return A {@link Pair} of the minimum {@link BlockPos} and the maximum {@link BlockPos}.
     */
    default Pair<BlockPos, BlockPos> getDefaultBounds(T entity) {
        AABB boundingBox = entity.getBoundingBox();
        BlockPos min = BlockPos.containing(boundingBox.minX - 1, boundingBox.minY - 1, boundingBox.minZ - 1);
        BlockPos max = BlockPos.containing(Math.ceil(boundingBox.maxX - 1) + 1, Math.ceil(boundingBox.maxY - 1) + 1, Math.ceil(boundingBox.maxZ - 1) + 1);
        return Pair.of(min, max);
    }
}
