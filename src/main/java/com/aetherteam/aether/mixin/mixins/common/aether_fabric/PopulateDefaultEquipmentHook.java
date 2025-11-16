package com.aetherteam.aether.mixin.mixins.common.aether_fabric;

import com.aetherteam.aether.event.hooks.EntityHooks;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.piglin.Piglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {Mob.class, Piglin.class})
public abstract class PopulateDefaultEquipmentHook {
    @Inject(at = @At(value = "TAIL"), method = "populateDefaultEquipmentSlots")
    private void aetherFabric$onFinalizeSpawn(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        if (EntityHooks.canMobSpawnWithAccessories((Mob)(Object) this)) {
            EntityHooks.spawnWithAccessories((Mob)(Object) this, difficulty);
        }
    }
}
