package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.event.hooks.EntityHooks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.event.EventHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EventHooks.class)
public class EventHooksMixin {
    /**
     * This handles spawning mobs with accessories right after the call to {@link Mob#finalizeSpawn(ServerLevelAccessor, DifficultyInstance, MobSpawnType, SpawnGroupData, CompoundTag)}.
     *
     * @see EntityHooks#canMobSpawnWithAccessories(Entity)
     * @see EntityHooks#spawnWithAccessories(Entity, DifficultyInstance)
     */
    @Inject(at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/Mob;finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/world/entity/SpawnGroupData;)Lnet/minecraft/world/entity/SpawnGroupData;"), method = "finalizeMobSpawn(Lnet/minecraft/world/entity/Mob;Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/world/entity/SpawnGroupData;)Lnet/minecraft/world/entity/SpawnGroupData;")
    private static void onFinalizeSpawn(Mob mob, ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnData, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (EntityHooks.canMobSpawnWithAccessories(mob)) {
            EntityHooks.spawnWithAccessories(mob, difficulty);
        }
    }
}
