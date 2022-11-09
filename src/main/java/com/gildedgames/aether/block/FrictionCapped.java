package com.gildedgames.aether.block;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public interface FrictionCapped {
    default float getFriction(@Nullable Entity entity, float defaultFriction) {
        if (entity != null) {
            Vec3 motion = entity.getDeltaMovement();
            if (Math.abs(motion.x()) > 1.0 || Math.abs(motion.z()) > 1.0) {
                return 0.99F;
            }
        }
        return defaultFriction;
    }
}
