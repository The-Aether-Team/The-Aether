package com.aetherteam.aether.entity;

import com.aetherteam.aether.block.dungeon.DoorwayBlock;
import com.aetherteam.nitrogen.entity.BossMob;
import net.minecraft.world.entity.Mob;

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
}
