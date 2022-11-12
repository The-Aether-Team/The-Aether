package com.gildedgames.aether.mixin.mixins.accessor;

import net.minecraft.world.entity.animal.Bee;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Bee.class)
public interface BeeAccessor {
    @Invoker("getCropsGrownSincePollination")
    int getCropsGrownSincePollination();

    @Invoker("isHiveValid")
    boolean isHiveValid();

    @Invoker("incrementNumCropsGrownSincePollination")
    void incrementNumCropsGrownSincePollination();
}
