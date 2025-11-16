package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.events.LivingEntityEvents;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {AbstractHorse.class, Camel.class})
public abstract class AbstractHorseTypesJumpMixin {
    @Definition(id = "hasImpulse", field = {
        "Lnet/minecraft/world/entity/animal/camel/Camel;hasImpulse:Z",
        "Lnet/minecraft/world/entity/animal/horse/AbstractHorse;hasImpulse:Z"
    })
    @Expression("this.hasImpulse = true")
    @Inject(method = "executeRidersJump", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER))
    private void aetherFabric$fireJumpEvent(CallbackInfo ci){
        LivingEntityEvents.ON_JUMP.invoker().onJump((LivingEntity) (Object) this);
    }
}
