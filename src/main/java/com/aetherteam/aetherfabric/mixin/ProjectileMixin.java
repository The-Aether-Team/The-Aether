package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.events.ProjectileEvents;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Projectile.class)
public abstract class ProjectileMixin {
    @WrapMethod(method = "onHit")
    private void aetherFabric$adjustImpact(HitResult result, Operation<Void> original) {
        if (((Projectile)(Object) this) instanceof FireworkRocketEntity entity && ProjectileEvents.shouldCancelImpact(entity, result)) {
            return;
        }

        original.call(result);
    }
}
