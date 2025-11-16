package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.events.ProjectileEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin {
    @WrapOperation(method = "checkCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/FishingHook;hitTargetOrDeflectSelf(Lnet/minecraft/world/phys/HitResult;)Lnet/minecraft/world/entity/projectile/ProjectileDeflection;"))
    private ProjectileDeflection aetherFabric$adjustImpact(FishingHook instance, HitResult hitResult, Operation<ProjectileDeflection> original) {
        return ProjectileEvents.adjustDeflection(instance, hitResult, ProjectileDeflection.NONE, () -> original.call(instance, hitResult));
    }
}
