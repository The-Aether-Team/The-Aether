package com.gildedgames.aether.common.entity.projectile.weapon;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;

import com.gildedgames.aether.core.capability.interfaces.ILightningTracker;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class LightningKnifeEntity extends ProjectileItemEntity
{
	public LightningKnifeEntity(EntityType<? extends LightningKnifeEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public LightningKnifeEntity(LivingEntity owner, World worldIn) {
		super(AetherEntityTypes.LIGHTNING_KNIFE.get(), owner, worldIn);
	}

	public LightningKnifeEntity(World worldIn) {
		super(AetherEntityTypes.LIGHTNING_KNIFE.get(), worldIn);
	}

	@Override
	protected void onHit(RayTraceResult result) {
		if (!this.level.isClientSide) {
			if (result.getType() != RayTraceResult.Type.MISS && this.level instanceof ServerWorld) {
				LightningBoltEntity lightningBolt = EntityType.LIGHTNING_BOLT.create(this.level);
				if (lightningBolt != null) {
					ILightningTracker.get(lightningBolt).ifPresent(lightningTracker -> lightningTracker.setOwner(this.getOwner()));
					lightningBolt.setPos(this.getX(), this.getY(), this.getZ());
					this.level.addFreshEntity(lightningBolt);
				}
			}
			this.remove();
		}
		super.onHit(result);
	}

	@Override
	protected Item getDefaultItem() {
		return AetherItems.LIGHTNING_KNIFE.get();
	}	
	
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
