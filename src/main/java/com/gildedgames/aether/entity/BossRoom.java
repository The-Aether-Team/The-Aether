package com.gildedgames.aether.entity;

import com.gildedgames.aether.api.DungeonTracker;
import net.minecraft.world.entity.Mob;

public interface BossRoom<T extends Mob & BossRoom<T>> { //TODO: To be merged with BossMob
    DungeonTracker<T> getDungeon();
    void setDungeon(DungeonTracker<T> dungeon);

    int getDeathScore();
}
