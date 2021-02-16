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

	public LightningKnifeEntity(double x, double y, double z, World worldIn) {
		super(AetherEntityTypes.LIGHTNING_KNIFE.get(), x, y, z, worldIn);
	}

	public LightningKnifeEntity(LivingEntity owner, World worldIn) {
		super(AetherEntityTypes.LIGHTNING_KNIFE.get(), owner, worldIn);
	}

	public LightningKnifeEntity(World worldIn) {
		super(AetherEntityTypes.LIGHTNING_KNIFE.get(), worldIn);
	}

	public LightningKnifeEntity(FMLPlayMessages.SpawnEntity packet, World worldIn) {
		super(AetherEntityTypes.LIGHTNING_KNIFE.get(), worldIn);
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
