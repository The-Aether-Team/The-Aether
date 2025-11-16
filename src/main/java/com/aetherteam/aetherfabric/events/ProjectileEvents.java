package com.aetherteam.aetherfabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.phys.HitResult;

import java.util.function.Supplier;

public class ProjectileEvents {

    public static final ProjectileDeflection EMPTY_DEFLECTION = (projectile, entity, randomSource) -> {};

    public static final Event<OnImpact> ON_IMPACT = EventFactory.createArrayBacked(OnImpact.class, invokers -> (projectile, hitResult, callback) -> {
        for (var invoker : invokers) invoker.onImpact(projectile, hitResult, callback);
    });

    public static boolean shouldCancelImpact(Projectile projectile, HitResult hitResult) {
        var callback = new CancellableCallbackImpl(false);

        if (hitResult.getType() != HitResult.Type.MISS) {
            ProjectileEvents.ON_IMPACT.invoker().onImpact(projectile, hitResult, callback);
        }

        return callback.isCanceled();
    }

    public static ProjectileDeflection adjustDeflection(Projectile projectile, HitResult hitResult, ProjectileDeflection canceledProjectile, Supplier<ProjectileDeflection> originalDeflection) {
        return shouldCancelImpact(projectile, hitResult) ? canceledProjectile : originalDeflection.get();
    }

    public interface OnImpact {
        void onImpact(Projectile projectile, HitResult hitResult, CancellableCallback callback);
    }
}
