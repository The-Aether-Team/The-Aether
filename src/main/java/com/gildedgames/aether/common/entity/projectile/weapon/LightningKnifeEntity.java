package com.gildedgames.aether.common.entity.projectile.weapon;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;

import com.gildedgames.aether.core.capability.interfaces.ILightningTracker;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.NetworkHooks;

public class LightningKnifeEntity extends ThrowableItemProjectile
{
	public LightningKnifeEntity(EntityType<? extends LightningKnifeEntity> entityTypeIn, Level worldIn) {
		super(entityTypeIn, worldIn);
	}

	public LightningKnifeEntity(LivingEntity owner, Level worldIn) {
		super(AetherEntityTypes.LIGHTNING_KNIFE.get(), owner, worldIn);
	}

	public LightningKnifeEntity(Level worldIn) {
		super(AetherEntityTypes.LIGHTNING_KNIFE.get(), worldIn);
	}

	@Override
	protected void onHit(HitResult result) {
		if (!this.level.isClientSide) {
			if (result.getType() != HitResult.Type.MISS && this.level instanceof ServerLevel) {
				LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(this.level);
				if (lightningBolt != null) {
					ILightningTracker.get(lightningBolt).ifPresent(lightningTracker -> lightningTracker.setOwner(this.getOwner()));
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
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
