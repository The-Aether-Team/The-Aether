package com.aetherteam.aether.capability.arrow;

import com.aetherteam.aether.capability.AetherCapabilities;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.AbstractArrow;

import java.util.Optional;

public interface PhoenixArrow extends INBTSynchable<CompoundTag>, Component {
    AbstractArrow getArrow();

    static Optional<PhoenixArrow> get(AbstractArrow arrow) {
        return AetherCapabilities.PHOENIX_ARROW_CAPABILITY.maybeGet(arrow);
    }

    void setPhoenixArrow(boolean isPhoenixArrow);
    boolean isPhoenixArrow();

    void setFireTime(int time);
    int getFireTime();
}
