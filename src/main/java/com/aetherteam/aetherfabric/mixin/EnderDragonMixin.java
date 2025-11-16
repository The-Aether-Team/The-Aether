package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.events.ExperienceDropHelper;
import com.aetherteam.aetherfabric.events.LivingEntityEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragon.class)
public abstract class EnderDragonMixin extends Mob {
    @Nullable
    @Unique
    private Player unlimitedLastHurtByPlayer = null;

    protected EnderDragonMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void aetherFabric$capturePlayerBetter(CallbackInfo ci) {
        // lastHurtByPlayer is cleared after 100 ticks, capture it indefinitely in unlimitedLastHurtByPlayer for LivingExperienceDropEvent
        if (this.lastHurtByPlayer != null) this.unlimitedLastHurtByPlayer = lastHurtByPlayer;
        if (this.unlimitedLastHurtByPlayer != null && this.unlimitedLastHurtByPlayer.isRemoved()) this.unlimitedLastHurtByPlayer = null;
    }

    @WrapOperation(method = "tickDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"))
    private void aetherFabric$adjustExperienceAmount(ServerLevel level, Vec3 pos, int amount, Operation<Void> original) {
        var helper = new ExperienceDropHelper(amount);

        LivingEntityEvents.ON_EXPERIENCE_DROP.invoker().onExperienceDrop(this, this.unlimitedLastHurtByPlayer, helper);

        original.call(level, pos, helper.getFinalExperienceAmount());
    }
}
