package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {
    @Shadow
    protected boolean inGround;
    @Shadow
    protected int inGroundTime;

    /**
     * Spawns particles from Phoenix Arrows.
     *
     * @param ci The {@link CallbackInfo} for the void method return.
     * @see AbstractArrowMixin#spawnParticles(AbstractArrow)
     */
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/Projectile;tick()V", shift = At.Shift.AFTER), method = "tick")
    private void tick(CallbackInfo ci) {
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        if (arrow.hasData(AetherDataAttachments.PHOENIX_ARROW)) {
            var attachment = arrow.getData(AetherDataAttachments.PHOENIX_ARROW);
            if (attachment.isPhoenixArrow()) {
                if (!arrow.level().isClientSide()) {
                    attachment.setSynched(arrow.getId(), INBTSynchable.Direction.CLIENT, "setPhoenixArrow", true); // Sync Phoenix Arrow variable to client.
                    if (this.inGround) { // Spawn less particles when the arrow is in the ground.
                        if (this.inGroundTime % 5 == 0) {
                            for (int i = 0; i < 1; i++) {
                                this.spawnParticles(arrow);
                            }
                        }
                    } else {
                        for (int i = 0; i < 2; i++) {
                            this.spawnParticles(arrow);
                        }
                    }
                }
            }
        }
    }

    private void spawnParticles(AbstractArrow arrow) {
        if (arrow.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME,
                    arrow.getX() + (serverLevel.getRandom().nextGaussian() / 5.0),
                    arrow.getY() + (serverLevel.getRandom().nextGaussian() / 3.0),
                    arrow.getZ() + (serverLevel.getRandom().nextGaussian() / 5.0),
                    1, 0.0, 0.0, 0.0, 0.0F);
        }
    }
}
