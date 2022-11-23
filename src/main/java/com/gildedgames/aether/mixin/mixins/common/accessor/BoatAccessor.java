package com.gildedgames.aether.mixin.mixins.common.accessor;

import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Boat.class)
public interface BoatAccessor {
    @Accessor
    void setLastYd(double lastYd);

    @Accessor
    Boat.Status getStatus();
}