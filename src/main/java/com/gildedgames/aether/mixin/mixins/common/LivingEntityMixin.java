package com.gildedgames.aether.mixin.mixins.common;

import com.gildedgames.aether.capability.cape.CapeEntity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    /**
     * The cape rotation and movement handling must be injected into the end of the {@link LivingEntity#aiStep()}; otherwise calling it from a tick event has different rotation and movement results compared to vanilla capes.
     */
    @Inject(at = @At(value = "RETURN"), method = "aiStep")
    private void tick(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        CapeEntity.get(livingEntity).ifPresent(CapeEntity::moveCloak);
    }
}
