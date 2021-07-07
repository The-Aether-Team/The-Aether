package com.gildedgames.aether.core.mixin.common;

import com.gildedgames.aether.core.capability.interfaces.IPhoenixArrow;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.PhoenixArrowPacket;
import com.gildedgames.aether.core.network.packet.client.PhoenixParticlePacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrowEntity.class)
public abstract class AbstractArrowEntityMixin extends Entity
{
    @Shadow
    protected boolean inGround;
    @Shadow
    protected int inGroundTime;

    public AbstractArrowEntityMixin(EntityType<?> p_i48580_1_, World p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    private void tick(CallbackInfo ci) {
        Entity entity = this.getEntity();
        if (entity instanceof AbstractArrowEntity) {
            AbstractArrowEntity arrow = (AbstractArrowEntity) entity;
            IPhoenixArrow.get(arrow).ifPresent(phoenixArrow -> {
                if (phoenixArrow.isPhoenixArrow()) {
                    AetherPacketHandler.sendToAll(new PhoenixArrowPacket(this.getId(), true));
                    if (this.inGround) {
                        if (this.inGroundTime % 5 == 0) {
                            for (int i = 0; i < 1; i++) {
                                AetherPacketHandler.sendToAll(new PhoenixParticlePacket(this.getId()));
                            }
                        }
                    } else {
                        for (int i = 0; i < 2; i++) {
                            AetherPacketHandler.sendToAll(new PhoenixParticlePacket(this.getId()));
                        }
                    }
                }
            });
        }
    }
}
