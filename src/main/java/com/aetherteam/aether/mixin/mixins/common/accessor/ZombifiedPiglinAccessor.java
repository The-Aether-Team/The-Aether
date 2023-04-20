package com.aetherteam.aether.mixin.mixins.common.accessor;

import net.minecraft.world.entity.monster.ZombifiedPiglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ZombifiedPiglin.class)
public interface ZombifiedPiglinAccessor {
    @Invoker
    void callAlertOthers();
}