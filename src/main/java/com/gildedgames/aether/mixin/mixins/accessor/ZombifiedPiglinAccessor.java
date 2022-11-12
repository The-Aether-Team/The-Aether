package com.gildedgames.aether.mixin.mixins.accessor;

import net.minecraft.world.entity.monster.ZombifiedPiglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ZombifiedPiglin.class)
public interface ZombifiedPiglinAccessor {
    @Invoker("alertOthers")
    void alertOthers();
}
