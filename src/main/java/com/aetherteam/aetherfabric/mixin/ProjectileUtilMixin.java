package com.aetherteam.aetherfabric.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ProjectileUtil.class)
public abstract class ProjectileUtilMixin {
    @Definition(id = "entity2", local = @Local(type = Entity.class, ordinal = 2))
    @Definition(id = "shooter", local = @Local(type = Entity.class, ordinal = 0, argsOnly = true))
    @Definition(id = "getRootVehicle", method = "Lnet/minecraft/world/entity/Entity;getRootVehicle()Lnet/minecraft/world/entity/Entity;")
    @Expression("entity2.getRootVehicle() == shooter.getRootVehicle()")
    @ModifyExpressionValue(
        method = "getEntityHitResult(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;D)Lnet/minecraft/world/phys/EntityHitResult;",
        at= @At("MIXINEXTRAS:EXPRESSION")
    )
    private static boolean aetherFabric$adjustIfEntityHit(boolean original, @Local(type = Entity.class, ordinal = 2) Entity entity2) {
        return original && !entity2.aetherFabric$canRiderInteract();
    }
}
