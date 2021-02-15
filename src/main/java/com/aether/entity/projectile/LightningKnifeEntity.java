package com.aether.entity.projectile;

import com.aether.registry.AetherEntityTypes;
import com.aether.registry.AetherItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class LightningKnifeEntity extends ProjectileItemEntity {

	public LightningKnifeEntity(EntityType<? extends LightningKnifeEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public LightningKnifeEntity constructOwner(LivingEntity owner)
	{
		this.setShooter(owner);
		return new LightningKnifeEntity(AetherEntityTypes.LIGHTNING_KNIFE.get(), this.world);
	}

	public LightningKnifeEntity constructPosition(double x, double y, double z)
	{
		this.setPosition(x, y, z);
		return new LightningKnifeEntity(AetherEntityTypes.LIGHTNING_KNIFE.get(), this.world);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (this.world.isRemote) {
			return;
		}
		
		if (result.getType() != RayTraceResult.Type.MISS && this.world instanceof ServerWorld) {
			LightningBoltEntity lightningBolt = EntityType.LIGHTNING_BOLT.create(this.world);
			lightningBolt.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
			this.world.addEntity(lightningBolt);
		}
		
		this.remove();
	}

	@Override
	protected Item getDefaultItem() {
		return AetherItems.LIGHTNING_KNIFE.get();
	}	
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
