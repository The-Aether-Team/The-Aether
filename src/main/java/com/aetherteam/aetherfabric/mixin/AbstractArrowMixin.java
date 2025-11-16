package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.events.ProjectileEvents;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {
    @Definition(id = "hitResult", local = @Local(type = HitResult.class))
    @Definition(id = "bl", local = @Local(ordinal = 0, type = Boolean.class))
    @Expression("hitResult != null")
    @ModifyExpressionValue(method = "tick", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 1))
    private boolean aether$neoParityAdjustExpression(boolean original, @Local() HitResult hitResult) {
        return original && hitResult.getType() != HitResult.Type.MISS;
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;hitTargetOrDeflectSelf(Lnet/minecraft/world/phys/HitResult;)Lnet/minecraft/world/entity/projectile/ProjectileDeflection;"))
    private ProjectileDeflection aether$projectileImpactEvent(AbstractArrow instance, HitResult hitResult, Operation<ProjectileDeflection> original) {
        return ProjectileEvents.adjustDeflection(instance, hitResult, ProjectileEvents.EMPTY_DEFLECTION, () -> original.call(instance, hitResult));
    }

    @Definition(id = "hasImpulse", field = "Lnet/minecraft/world/entity/projectile/AbstractArrow;hasImpulse:Z")
    @Expression("this.hasImpulse = true")
    @WrapOperation(method = "tick", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private void aether$preventImpulseFlagging(AbstractArrow instance, boolean value, Operation<Void> original, @Local() ProjectileDeflection deflection) {
        if (deflection != ProjectileEvents.EMPTY_DEFLECTION) original.call(instance, value);
    }
}
