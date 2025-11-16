package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.events.ProjectileEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractHurtingProjectile.class)
public abstract class AbstractHurtingProjectileMixin {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractHurtingProjectile;hitTargetOrDeflectSelf(Lnet/minecraft/world/phys/HitResult;)Lnet/minecraft/world/entity/projectile/ProjectileDeflection;"))
    private ProjectileDeflection aetherFabric$adjustImpact(AbstractHurtingProjectile instance, HitResult hitResult, Operation<ProjectileDeflection> original) {
        return ProjectileEvents.adjustDeflection(instance, hitResult, ProjectileDeflection.NONE, () -> original.call(instance, hitResult));
    }
}
