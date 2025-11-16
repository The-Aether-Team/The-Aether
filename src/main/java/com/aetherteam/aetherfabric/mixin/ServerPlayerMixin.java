package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.events.EntityEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.DimensionTransition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Inject(method = "changeDimension", at = @At("HEAD"))
    private void aetherFabric$beforeDimensionChange(DimensionTransition transition, CallbackInfoReturnable<Entity> cir) {
        EntityEvents.BEFORE_DIMENSION_CHANGE.invoker().beforeChange((ServerPlayer) (Object) this, transition.newLevel().dimension());
    }
}
