package com.gildedgames.aether.core.mixin.common;

import com.gildedgames.aether.core.capability.interfaces.IPhoenixArrow;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.PhoenixArrowPacket;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin
{
    @Shadow
    protected boolean inGround;
    @Shadow
    protected int inGroundTime;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/Projectile;tick()V", shift = At.Shift.AFTER), method = "tick")
    private void tick(CallbackInfo ci) {
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        IPhoenixArrow.get(arrow).ifPresent(phoenixArrow -> {
            if (phoenixArrow.isPhoenixArrow()) {
                if (!arrow.level.isClientSide) {
                    AetherPacketHandler.sendToNear(new PhoenixArrowPacket(arrow.getId(), true), arrow.getX(), arrow.getY(), arrow.getZ(), 1.0D, arrow.level.dimension());
                    if (this.inGround) {
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
        });
    }

    private void spawnParticles(AbstractArrow arrow) {
        if (arrow.level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME,
                    arrow.getX() + (serverLevel.getRandom().nextGaussian() / 5.0D),
                    arrow.getY() + (serverLevel.getRandom().nextGaussian() / 3.0D),
                    arrow.getZ() + (serverLevel.getRandom().nextGaussian() / 5.0D),
                    1, 0.0D, 0.0D, 0.0D, 0.0F);
        }
    }
}
