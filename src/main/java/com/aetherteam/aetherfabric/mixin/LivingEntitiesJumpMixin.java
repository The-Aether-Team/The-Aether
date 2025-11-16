package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.events.LivingEntityEvents;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {LivingEntity.class, MagmaCube.class, Slime.class})
public abstract class LivingEntitiesJumpMixin {
    @Definition(id = "hasImpulse", field = {
        "Lnet/minecraft/world/entity/LivingEntity;hasImpulse:Z",
        "Lnet/minecraft/world/entity/monster/MagmaCube;hasImpulse:Z",
        "Lnet/minecraft/world/entity/monster/Slime;hasImpulse:Z",
    })
    @Expression("this.hasImpulse = true")
    @Inject(method = "jumpFromGround", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER))
    private void aetherFabric$fireJumpEvent(CallbackInfo ci){
        LivingEntityEvents.ON_JUMP.invoker().onJump((LivingEntity) (Object) this);
    }
}
