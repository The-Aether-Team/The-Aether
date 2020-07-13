package com.aether.entity.projectile;

import com.aether.entity.AetherEntityTypes;
import com.aether.item.AetherItems;

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
		super(AetherEntityTypes.LIGHTNING_KNIFE, x, y, z, worldIn);
	}

	public LightningKnifeEntity(LivingEntity owner, World worldIn) {
		super(AetherEntityTypes.LIGHTNING_KNIFE, owner, worldIn);
	}

	public LightningKnifeEntity(World worldIn) {
		super(AetherEntityTypes.LIGHTNING_KNIFE, worldIn);
	}
	
	public LightningKnifeEntity(FMLPlayMessages.SpawnEntity packet, World worldIn) {
		super(AetherEntityTypes.LIGHTNING_KNIFE, worldIn);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (this.world.isRemote) {
			return;
		}
		
		if (result.getType() != RayTraceResult.Type.MISS && this.world instanceof ServerWorld) {
			((ServerWorld)this.world).addLightningBolt(new LightningBoltEntity(this.world, result.getHitVec().x, result.getHitVec().y, result.getHitVec().z, false));
		}
		
		this.remove();
	}

	@Override
	protected Item getDefaultItem() {
		return AetherItems.LIGHTNING_KNIFE;
	}	
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
