package com.gildedgames.aether.entity;

import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.block.dungeon.InvisibleBlock;
import com.gildedgames.aether.block.dungeon.TreasureRoomBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

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
                return state.setValue(TreasureRoomBlock.INVISIBLE, false);
            } else {
                return null;
            }
        });
    }

    default void openRoom() {
        this.getDungeon().modifyRoom(state -> {
            if (state.getBlock() instanceof InvisibleBlock) {
                return state.setValue(TreasureRoomBlock.INVISIBLE, true);
            } else {
                return null;
            }
        });
    }

    @Nullable
    BlockState convertBlock(BlockState state);
}
