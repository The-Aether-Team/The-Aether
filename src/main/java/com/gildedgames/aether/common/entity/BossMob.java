package com.gildedgames.aether.common.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Mob;

/**
 * Interface for applying names to Aether bosses without displaying a name tag.
 */
public interface BossMob {
    Component getBossName();
    void setBossName(Component component);
}
