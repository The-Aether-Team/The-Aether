package com.gildedgames.aether.mixin.mixins.accessor;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Player.class)
public interface PlayerAccessor {
    @Invoker("hurtCurrentlyUsedShield")
    void hurtCurrentlyUsedShield(float damage);
}
