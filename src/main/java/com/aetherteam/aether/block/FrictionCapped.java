package com.aetherteam.aether.block;

import com.aetherteam.aether.mixin.mixins.common.accessor.BoatAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public interface FrictionCapped {
    /**
     * Limits the friction that an entity experiences on a block if the entity begins moving too fast.
     *
     * @param entity          The {@link Entity} experiencing friction.
     * @param defaultFriction The {@link Float} for the default block friction.
     * @return A new friction value as a {@link Float}.
     */
    default float getCappedFriction(@Nullable Entity entity, float defaultFriction) {
        if (entity != null) {
            Vec3 motion = entity.getDeltaMovement();
            if (entity instanceof Boat boat) {
                float deltaRotation = ((BoatAccessor) boat).aether$getDeltaRotation();
                if (deltaRotation > 25.0F) {
                    ((BoatAccessor) boat).aether$setDeltaRotation(25.0F);
                } else if (deltaRotation < -25.0F) {
                    ((BoatAccessor) boat).aether$setDeltaRotation(-25.0F);
                }
            }
            if (Math.abs(motion.x()) > 1.0 || Math.abs(motion.z()) > 1.0) {
                return 0.99F;
            }
        }
        return defaultFriction;
    }
}
