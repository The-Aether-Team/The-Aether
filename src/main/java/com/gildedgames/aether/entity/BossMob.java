package com.gildedgames.aether.entity;

import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.block.dungeon.InvisibleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Interface for applying names to Aether bosses without displaying a name tag.
 * This also handles dungeon tracking.
 */
public interface BossMob<T extends Mob & BossMob<T>> {
    private T self() {
        return (T) this;
    }
    TargetingConditions NON_COMBAT = TargetingConditions.forNonCombat();
    Component getBossName();
    void setBossName(Component component);

    boolean isBossFight();
    void setBossFight(boolean isFighting);

    DungeonTracker<T> getDungeon();
    void setDungeon(DungeonTracker<T> dungeon);

    int getDeathScore();

    default void trackDungeon() {
        if (this.getDungeon() != null) {
            this.getDungeon().trackPlayers();
            if (this.isBossFight() && (this.getDungeon().dungeonPlayers().isEmpty() || !this.getDungeon().isBossWithinRoom())) {
                this.reset();
            }
        }
    }

    void reset();

    /**
     * Called when the boss is defeated to change all blocks to unlocked blocks.
     */
    default void tearDownRoom() {
        this.getDungeon().modifyRoom(this::convertBlock);
    }


    default void closeRoom() {
        this.getDungeon().modifyRoom(state -> {
            if (state.getBlock() instanceof InvisibleBlock) {
                return state.setValue(InvisibleBlock.INVISIBLE, false);
            } else {
                return null;
            }
        });
    }

    default void openRoom() {
        this.getDungeon().modifyRoom(state -> {
            if (state.getBlock() instanceof InvisibleBlock) {
                return state.setValue(InvisibleBlock.INVISIBLE, true);
            } else {
                return null;
            }
        });
    }

    @Nullable
    BlockState convertBlock(BlockState state);
}
