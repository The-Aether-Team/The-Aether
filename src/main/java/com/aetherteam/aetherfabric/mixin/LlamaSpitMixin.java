package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.events.ProjectileEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LlamaSpit.class)
public abstract class LlamaSpitMixin {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/LlamaSpit;hitTargetOrDeflectSelf(Lnet/minecraft/world/phys/HitResult;)Lnet/minecraft/world/entity/projectile/ProjectileDeflection;"))
    private ProjectileDeflection aetherFabric$adjustImpact(LlamaSpit instance, HitResult hitResult, Operation<ProjectileDeflection> original) {
        return ProjectileEvents.adjustDeflection(instance, hitResult, ProjectileDeflection.NONE, () -> original.call(instance, hitResult));
    }
}
