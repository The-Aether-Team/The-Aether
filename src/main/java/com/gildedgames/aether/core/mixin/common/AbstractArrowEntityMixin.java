package com.gildedgames.aether.core.mixin.common;

import com.gildedgames.aether.core.capability.interfaces.IPhoenixArrow;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.PhoenixArrowPacket;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrowEntity.class)
public class AbstractArrowEntityMixin
{
    @Shadow
    protected boolean inGround;
    @Shadow
    protected int inGroundTime;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileEntity;tick()V", shift = At.Shift.AFTER), method = "tick", cancellable = true)
    private void tick(CallbackInfo ci) {
        AbstractArrowEntity arrow = (AbstractArrowEntity) (Object) this;
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

    private void spawnParticles(AbstractArrowEntity arrow) {
        if (arrow.level instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) arrow.level;
            world.addParticle(ParticleTypes.FLAME,
                    arrow.getX() + (world.getRandom().nextGaussian() / 5.0D),
                    arrow.getY() + (world.getRandom().nextGaussian() / 3.0D),
                    arrow.getZ() + (world.getRandom().nextGaussian() / 5.0D),
                    0.0D, 0.0D, 0.0D);
        }
    }
}
