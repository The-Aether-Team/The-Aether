package com.aetherteam.aether.entity;

import com.aetherteam.aether.block.dungeon.DoorwayBlock;
import com.aetherteam.nitrogen.entity.BossMob;
import net.minecraft.world.entity.Mob;

/**
 * Interface for applying names to Aether bosses without displaying a name tag.
 * This also handles dungeon tracking.
 */
public interface AetherBossMob<T extends Mob & AetherBossMob<T>> extends BossMob<T> {
    default void closeRoom() {
        this.getDungeon().modifyRoom(state -> {
            if (state.getBlock() instanceof DoorwayBlock) {
                return state.setValue(DoorwayBlock.INVISIBLE, false);
            } else {
                return null;
            }
        });
    }

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
