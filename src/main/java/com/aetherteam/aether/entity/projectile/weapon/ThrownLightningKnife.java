package com.aetherteam.aether.entity.projectile.weapon;

import com.aetherteam.aether.attachment.LightningTrackerAttachment;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.EntityUtil;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ThrownLightningKnife extends ThrowableItemProjectile {
    public ThrownLightningKnife(EntityType<? extends ThrownLightningKnife> type, Level level) {
        super(type, level);
    }

    public ThrownLightningKnife(LivingEntity owner, Level level) {
        super(AetherEntityTypes.LIGHTNING_KNIFE.get(), owner, level);
    }

    public ThrownLightningKnife(Level level) {
        super(AetherEntityTypes.LIGHTNING_KNIFE.get(), level);
    }

    /**
     * Summons lightning when hitting something, and tracks the lightning as spawned by the owner of the projectile through {@link LightningTrackerAttachment}.
     *
     * @param result The {@link HitResult} of the projectile.
     */
    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            if (result.getType() != HitResult.Type.MISS && this.level() instanceof ServerLevel) {
                EntityUtil.summonLightningFromProjectile(this);
            }
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return AetherItems.LIGHTNING_KNIFE.get();
    }
}
