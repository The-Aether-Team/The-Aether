package com.aetherteam.aether.entity.projectile.weapon;

import com.aetherteam.aether.capability.lightning.LightningTracker;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

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

	@Override
	protected void onHit(HitResult result) {
		if (!this.level.isClientSide) {
			if (result.getType() != HitResult.Type.MISS && this.level instanceof ServerLevel) {
				LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(this.level);
				if (lightningBolt != null) {
					LightningTracker.get(lightningBolt).ifPresent(lightningTracker -> lightningTracker.setOwner(this.getOwner()));
					lightningBolt.setPos(this.getX(), this.getY(), this.getZ());
					this.level.addFreshEntity(lightningBolt);
				}
			}
			this.discard();
		}
		super.onHit(result);
	}

	@Override
	protected Item getDefaultItem() {
		return AetherItems.LIGHTNING_KNIFE.get();
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
