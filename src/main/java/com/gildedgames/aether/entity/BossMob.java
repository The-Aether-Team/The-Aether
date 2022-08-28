package com.gildedgames.aether.entity;

import com.gildedgames.aether.api.DungeonTracker;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

/**
 * Interface for applying names to Aether bosses without displaying a name tag.
 * This also handles dungeon tracking.
 */
public interface BossMob<T extends Mob & BossMob<T>> {
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
}
