package com.aetherteam.aether.entity.miscellaneous;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.mixin.mixins.common.accessor.BoatAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Used for replicated behavior between Skyroot Boat entities.
 */
public interface SkyrootBoatBehavior {
    /**
     * [CODE COPY] - {@link Boat#checkFallDamage(double, boolean, BlockState, BlockPos)}.<br><br>
     * Replace wood usage with Skyroot.
     */
    default void fall(Boat boat, double y, boolean onGround) {
        BoatAccessor boatAccessor = (BoatAccessor) boat;
        boatAccessor.aether$setLastYd(boat.getDeltaMovement().y);
        if (!boat.isPassenger()) {
            if (onGround) {
                if (boat.fallDistance > 3.0F) {
                    if (boatAccessor.aether$getStatus() != Boat.Status.ON_LAND) {
                        boat.resetFallDistance();
                        return;
                    }
                    boat.causeFallDamage(boat.fallDistance, 1.0F, boat.damageSources().fall());
                    if (!boat.level().isClientSide() && !boat.isRemoved()) {
                        boat.kill();
                        if (boat.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            for(int i = 0; i < 3; ++i) {
                                boat.spawnAtLocation(AetherBlocks.SKYROOT_PLANKS.get());
                            }

                            for(int j = 0; j < 2; ++j) {
                                boat.spawnAtLocation(AetherItems.SKYROOT_STICK.get());
                            }
                        }
                    }
                }
                boat.resetFallDistance();
            } else if (!boat.level().getFluidState(boat.blockPosition().below()).is(FluidTags.WATER) && y < 0.0) {
                boat.fallDistance -= (float) y;
            }
        }
    }
}
