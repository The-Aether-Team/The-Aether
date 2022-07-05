package com.gildedgames.aether.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

/**
 * Interface for applying names to Aether bosses without displaying a name tag.
 */
public interface BossMob {
    TargetingConditions NON_COMBAT = TargetingConditions.forNonCombat();
    Component getBossName();
    void setBossName(Component component);
}
